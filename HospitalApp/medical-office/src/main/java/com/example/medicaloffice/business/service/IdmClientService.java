package com.example.medicaloffice.business.service;

import com.example.idm.IdmProto.*;
import com.example.idm.IdmServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class IdmClientService {

    public static Integer ADMINISTRATOR_ROLE_ID = 1;
    public static Integer PATIENT_ROLE_ID = 2;
    public static Integer PHYSICIAN_ROLE_ID = 3;
    private ManagedChannel channel;
    private IdmServiceGrpc.IdmServiceBlockingStub idmStub;

    public IdmClientService() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build(); // 9090 default grpc server port
        idmStub = IdmServiceGrpc.newBlockingStub(channel);
    }

    // validateTokenAndExtractIdentity
    public UserResponse authorize(String token) {
        AuthorizeRequest request = AuthorizeRequest.newBuilder()
                .setToken(token)
                .build();

        UserResponse response = idmStub.authorize(request);
        return response;
    }

    public Boolean verifyIsAdministrator(String token){
        UserResponse userResponse = authorize(token);
        return userResponse.getRoleId() == ADMINISTRATOR_ROLE_ID;
    }

    public void verifyIsLoggedIn(String token){
        UserResponse userResponse = authorize(token);
        // in caz de nu e autentificat, va fi generata o exceptie
    }

    public Boolean verifyIsPatient(String token){
        UserResponse userResponse = authorize(token);
        return userResponse.getRoleId() == PATIENT_ROLE_ID;
    }

    public Boolean verifyIsPhysician(String token){
        UserResponse userResponse = authorize(token);
        return userResponse.getRoleId() == PHYSICIAN_ROLE_ID;
    }

    public Boolean verifyIsPhysicianAndHasIdUser(String token, Integer userId){
        UserResponse userResponse = authorize(token);

        return userResponse.getRoleId() == PHYSICIAN_ROLE_ID && userResponse.getUserId() == userId;
    }

    public Boolean verifyIsPatientAndHasIdUser(String token, Integer userId){
        UserResponse userResponse = authorize(token);

        return userResponse.getRoleId() == PATIENT_ROLE_ID && userResponse.getUserId() == userId;
    }
}
