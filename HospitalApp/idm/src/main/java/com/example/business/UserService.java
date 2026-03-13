package com.example.business;

import com.example.model.IdmProto.*;
import com.example.persistence.entity.RoleEntity;
import com.example.persistence.entity.UserEntity;
import com.example.persistence.repository.RoleRepository;
import com.example.persistence.repository.UserRepository;
import com.example.util.Constants;
import com.example.util.CustomException;
import com.google.rpc.Code;
import com.google.rpc.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();;
    }

    public void createRoles () {
        roleRepository.save(new RoleEntity(Constants.ADMINISTRATOR_ROLE_ID, "administrator"));
        roleRepository.save(new RoleEntity(Constants.PATIENT_ROLE_ID, "patient"));
        roleRepository.save(new RoleEntity(Constants.PHYSICIAN_ROLE_ID, "physician"));
    }

    public void createAdministrator () {
        UserEntity administrator = UserEntity.builder()
                .username("administrator")
                .password(passwordEncoder.encode("administrator"))
                .roleId(Constants.ADMINISTRATOR_ROLE_ID)
                .build();

        userRepository.save(administrator);
    }
    private UserResponse toUserResponse(UserEntity userEntity) {
        UserResponse userResponse = UserResponse.newBuilder()
                .setUserId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setRoleId(userEntity.getRoleId())
                .build();

        return userResponse;
    }

    // initial atribuim rol null
    private UserEntity toEntity(CreateUserRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleId(null)
                .build();
    }

    public UserResponse createPatient(CreateUserRequest request) throws CustomException {
        UserEntity userEntity = toEntity(request);
        userEntity.setRoleId(Constants.PATIENT_ROLE_ID);

        verifyUsernameDoesNotExist(request.getUsername());

        return toUserResponse(userRepository.save(userEntity));
    }

    public UserResponse createPhysician(CreateUserRequest request) throws CustomException {
        UserEntity userEntity = toEntity(request);
        userEntity.setRoleId(Constants.PHYSICIAN_ROLE_ID);

        verifyUsernameDoesNotExist(request.getUsername());

        return toUserResponse(userRepository.save(userEntity));
    }

    public void verifyUsernameDoesNotExist(String username) throws CustomException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isPresent()) {
            Status status = Status.newBuilder()
                    .setCode(Code.ALREADY_EXISTS_VALUE)
                    .setMessage("Username already exists")
                    .build();
            throw new CustomException(status);
        }
    }

    public UserResponse updateInfo(UpdateInfoRequest request) throws CustomException {
        UserEntity userEntity = verifyUserExists(request.getUserId());

        verifyUsernameDoesNotExist(request.getUsername());

        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        return toUserResponse(userRepository.save(userEntity));
    }

    public UserResponse setRole(SetRoleRequest request) throws CustomException {
        UserEntity userEntity = verifyUserExists(request.getUserId());
        RoleEntity roleEntity = verifyRoleExists(request.getRoleId());

        userEntity.setRoleId(roleEntity.getId());

        return toUserResponse(userRepository.save(userEntity));
    }

    public UserResponse getUser(GetUserRequest request) throws CustomException {
        UserEntity userEntity = verifyUserExists(request.getUserId());

        return toUserResponse(userEntity);
    }

    public UsersResponse getUsers() {
        List<UserEntity> users = userRepository.findAll();

        return toUsersResponse(users);
    }

    private RoleResponse toRoleResponse(RoleEntity roleEntity) {
        RoleResponse roleResponse = RoleResponse.newBuilder()
                .setRoleId(roleEntity.getId())
                .setRoleName(roleEntity.getName())
                .build();

        return roleResponse;
    }

    private RolesResponse toRolesResponse(List<RoleEntity> roleEntities) {
        RolesResponse rolesResponse = RolesResponse.newBuilder()
                        .addAllRole(roleEntities.stream()
                                .map(this::toRoleResponse)
                                .collect(Collectors.toList()))
                        .build();

        return rolesResponse;
    }

    private UsersResponse toUsersResponse(List<UserEntity> userEntities) {
        UsersResponse usersResponse = UsersResponse.newBuilder()
                .addAllUser(userEntities.stream()
                        .map(this::toUserResponse)
                        .collect(Collectors.toList()))
                .build();

        return usersResponse;
    }

    public RolesResponse getRoles() {
        List<RoleEntity> roles = roleRepository.findAll();

        return toRolesResponse(roles);
    }

    public UserEntity login(LoginRequest request) throws CustomException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(request.getUsername());

        if (optionalUserEntity.isEmpty()) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAUTHENTICATED_VALUE)
                    .setMessage("Incorrect username or password.")
                    .build();
            throw new CustomException(status);
        }

        if (!passwordEncoder.matches(request.getPassword(), optionalUserEntity.get().getPassword())) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAUTHENTICATED_VALUE)
                    .setMessage("Incorrect username or password.")
                    .build();
            throw new CustomException(status);
        }

        return optionalUserEntity.get();
    }

    public void deletePhysician(DeletePhysicianRequest request) throws CustomException {
        UserEntity userEntity = verifyUserExists(request.getUserId());

        userRepository.delete(userEntity);
    }

    private UserEntity verifyUserExists(Integer userId) throws CustomException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

        if (optionalUserEntity.isPresent()) {
            return optionalUserEntity.get();
        } else {
            Status status = Status.newBuilder()
                    .setCode(Code.NOT_FOUND_VALUE)
                    .setMessage("User id doesn't exist.")
                    .build();
            throw new CustomException(status);
        }
    }

    private RoleEntity verifyRoleExists(Integer roleId) throws CustomException {
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(roleId);

        if (optionalRoleEntity.isPresent()) {
            return optionalRoleEntity.get();
        } else {
            Status status = Status.newBuilder()
                    .setCode(Code.NOT_FOUND_VALUE)
                    .setMessage("Role id doesn't exist.")
                    .build();
            throw new CustomException(status);
        }
    }
}
