package com.example.presentation;

import com.example.business.TokenService;
import com.example.business.UserService;
import com.example.model.IdmProto.*;
import com.example.model.IdmServiceGrpc;
import com.example.persistence.entity.UserEntity;
import com.example.util.CustomException;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import static io.grpc.protobuf.StatusProto.toStatusRuntimeException;

@GrpcService
public class IdmService extends IdmServiceGrpc.IdmServiceImplBase {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @Override
    public void createPatient(CreateUserRequest request, StreamObserver<UserResponse> responseObserver){
        try {
            UserResponse userResponse = userService.createPatient(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @SneakyThrows
    @Override
    public void createPhysician(CreateUserRequest request, StreamObserver<UserResponse> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyIsAdministrator(token);
            UserResponse userResponse = userService.createPhysician(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @SneakyThrows
    @Override
    public void updateInfo(UpdateInfoRequest request, StreamObserver<UserResponse> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyHasUserId(token, request.getUserId());
            UserResponse userResponse = userService.updateInfo(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @SneakyThrows
    @Override
    public void setRole(SetRoleRequest request, StreamObserver<UserResponse> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyIsAdministrator(token);
            UserResponse userResponse = userService.setRole(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @Override
    public void getRoles(GetRolesRequest request, StreamObserver<RolesResponse> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyToken(token);
            RolesResponse rolesResponse = userService.getRoles();
            responseObserver.onNext(rolesResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserResponse> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyToken(token);
            UserResponse userResponse = userService.getUser(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @Override
    public void getUsers(GetUsersRequest request, StreamObserver<UsersResponse> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyToken(token);
            UsersResponse usersResponse = userService.getUsers();
            responseObserver.onNext(usersResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @SneakyThrows
    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            UserEntity userEntity = userService.login(request);
            String token = tokenService.generateToken(userEntity);

            LoginResponse loginResponse = LoginResponse.newBuilder()
                    .setUserId(userEntity.getId())
                    .setToken(token)
                    .setRoleId(userEntity.getRoleId())
                    .build();
            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @Override
    public void logout(LogoutRequest request, StreamObserver<Empty> responseObserver){
        tokenService.logout(request.getToken());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @SneakyThrows
    @Override
    public void authorize(AuthorizeRequest request, StreamObserver<UserResponse> responseObserver){
        try {
            UserResponse userResponse = tokenService.authorize(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }

    @SneakyThrows
    @Override
    public void deletePhysician(DeletePhysicianRequest request, StreamObserver<Empty> responseObserver){
        try {
            String token = request.getToken();
            tokenService.verifyIsAdministrator(token);
            userService.deletePhysician(request);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();

        } catch (CustomException exception) {
            responseObserver.onError(toStatusRuntimeException(exception.getStatus()));
        }
    }
}
