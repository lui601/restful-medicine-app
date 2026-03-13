import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import "./Register.css";
import { Toast } from "primereact/toast";

export default function Register() {
  const navigate = useNavigate();
  const [cnp, setCnp] = useState();
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [email, setEmail] = useState();
  const [phoneNumber, setPhoneNumber] = useState();
  const [dateOfBirth, setDateOfBirth] = useState();
  const toast = useRef(null);

  useEffect(() => {}, []);

  const showSuccess = (content) => {
    toast.current.show({
      severity: "success",
      summary: "Success",
      detail: content,
      life: 3000,
    });
  };

  const showError = (content) => {
    toast.current.show({
      severity: "error",
      summary: "Error",
      detail: content,
      life: 3000,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const requestOptions = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName,
        email: email,
        phoneNumber: phoneNumber,
        dateOfBirth: dateOfBirth,
        isActive: true,
      }),
    };
    fetch(`http://localhost:5000/api/patients/${cnp}`, requestOptions)
      .then((response) => {
        console.log(response);
        if (response.status == 400) {
          // bad request
          showError("All fields are required");
        } else if (response.status == 409) {
          // conflict
          showError("CNP , username, or email is already in use");
        } else if (response.status == 422) {
          // unprocessable entity
          showError("Email, phone number or date of birth is incorrect");
        }

        console.log(response.body);
        if (response.status != 200 && response.status != 201) {
          throw new Error();
        }
        return response.json();
      })
      .then((data) => {
        showSuccess("Success");
        navigate("/login");
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  return (
    <div className="register-wrapper">
      <Toast ref={toast} />
      <h1>Register</h1>
      <form onSubmit={handleSubmit}>
        <label>
          <p>Cnp</p>
          <input type="text" onChange={(e) => setCnp(e.target.value)} />
        </label>
        <label>
          <p>Username</p>
          <input type="text" onChange={(e) => setUserName(e.target.value)} />
        </label>
        <label>
          <p>Password</p>
          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <label>
          <p>First Name</p>
          <input type="text" onChange={(e) => setFirstName(e.target.value)} />
        </label>
        <label>
          <p>Last Name</p>
          <input type="text" onChange={(e) => setLastName(e.target.value)} />
        </label>
        <label>
          <p>Email</p>
          <input type="text" onChange={(e) => setEmail(e.target.value)} />
        </label>
        <label>
          <p>Phone number</p>
          <input type="text" onChange={(e) => setPhoneNumber(e.target.value)} />
        </label>
        <label>
          <p>Date of birth</p>
          <input type="text" onChange={(e) => setDateOfBirth(e.target.value)} />
        </label>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  );
}
