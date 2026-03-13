from google.protobuf import empty_pb2 as _empty_pb2
from google.protobuf import wrappers_pb2 as _wrappers_pb2
from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class CreateUserRequest(_message.Message):
    __slots__ = ("username", "password", "token")
    USERNAME_FIELD_NUMBER: _ClassVar[int]
    PASSWORD_FIELD_NUMBER: _ClassVar[int]
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    username: str
    password: str
    token: str
    def __init__(self, username: _Optional[str] = ..., password: _Optional[str] = ..., token: _Optional[str] = ...) -> None: ...

class UpdateInfoRequest(_message.Message):
    __slots__ = ("user_id", "token", "password", "username")
    USER_ID_FIELD_NUMBER: _ClassVar[int]
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    PASSWORD_FIELD_NUMBER: _ClassVar[int]
    USERNAME_FIELD_NUMBER: _ClassVar[int]
    user_id: int
    token: str
    password: str
    username: str
    def __init__(self, user_id: _Optional[int] = ..., token: _Optional[str] = ..., password: _Optional[str] = ..., username: _Optional[str] = ...) -> None: ...

class SetRoleRequest(_message.Message):
    __slots__ = ("user_id", "role_id", "token")
    USER_ID_FIELD_NUMBER: _ClassVar[int]
    ROLE_ID_FIELD_NUMBER: _ClassVar[int]
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    user_id: int
    role_id: int
    token: str
    def __init__(self, user_id: _Optional[int] = ..., role_id: _Optional[int] = ..., token: _Optional[str] = ...) -> None: ...

class GetUserRequest(_message.Message):
    __slots__ = ("user_id", "token")
    USER_ID_FIELD_NUMBER: _ClassVar[int]
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    user_id: int
    token: str
    def __init__(self, user_id: _Optional[int] = ..., token: _Optional[str] = ...) -> None: ...

class GetUsersRequest(_message.Message):
    __slots__ = ("token",)
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    token: str
    def __init__(self, token: _Optional[str] = ...) -> None: ...

class LoginRequest(_message.Message):
    __slots__ = ("username", "password")
    USERNAME_FIELD_NUMBER: _ClassVar[int]
    PASSWORD_FIELD_NUMBER: _ClassVar[int]
    username: str
    password: str
    def __init__(self, username: _Optional[str] = ..., password: _Optional[str] = ...) -> None: ...

class LogoutRequest(_message.Message):
    __slots__ = ("token",)
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    token: str
    def __init__(self, token: _Optional[str] = ...) -> None: ...

class AuthorizeRequest(_message.Message):
    __slots__ = ("token",)
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    token: str
    def __init__(self, token: _Optional[str] = ...) -> None: ...

class UserResponse(_message.Message):
    __slots__ = ("user_id", "role_id", "username")
    USER_ID_FIELD_NUMBER: _ClassVar[int]
    ROLE_ID_FIELD_NUMBER: _ClassVar[int]
    USERNAME_FIELD_NUMBER: _ClassVar[int]
    user_id: int
    role_id: int
    username: str
    def __init__(self, user_id: _Optional[int] = ..., role_id: _Optional[int] = ..., username: _Optional[str] = ...) -> None: ...

class UsersResponse(_message.Message):
    __slots__ = ("user",)
    USER_FIELD_NUMBER: _ClassVar[int]
    user: _containers.RepeatedCompositeFieldContainer[UserResponse]
    def __init__(self, user: _Optional[_Iterable[_Union[UserResponse, _Mapping]]] = ...) -> None: ...

class LoginResponse(_message.Message):
    __slots__ = ("user_id", "token", "role_id")
    USER_ID_FIELD_NUMBER: _ClassVar[int]
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    ROLE_ID_FIELD_NUMBER: _ClassVar[int]
    user_id: int
    token: str
    role_id: int
    def __init__(self, user_id: _Optional[int] = ..., token: _Optional[str] = ..., role_id: _Optional[int] = ...) -> None: ...

class GetRolesRequest(_message.Message):
    __slots__ = ("token",)
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    token: str
    def __init__(self, token: _Optional[str] = ...) -> None: ...

class RoleResponse(_message.Message):
    __slots__ = ("role_id", "role_name")
    ROLE_ID_FIELD_NUMBER: _ClassVar[int]
    ROLE_NAME_FIELD_NUMBER: _ClassVar[int]
    role_id: int
    role_name: str
    def __init__(self, role_id: _Optional[int] = ..., role_name: _Optional[str] = ...) -> None: ...

class RolesResponse(_message.Message):
    __slots__ = ("role",)
    ROLE_FIELD_NUMBER: _ClassVar[int]
    role: _containers.RepeatedCompositeFieldContainer[RoleResponse]
    def __init__(self, role: _Optional[_Iterable[_Union[RoleResponse, _Mapping]]] = ...) -> None: ...

class DeletePhysicianRequest(_message.Message):
    __slots__ = ("user_id", "token")
    USER_ID_FIELD_NUMBER: _ClassVar[int]
    TOKEN_FIELD_NUMBER: _ClassVar[int]
    user_id: int
    token: str
    def __init__(self, user_id: _Optional[int] = ..., token: _Optional[str] = ...) -> None: ...
