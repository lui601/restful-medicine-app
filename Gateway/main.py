import json

import flask
import grpc
import requests
from flask import Flask, request, Response, abort
from flask_cors import CORS

import idm_pb2
import idm_pb2_grpc

app = Flask(__name__)
CORS(app)

MEDICAL_OFFICE_URL = "http://localhost:8080/api/medical-office"
MEDICAL_EXAMINATION_URL = "http://localhost:8081/api/medical-examination"

channel = grpc.insecure_channel('localhost:9090')
stub = idm_pb2_grpc.IdmServiceStub(channel)


# cererile care vin de la user catre medical-office trec pe aici
@app.route("/api/medical-office/<path:path>", methods=["GET", "POST", "PUT", "DELETE"])
def medical_office_proxy(path):
    # print(request.data)
    # print(request.headers)

    return forward_request(MEDICAL_OFFICE_URL, path, request)


# cererile care vin de la user catre medical-exam trec pe aici
@app.route("/api/medical-examination/<path:path>", methods=["GET", "POST", "PUT", "DELETE"])
def medical_examination_proxy(path):
    # print(request.data)
    # print(request.headers)

    return forward_request(MEDICAL_EXAMINATION_URL, path, request)


def forward_request(api_url, path, req):
    resp = None
    url = f"{api_url}/{path}"
    headers = {
        'content-type': 'application/json',
        'Authorization': request.headers['Authorization']
    }
    # print('-----')
    # print(headers)

    if req.method == "GET":
        resp = requests.get(url=url, params=req.args, headers=headers)
    elif req.method == "POST":
        resp = requests.post(url=url, data=req.data, headers=headers)
    elif req.method == "PUT":
        resp = requests.put(url=url, data=req.data, headers=headers)
    elif req.method == "DELETE":
        resp = requests.delete(url=url, headers=headers)

    return to_response(resp)


def to_response(resp):
    excluded_headers = ["content-encoding", "content-length", "transfer-encoding", "connection"]
    headers = [(name, value) for (name, value) in resp.raw.headers.items() if name.lower() not in excluded_headers]
    response = Response(resp.content, resp.status_code, headers)
    return response


def translate_grpc_error_to_http_status(e):
    code = e._state.code
    print(str(e))
    if code == grpc.StatusCode.UNAUTHENTICATED:
        abort(401)  # unauthorized -> login cu date incorecte
    elif code == grpc.StatusCode.PERMISSION_DENIED:
        abort(403)  # forbidden -> operatie nepermisa
    elif code == grpc.StatusCode.NOT_FOUND:
        abort(404)  # not found
    elif code == grpc.StatusCode.ALREADY_EXISTS:
        abort(409)  # conflict -> create user cu acelasi username
    else:
        abort(500)


@app.route("/api/login", methods=["POST"])
def login():
    try:
        request_body = request.get_json()

        loginRequest = idm_pb2.LoginRequest(
           
            username=request_body['username'],
            password=request_body['password'],
        )
        loginResponse = stub.Login(loginRequest)
        response = {
            'user_id': loginResponse.user_id,
            'token': loginResponse.token,
            'role_id': loginResponse.role_id
        }

        return json.dumps(response)
    except grpc._channel._InactiveRpcError as e:
        translate_grpc_error_to_http_status(e)


@app.route("/api/logout", methods=["POST"])
def logout():
    try:
        request_body = request.get_json()

        logoutRequest = idm_pb2.LogoutRequest(
            token=request_body['token']
        )
        stub.Logout(logoutRequest)
    except grpc._channel._InactiveRpcError as e:
        translate_grpc_error_to_http_status(e)

    return 'Logout'


# suprascriem metodele de create ca sa adaugam mai intai utilizatorii corespunzatori in idm
# mai intai cream utilizatorul in idm, luam idUser, apoi cream doctorul/pacientul in medical office
@app.route("/api/physicians", methods=["POST"])
def create_physician():
    try:
        request_body = request.get_json()

        # apel catre idm
        createUserRequest = idm_pb2.CreateUserRequest(
            username=request_body['username'],
            password=request_body['password'],
            token=request.headers['Authorization'].split(' ')[1]  # luam token: Bearer aadhbfhbakf
        )
        user_response = stub.CreatePhysician(createUserRequest)

        payload = {
            "idUser": f'{user_response.user_id}',
            "firstName": f"{request_body['firstName']}",
            "lastName": request_body['lastName'],
            "email": request_body['email'],
            "phoneNumber": request_body['phoneNumber'],
            "specialization": request_body['specialization']
        }

        headers = {
            'content-type': 'application/json',
            'Authorization': request.headers['Authorization']
        }

        # apel catre medical-office
        response = requests.post(f"{MEDICAL_OFFICE_URL}/physicians",
                                 json=payload,
                                 headers=headers)
        return to_response(response)
    except grpc._channel._InactiveRpcError as e:
        translate_grpc_error_to_http_status(e)


@app.route("/api/users/<idUser>", methods=["DELETE"])
def delete_physician(idUser):
    try:
        # apel catre idm
        deletePhysicianRequest = idm_pb2.DeletePhysicianRequest(
            user_id=int(idUser),
            token=request.headers['Authorization'].split(' ')[1]  # luam token: Bearer aadhbfhbakf
        )
        stub.DeletePhysician(deletePhysicianRequest)

        headers = {
            'content-type': 'application/json',
            'Authorization': request.headers['Authorization']
        }

        # apel catre medical-office
        response = requests.get(f"{MEDICAL_OFFICE_URL}/physicians?idUser={idUser}", headers=headers)
        physician = response.json()['physicians'][0]
        idDoctor = physician['idDoctor']

        response = requests.delete(f"{MEDICAL_OFFICE_URL}/physicians/{idDoctor}", headers=headers)
        return to_response(response)
    except grpc._channel._InactiveRpcError as e:
        translate_grpc_error_to_http_status(e)


@app.route("/api/patients/<cnp>", methods=["PUT"])
def create_patient(cnp):
    try:
        request_body = request.get_json()
        # cnp = request.view_args['cnp']

        # apel catre idm
        createUserRequest = idm_pb2.CreateUserRequest(
            username=request_body['username'],
            password=request_body['password'],
            # token=request.headers['Authorization'].split(' ')[1]  # luam token: Bearer aadhbfhbakf
        )
        user_response = stub.CreatePatient(createUserRequest)

        payload = {
            "idUser": f'{user_response.user_id}',
            "firstName": f"{request_body['firstName']}",
            "lastName": request_body['lastName'],
            "email": request_body['email'],
            "phoneNumber": request_body['phoneNumber'],
            "dateOfBirth": request_body['dateOfBirth'],
            "isActive": request_body['isActive']
        }

        headers = {
            'content-type': 'application/json'
        }

        # apel catre medical-office
        response = requests.put(f"{MEDICAL_OFFICE_URL}/patients/{cnp}", json=payload, headers=headers)
        return to_response(response)
    except KeyError as e:
        abort(400)
    except grpc._channel._InactiveRpcError as e:
        translate_grpc_error_to_http_status(e)


if __name__ == '__main__':
    app.run(debug=True, port=5000)  # default de la flask
