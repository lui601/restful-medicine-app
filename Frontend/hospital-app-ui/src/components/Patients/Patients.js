import React, { useState } from "react";
import useToken from "../../hooks/useToken";
import { useEffect } from "react";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { Dropdown } from "primereact/dropdown";
import { Dialog } from "primereact/dialog";
import "./Patients.css";

const Patients = ({ userId }) => {
  const { token, setToken } = useToken();
  const [patients, setPatients] = useState([]);
  const [displayDetails, setDisplayDetails] = useState(false);
  const details = {
    cnp: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    dateOfBirth: "",
    appointments: [],
  };
  const [selectedPatient, setSelectedPatient] = useState(details);

  useEffect(() => {
    loadPatients();
  }, []);

  const loadPatients = () => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/physicians?idUser=${userId}`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        let idDoctor = data.physicians[0].idDoctor;
        getPatients(idDoctor);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const getPatients = (idDoctor) => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/physicians/${idDoctor}/patients`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        setPatients(
          data.map((patient) => {
            patient["appointments"] = [];
            let url = `http://localhost:5000/api/medical-office/patients/${patient.cnp}/appointments?idDoctor=${idDoctor}`;
            fetch(url, requestOptions)
              .then((response) => response.json())
              .then((data2) => {
                patient["appointments"] = data2;
              })
              .catch((err) => {
                console.log(err.message);
              });
            return patient;
          })
        );
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onHide = () => {
    setDisplayDetails(false);
  };

  const updateDetails = (patient) => {
    setDisplayDetails(true);
    setSelectedPatient(patient);
  };

  return (
    <div>
      <h1>Patients</h1>
      <div className="patients-wrapper">
        <ul>
          {patients.map((patient) => (
            <div>
              <Card
                title={
                  <div>
                    {patient.firstName} {patient.lastName}
                  </div>
                }
                style={{ height: "8em", width: "20em" }}
              >
                <div>
                  <Button
                    label="View Details"
                    onClick={() => {
                      updateDetails(patient);
                    }}
                  />
                </div>
              </Card>
              <br />
            </div>
          ))}
        </ul>

        <Dialog
          header="Patient details"
          visible={displayDetails}
          style={{ width: "50vw" }}
          onHide={() => onHide()}
        >
          <h2>Patient details</h2>
          <p>
            <strong>Cnp:</strong> {selectedPatient.cnp}
          </p>
          <p>
            <strong>First Name:</strong> {selectedPatient.firstName}
          </p>
          <p>
            <strong>Last Name:</strong> {selectedPatient.lastName}
          </p>
          <p>
            <strong>Email:</strong> {selectedPatient.email}
          </p>
          <p>
            <strong>Phone Number:</strong> {selectedPatient.phoneNumber}
          </p>
          <p>
            <strong>Date of Birth:</strong> {selectedPatient.dateOfBirth}
          </p>
          <p>
            <strong>Appointments:</strong>
          </p>
          <ul>
            {selectedPatient?.appointments.map((appointment) => (
              <div>
                <Card
                  title={<div>{appointment.dateOfAppointment}</div>}
                  style={{ height: "5em", width: "15em" }}
                >
                  <div>Status: {appointment.status}</div>
                </Card>
                <br />
              </div>
            ))}
          </ul>
        </Dialog>
      </div>
    </div>
  );
};

export default Patients;
