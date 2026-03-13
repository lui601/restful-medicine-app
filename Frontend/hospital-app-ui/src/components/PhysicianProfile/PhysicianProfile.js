import React, { useState } from "react";
import useToken from "../../hooks/useToken";
import { useEffect } from 'react';
import "./PhysicianProfile.css";


const PhysicianProfile = ({ userId }) => {

    const { token, setToken } = useToken();
    const initialValue = {
        idDoctor: "",
        idUser: "",
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        specialization: ""
      };

    const [user, setUser] = useState(initialValue);

    useEffect(() => {
        getPhysician();
      }, []);

    const getPhysician = () => {
        const requestOptions = {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
          };
        let url = `http://localhost:5000/api/medical-office/physicians?idUser=${userId}`;

        fetch(url, requestOptions)
            .then((response) => response.json())
            .then((data) => {
                setUser(data.physicians[0]);
            })
            .catch((err) => {
            console.log(err.message);
            });
    };

  return (
    <div>
      <h1>My Profile</h1>
      <div className="physician-profile-wrapper">
        <div>
          <p><strong>IdDoctor:</strong> {user.idDoctor}</p>
          <p><strong>First Name:</strong> {user.firstName}</p>
          <p><strong>Last Name:</strong> {user.lastName}</p>
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Phone Number:</strong> {user.phoneNumber}</p>
          <p><strong>Specialization:</strong> {user.specialization}</p>
        </div>
      </div>
    </div>
  );
};

export default PhysicianProfile;
