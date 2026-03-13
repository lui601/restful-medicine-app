import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import useToken from "../../hooks/useToken";
import { useEffect } from "react";
import "./Login.css";
import { Toast } from "primereact/toast";

export default function Login({ setToken, setUserId, setUserRole }) {
  const navigate = useNavigate();
  const { token } = useToken();
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const toast = useRef(null);

  useEffect(() => {
    if (token) {
      navigate("/home");
    }
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    };
    fetch(`http://localhost:5000/api/login`, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        let userId = data["user_id"];
        let token = data["token"];
        let userRole = data["role_id"];
        setUserId({
          userId,
        });
        setToken({
          token,
        });
        setUserRole({
          userRole,
        });
        showSuccess("Success");
        window.location.reload(true);
      })
      .catch((err) => {
        console.log(err.message);
        showError("Incorrect username or password");
      });
  };

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

  return (
    <div className="login-wrapper">
      <Toast ref={toast} />
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
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
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  );
}
