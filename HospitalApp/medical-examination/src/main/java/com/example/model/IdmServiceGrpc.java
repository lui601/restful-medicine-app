package com.example.model;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: idm.proto")
public final class IdmServiceGrpc {

  private IdmServiceGrpc() {}

  public static final String SERVICE_NAME = "model.IdmService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.CreateUserRequest,
      com.example.model.IdmProto.UserResponse> getCreatePatientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePatient",
      requestType = com.example.model.IdmProto.CreateUserRequest.class,
      responseType = com.example.model.IdmProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.CreateUserRequest,
      com.example.model.IdmProto.UserResponse> getCreatePatientMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.CreateUserRequest, com.example.model.IdmProto.UserResponse> getCreatePatientMethod;
    if ((getCreatePatientMethod = IdmServiceGrpc.getCreatePatientMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getCreatePatientMethod = IdmServiceGrpc.getCreatePatientMethod) == null) {
          IdmServiceGrpc.getCreatePatientMethod = getCreatePatientMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.CreateUserRequest, com.example.model.IdmProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "CreatePatient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.CreateUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UserResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("CreatePatient"))
                  .build();
          }
        }
     }
     return getCreatePatientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.CreateUserRequest,
      com.example.model.IdmProto.UserResponse> getCreatePhysicianMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePhysician",
      requestType = com.example.model.IdmProto.CreateUserRequest.class,
      responseType = com.example.model.IdmProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.CreateUserRequest,
      com.example.model.IdmProto.UserResponse> getCreatePhysicianMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.CreateUserRequest, com.example.model.IdmProto.UserResponse> getCreatePhysicianMethod;
    if ((getCreatePhysicianMethod = IdmServiceGrpc.getCreatePhysicianMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getCreatePhysicianMethod = IdmServiceGrpc.getCreatePhysicianMethod) == null) {
          IdmServiceGrpc.getCreatePhysicianMethod = getCreatePhysicianMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.CreateUserRequest, com.example.model.IdmProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "CreatePhysician"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.CreateUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UserResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("CreatePhysician"))
                  .build();
          }
        }
     }
     return getCreatePhysicianMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.UpdateInfoRequest,
      com.example.model.IdmProto.UserResponse> getUpdateInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateInfo",
      requestType = com.example.model.IdmProto.UpdateInfoRequest.class,
      responseType = com.example.model.IdmProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.UpdateInfoRequest,
      com.example.model.IdmProto.UserResponse> getUpdateInfoMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.UpdateInfoRequest, com.example.model.IdmProto.UserResponse> getUpdateInfoMethod;
    if ((getUpdateInfoMethod = IdmServiceGrpc.getUpdateInfoMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getUpdateInfoMethod = IdmServiceGrpc.getUpdateInfoMethod) == null) {
          IdmServiceGrpc.getUpdateInfoMethod = getUpdateInfoMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.UpdateInfoRequest, com.example.model.IdmProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "UpdateInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UpdateInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UserResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("UpdateInfo"))
                  .build();
          }
        }
     }
     return getUpdateInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.SetRoleRequest,
      com.example.model.IdmProto.UserResponse> getSetRoleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetRole",
      requestType = com.example.model.IdmProto.SetRoleRequest.class,
      responseType = com.example.model.IdmProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.SetRoleRequest,
      com.example.model.IdmProto.UserResponse> getSetRoleMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.SetRoleRequest, com.example.model.IdmProto.UserResponse> getSetRoleMethod;
    if ((getSetRoleMethod = IdmServiceGrpc.getSetRoleMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getSetRoleMethod = IdmServiceGrpc.getSetRoleMethod) == null) {
          IdmServiceGrpc.getSetRoleMethod = getSetRoleMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.SetRoleRequest, com.example.model.IdmProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "SetRole"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.SetRoleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UserResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("SetRole"))
                  .build();
          }
        }
     }
     return getSetRoleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.GetRolesRequest,
      com.example.model.IdmProto.RolesResponse> getGetRolesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRoles",
      requestType = com.example.model.IdmProto.GetRolesRequest.class,
      responseType = com.example.model.IdmProto.RolesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.GetRolesRequest,
      com.example.model.IdmProto.RolesResponse> getGetRolesMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.GetRolesRequest, com.example.model.IdmProto.RolesResponse> getGetRolesMethod;
    if ((getGetRolesMethod = IdmServiceGrpc.getGetRolesMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getGetRolesMethod = IdmServiceGrpc.getGetRolesMethod) == null) {
          IdmServiceGrpc.getGetRolesMethod = getGetRolesMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.GetRolesRequest, com.example.model.IdmProto.RolesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "GetRoles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.GetRolesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.RolesResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("GetRoles"))
                  .build();
          }
        }
     }
     return getGetRolesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.GetUserRequest,
      com.example.model.IdmProto.UserResponse> getGetUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUser",
      requestType = com.example.model.IdmProto.GetUserRequest.class,
      responseType = com.example.model.IdmProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.GetUserRequest,
      com.example.model.IdmProto.UserResponse> getGetUserMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.GetUserRequest, com.example.model.IdmProto.UserResponse> getGetUserMethod;
    if ((getGetUserMethod = IdmServiceGrpc.getGetUserMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getGetUserMethod = IdmServiceGrpc.getGetUserMethod) == null) {
          IdmServiceGrpc.getGetUserMethod = getGetUserMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.GetUserRequest, com.example.model.IdmProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "GetUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.GetUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UserResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("GetUser"))
                  .build();
          }
        }
     }
     return getGetUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.GetUsersRequest,
      com.example.model.IdmProto.UsersResponse> getGetUsersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUsers",
      requestType = com.example.model.IdmProto.GetUsersRequest.class,
      responseType = com.example.model.IdmProto.UsersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.GetUsersRequest,
      com.example.model.IdmProto.UsersResponse> getGetUsersMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.GetUsersRequest, com.example.model.IdmProto.UsersResponse> getGetUsersMethod;
    if ((getGetUsersMethod = IdmServiceGrpc.getGetUsersMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getGetUsersMethod = IdmServiceGrpc.getGetUsersMethod) == null) {
          IdmServiceGrpc.getGetUsersMethod = getGetUsersMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.GetUsersRequest, com.example.model.IdmProto.UsersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "GetUsers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.GetUsersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UsersResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("GetUsers"))
                  .build();
          }
        }
     }
     return getGetUsersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.LoginRequest,
      com.example.model.IdmProto.LoginResponse> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = com.example.model.IdmProto.LoginRequest.class,
      responseType = com.example.model.IdmProto.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.LoginRequest,
      com.example.model.IdmProto.LoginResponse> getLoginMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.LoginRequest, com.example.model.IdmProto.LoginResponse> getLoginMethod;
    if ((getLoginMethod = IdmServiceGrpc.getLoginMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getLoginMethod = IdmServiceGrpc.getLoginMethod) == null) {
          IdmServiceGrpc.getLoginMethod = getLoginMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.LoginRequest, com.example.model.IdmProto.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.LoginResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("Login"))
                  .build();
          }
        }
     }
     return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.LogoutRequest,
      com.google.protobuf.Empty> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Logout",
      requestType = com.example.model.IdmProto.LogoutRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.LogoutRequest,
      com.google.protobuf.Empty> getLogoutMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.LogoutRequest, com.google.protobuf.Empty> getLogoutMethod;
    if ((getLogoutMethod = IdmServiceGrpc.getLogoutMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getLogoutMethod = IdmServiceGrpc.getLogoutMethod) == null) {
          IdmServiceGrpc.getLogoutMethod = getLogoutMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.LogoutRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "Logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.LogoutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("Logout"))
                  .build();
          }
        }
     }
     return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.AuthorizeRequest,
      com.example.model.IdmProto.UserResponse> getAuthorizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Authorize",
      requestType = com.example.model.IdmProto.AuthorizeRequest.class,
      responseType = com.example.model.IdmProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.AuthorizeRequest,
      com.example.model.IdmProto.UserResponse> getAuthorizeMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.AuthorizeRequest, com.example.model.IdmProto.UserResponse> getAuthorizeMethod;
    if ((getAuthorizeMethod = IdmServiceGrpc.getAuthorizeMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getAuthorizeMethod = IdmServiceGrpc.getAuthorizeMethod) == null) {
          IdmServiceGrpc.getAuthorizeMethod = getAuthorizeMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.AuthorizeRequest, com.example.model.IdmProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "Authorize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.AuthorizeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.UserResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("Authorize"))
                  .build();
          }
        }
     }
     return getAuthorizeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.model.IdmProto.DeletePhysicianRequest,
      com.google.protobuf.Empty> getDeletePhysicianMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeletePhysician",
      requestType = com.example.model.IdmProto.DeletePhysicianRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.model.IdmProto.DeletePhysicianRequest,
      com.google.protobuf.Empty> getDeletePhysicianMethod() {
    io.grpc.MethodDescriptor<com.example.model.IdmProto.DeletePhysicianRequest, com.google.protobuf.Empty> getDeletePhysicianMethod;
    if ((getDeletePhysicianMethod = IdmServiceGrpc.getDeletePhysicianMethod) == null) {
      synchronized (IdmServiceGrpc.class) {
        if ((getDeletePhysicianMethod = IdmServiceGrpc.getDeletePhysicianMethod) == null) {
          IdmServiceGrpc.getDeletePhysicianMethod = getDeletePhysicianMethod = 
              io.grpc.MethodDescriptor.<com.example.model.IdmProto.DeletePhysicianRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.IdmService", "DeletePhysician"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.model.IdmProto.DeletePhysicianRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new IdmServiceMethodDescriptorSupplier("DeletePhysician"))
                  .build();
          }
        }
     }
     return getDeletePhysicianMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IdmServiceStub newStub(io.grpc.Channel channel) {
    return new IdmServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IdmServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new IdmServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IdmServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new IdmServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class IdmServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createPatient(com.example.model.IdmProto.CreateUserRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreatePatientMethod(), responseObserver);
    }

    /**
     */
    public void createPhysician(com.example.model.IdmProto.CreateUserRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreatePhysicianMethod(), responseObserver);
    }

    /**
     */
    public void updateInfo(com.example.model.IdmProto.UpdateInfoRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateInfoMethod(), responseObserver);
    }

    /**
     */
    public void setRole(com.example.model.IdmProto.SetRoleRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSetRoleMethod(), responseObserver);
    }

    /**
     */
    public void getRoles(com.example.model.IdmProto.GetRolesRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.RolesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRolesMethod(), responseObserver);
    }

    /**
     */
    public void getUser(com.example.model.IdmProto.GetUserRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserMethod(), responseObserver);
    }

    /**
     */
    public void getUsers(com.example.model.IdmProto.GetUsersRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UsersResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUsersMethod(), responseObserver);
    }

    /**
     */
    public void login(com.example.model.IdmProto.LoginRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.LoginResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    public void logout(com.example.model.IdmProto.LogoutRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    public void authorize(com.example.model.IdmProto.AuthorizeRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAuthorizeMethod(), responseObserver);
    }

    /**
     */
    public void deletePhysician(com.example.model.IdmProto.DeletePhysicianRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getDeletePhysicianMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreatePatientMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.CreateUserRequest,
                com.example.model.IdmProto.UserResponse>(
                  this, METHODID_CREATE_PATIENT)))
          .addMethod(
            getCreatePhysicianMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.CreateUserRequest,
                com.example.model.IdmProto.UserResponse>(
                  this, METHODID_CREATE_PHYSICIAN)))
          .addMethod(
            getUpdateInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.UpdateInfoRequest,
                com.example.model.IdmProto.UserResponse>(
                  this, METHODID_UPDATE_INFO)))
          .addMethod(
            getSetRoleMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.SetRoleRequest,
                com.example.model.IdmProto.UserResponse>(
                  this, METHODID_SET_ROLE)))
          .addMethod(
            getGetRolesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.GetRolesRequest,
                com.example.model.IdmProto.RolesResponse>(
                  this, METHODID_GET_ROLES)))
          .addMethod(
            getGetUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.GetUserRequest,
                com.example.model.IdmProto.UserResponse>(
                  this, METHODID_GET_USER)))
          .addMethod(
            getGetUsersMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.GetUsersRequest,
                com.example.model.IdmProto.UsersResponse>(
                  this, METHODID_GET_USERS)))
          .addMethod(
            getLoginMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.LoginRequest,
                com.example.model.IdmProto.LoginResponse>(
                  this, METHODID_LOGIN)))
          .addMethod(
            getLogoutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.LogoutRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_LOGOUT)))
          .addMethod(
            getAuthorizeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.AuthorizeRequest,
                com.example.model.IdmProto.UserResponse>(
                  this, METHODID_AUTHORIZE)))
          .addMethod(
            getDeletePhysicianMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.model.IdmProto.DeletePhysicianRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETE_PHYSICIAN)))
          .build();
    }
  }

  /**
   */
  public static final class IdmServiceStub extends io.grpc.stub.AbstractStub<IdmServiceStub> {
    private IdmServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private IdmServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IdmServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new IdmServiceStub(channel, callOptions);
    }

    /**
     */
    public void createPatient(com.example.model.IdmProto.CreateUserRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreatePatientMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createPhysician(com.example.model.IdmProto.CreateUserRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreatePhysicianMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateInfo(com.example.model.IdmProto.UpdateInfoRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setRole(com.example.model.IdmProto.SetRoleRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetRoleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRoles(com.example.model.IdmProto.GetRolesRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.RolesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRolesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUser(com.example.model.IdmProto.GetUserRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUsers(com.example.model.IdmProto.GetUsersRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UsersResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUsersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void login(com.example.model.IdmProto.LoginRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.LoginResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(com.example.model.IdmProto.LogoutRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void authorize(com.example.model.IdmProto.AuthorizeRequest request,
        io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAuthorizeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deletePhysician(com.example.model.IdmProto.DeletePhysicianRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeletePhysicianMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class IdmServiceBlockingStub extends io.grpc.stub.AbstractStub<IdmServiceBlockingStub> {
    private IdmServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private IdmServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IdmServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new IdmServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.model.IdmProto.UserResponse createPatient(com.example.model.IdmProto.CreateUserRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreatePatientMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.UserResponse createPhysician(com.example.model.IdmProto.CreateUserRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreatePhysicianMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.UserResponse updateInfo(com.example.model.IdmProto.UpdateInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.UserResponse setRole(com.example.model.IdmProto.SetRoleRequest request) {
      return blockingUnaryCall(
          getChannel(), getSetRoleMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.RolesResponse getRoles(com.example.model.IdmProto.GetRolesRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetRolesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.UserResponse getUser(com.example.model.IdmProto.GetUserRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.UsersResponse getUsers(com.example.model.IdmProto.GetUsersRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetUsersMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.LoginResponse login(com.example.model.IdmProto.LoginRequest request) {
      return blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty logout(com.example.model.IdmProto.LogoutRequest request) {
      return blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.model.IdmProto.UserResponse authorize(com.example.model.IdmProto.AuthorizeRequest request) {
      return blockingUnaryCall(
          getChannel(), getAuthorizeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty deletePhysician(com.example.model.IdmProto.DeletePhysicianRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeletePhysicianMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class IdmServiceFutureStub extends io.grpc.stub.AbstractStub<IdmServiceFutureStub> {
    private IdmServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private IdmServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IdmServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new IdmServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UserResponse> createPatient(
        com.example.model.IdmProto.CreateUserRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreatePatientMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UserResponse> createPhysician(
        com.example.model.IdmProto.CreateUserRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreatePhysicianMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UserResponse> updateInfo(
        com.example.model.IdmProto.UpdateInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UserResponse> setRole(
        com.example.model.IdmProto.SetRoleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSetRoleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.RolesResponse> getRoles(
        com.example.model.IdmProto.GetRolesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRolesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UserResponse> getUser(
        com.example.model.IdmProto.GetUserRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UsersResponse> getUsers(
        com.example.model.IdmProto.GetUsersRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUsersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.LoginResponse> login(
        com.example.model.IdmProto.LoginRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> logout(
        com.example.model.IdmProto.LogoutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.model.IdmProto.UserResponse> authorize(
        com.example.model.IdmProto.AuthorizeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAuthorizeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deletePhysician(
        com.example.model.IdmProto.DeletePhysicianRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeletePhysicianMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_PATIENT = 0;
  private static final int METHODID_CREATE_PHYSICIAN = 1;
  private static final int METHODID_UPDATE_INFO = 2;
  private static final int METHODID_SET_ROLE = 3;
  private static final int METHODID_GET_ROLES = 4;
  private static final int METHODID_GET_USER = 5;
  private static final int METHODID_GET_USERS = 6;
  private static final int METHODID_LOGIN = 7;
  private static final int METHODID_LOGOUT = 8;
  private static final int METHODID_AUTHORIZE = 9;
  private static final int METHODID_DELETE_PHYSICIAN = 10;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final IdmServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(IdmServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_PATIENT:
          serviceImpl.createPatient((com.example.model.IdmProto.CreateUserRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse>) responseObserver);
          break;
        case METHODID_CREATE_PHYSICIAN:
          serviceImpl.createPhysician((com.example.model.IdmProto.CreateUserRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse>) responseObserver);
          break;
        case METHODID_UPDATE_INFO:
          serviceImpl.updateInfo((com.example.model.IdmProto.UpdateInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse>) responseObserver);
          break;
        case METHODID_SET_ROLE:
          serviceImpl.setRole((com.example.model.IdmProto.SetRoleRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse>) responseObserver);
          break;
        case METHODID_GET_ROLES:
          serviceImpl.getRoles((com.example.model.IdmProto.GetRolesRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.RolesResponse>) responseObserver);
          break;
        case METHODID_GET_USER:
          serviceImpl.getUser((com.example.model.IdmProto.GetUserRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse>) responseObserver);
          break;
        case METHODID_GET_USERS:
          serviceImpl.getUsers((com.example.model.IdmProto.GetUsersRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UsersResponse>) responseObserver);
          break;
        case METHODID_LOGIN:
          serviceImpl.login((com.example.model.IdmProto.LoginRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.LoginResponse>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((com.example.model.IdmProto.LogoutRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_AUTHORIZE:
          serviceImpl.authorize((com.example.model.IdmProto.AuthorizeRequest) request,
              (io.grpc.stub.StreamObserver<com.example.model.IdmProto.UserResponse>) responseObserver);
          break;
        case METHODID_DELETE_PHYSICIAN:
          serviceImpl.deletePhysician((com.example.model.IdmProto.DeletePhysicianRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class IdmServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IdmServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.model.IdmProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("IdmService");
    }
  }

  private static final class IdmServiceFileDescriptorSupplier
      extends IdmServiceBaseDescriptorSupplier {
    IdmServiceFileDescriptorSupplier() {}
  }

  private static final class IdmServiceMethodDescriptorSupplier
      extends IdmServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    IdmServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (IdmServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IdmServiceFileDescriptorSupplier())
              .addMethod(getCreatePatientMethod())
              .addMethod(getCreatePhysicianMethod())
              .addMethod(getUpdateInfoMethod())
              .addMethod(getSetRoleMethod())
              .addMethod(getGetRolesMethod())
              .addMethod(getGetUserMethod())
              .addMethod(getGetUsersMethod())
              .addMethod(getLoginMethod())
              .addMethod(getLogoutMethod())
              .addMethod(getAuthorizeMethod())
              .addMethod(getDeletePhysicianMethod())
              .build();
        }
      }
    }
    return result;
  }
}
