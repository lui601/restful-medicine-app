package com.example.business;

import com.example.model.IdmProto.AuthorizeRequest;
import com.example.model.IdmProto.UserResponse;
import com.example.persistence.entity.UserEntity;
import com.example.util.Constants;
import com.example.util.CustomException;
import com.google.rpc.Code;
import com.google.rpc.Status;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {

    private final RsaJsonWebKey rsaJsonWebKey; // generam token
    private final JwtConsumer jwtConsumer; // valideaza token

    private final List<String> blacklist;

    public TokenService() throws JoseException {
        blacklist = new ArrayList<>();
        rsaJsonWebKey = RsaJwkGenerator.generateJwk(4096);
        jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setRequireSubject() // user id
                .setVerificationKey(rsaJsonWebKey.getKey())
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();
    }

    public String generateToken(UserEntity userEntity) throws JoseException {
        JwtClaims claims = new JwtClaims();
        JsonWebSignature jws = new JsonWebSignature();

        claims.setExpirationTimeMinutesInTheFuture(60);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setSubject(String.valueOf(userEntity.getId()));
        claims.setStringClaim("roleId", String.valueOf(userEntity.getRoleId()));
        claims.setStringClaim("username", userEntity.getUsername());

        rsaJsonWebKey.setKeyId("keyId");

        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        return jws.getCompactSerialization();
    }

    public JwtClaims verifyToken(String token) throws CustomException {
        if (blacklist.contains(token)) {
            Status status = Status.newBuilder()
                    .setCode(Code.PERMISSION_DENIED_VALUE)
                    .setMessage("Your token is blacklisted.")
                    .build();
            throw new CustomException(status);
        }
        try {
            return jwtConsumer.processToClaims(token);
        } catch (InvalidJwtException e) {
            if (e.hasExpired()) {
                Status status = Status.newBuilder()
                        .setCode(Code.UNAUTHENTICATED_VALUE)
                        .setMessage("Your token expired.")
                        .build();
                throw new CustomException(status);
            }
            blacklist.add(token);
            Status status = Status.newBuilder()
                    .setCode(Code.UNAUTHENTICATED_VALUE)
                    .setMessage("Your token is invalid and will be blacklisted.")
                    .build();
            throw new CustomException(status);
        }
    }

    public void verifyIsAdministrator(String token) throws MalformedClaimException, CustomException {
        JwtClaims claims = verifyToken(token);
        Integer roleId = Integer.valueOf(claims.getStringClaimValue("roleId"));

        if (!roleId.equals(Constants.ADMINISTRATOR_ROLE_ID)) {
            Status status = Status.newBuilder()
                    .setCode(Code.PERMISSION_DENIED_VALUE)
                    .setMessage("You must be an administrator to create a physician.")
                    .build();
            throw new CustomException(status);
        }
    }

    public void verifyHasUserId(String token, Integer userId) throws MalformedClaimException, CustomException {
        JwtClaims claims = verifyToken(token);
        Integer tokenUserId = Integer.valueOf(claims.getSubject());

        if (!tokenUserId.equals(userId)) {
            Status status = Status.newBuilder()
                    .setCode(Code.PERMISSION_DENIED_VALUE)
                    .setMessage("You can only update your own information.")
                    .build();
            throw new CustomException(status);
        }
    }

    public UserResponse authorize(AuthorizeRequest request) throws MalformedClaimException, CustomException {
        JwtClaims jwtClaims = verifyToken(request.getToken());

        UserResponse userResponse = UserResponse.newBuilder()
                .setUserId(Integer.parseInt(jwtClaims.getSubject()))
                .setUsername(jwtClaims.getStringClaimValue("username"))
                .setRoleId(Integer.parseInt(jwtClaims.getClaimValueAsString("roleId")))
                .build();

        return userResponse;
    }

    public void logout(String token) {
        blacklist.add(token);
    }
}
