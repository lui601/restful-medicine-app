import React, { useState } from "react";
import useToken from "../../hooks/useToken";
import { useEffect } from "react";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";
import "./PatientProfile.css";

const PatientProfile = ({ userId }) => {
  const { token, setToken } = useToken();
  const initialValue = {
    cnp: "",
    idUser: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    dateOfBirth: "",
    isActive: "",
  };

  const [user, setUser] = useState(initialValue);
  const [appointments, setAppointments] = useState([]);
  const [selectedAppointment, setSelectedAppointment] = useState(null);
  const [displayDetails, setDisplayDetails] = useState(false);
  const [displayInvestigations, setDisplayInvestigations] = useState(false);
  const [selectedExamination, setSelectedExamination] = useState(null);

  useEffect(() => {
    getPatient();
  }, []);

  const getPatient = () => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/patients?idUser=${userId}`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        setUser(data[0]); // noi returnam un array cu un elem in medical-office (get patients cu query param iduser)
        getAppointments(data[0].cnp); // ca sa asteptam userul
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const getAppointments = (cnp) => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/patients/${cnp}/appointments`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        data.map((app) => {
          app["examinations"] = [];
          let url = `http://localhost:5000/api/medical-examination/patients/${cnp}/examinations?idDoctor=${app.idDoctor}&dateOfExamination=${app.dateOfAppointment}`;
          fetch(url, requestOptions)
            .then((response) => response.json())
            .then((data2) => {
              app["examinations"] = data2;
            })
            .catch((err) => {
              console.log(err.message);
            });
          return app;
        });
        setAppointments(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onHide = () => {
    setDisplayDetails(false);
  };

  const onHideInvestigations = () => {
    setDisplayInvestigations(false);
  };

  return (
    <div className="patient-profile-wrapper">
      <h1>My Profile</h1>
      <div>
        <p>
          <strong>Cnp:</strong> {user.cnp}
        </p>
        {/* <p><strong>Username:</strong> {user.username}</p> */}
        <p>
          <strong>First Name:</strong> {user.firstName}
        </p>
        <p>
          <strong>Last Name:</strong> {user.lastName}
        </p>
        <p>
          <strong>Email:</strong> {user.email}
        </p>
        <p>
          <strong>Phone Number:</strong> {user.phoneNumber}
        </p>
        <p>
          <strong>Date of Birth:</strong> {user.dateOfBirth}
        </p>
      </div>

      <h2>Appointments</h2>
      <ul>
        {appointments.map((appointment) => (
          <div>
            <Card
              title={appointment.dateOfAppointment}
              style={{ height: "8em", width: "20em" }}
            >
              <div>
                <Button
                  label="View Details"
                  onClick={() => {
                    setDisplayDetails(true);
                    setSelectedAppointment(appointment);
                  }}
                />
              </div>
            </Card>
            <br />
          </div>
        ))}
      </ul>
      <Dialog
        header="Appointment details"
        visible={displayDetails}
        style={{ width: "50vw" }}
        onHide={() => onHide()}
      >
        <h2>Appointment Details</h2>
        <h3>Id: {selectedAppointment?.id}</h3>
        <h3>Id Doctor: {selectedAppointment?.idDoctor}</h3>
        <h3>Id Patient: {selectedAppointment?.idPatient}</h3>
        <h3>Date of appointment: {selectedAppointment?.dateOfAppointment}</h3>
        <h3>Status: {selectedAppointment?.status}</h3>
        <h3>Examinations:</h3>
        <ul>
          {selectedAppointment?.examinations.map((examination) => (
            <div>
              <Card
                title={<div>{examination.diagnostic}</div>}
                style={{ height: "5em", width: "10em" }}
              >
                <div>
                  <Button
                    label="View Investigations"
                    onClick={() => {
                      setDisplayInvestigations(true);
                      setSelectedExamination(examination);
                    }}
                  />
                </div>
              </Card>
              <br />
            </div>
          ))}
        </ul>
      </Dialog>

      <Dialog
        header="Investigations"
        visible={displayInvestigations}
        style={{ width: "50vw" }}
        onHide={() => onHideInvestigations()}
      >
        <ul>
          {selectedExamination?.investigations.map((investigation) => (
            <div>
              <Card
                title={
                  <div>
                    {investigation.name} ({investigation.processingTime} min):{" "}
                    {investigation.result}
                  </div>
                }
                style={{ height: "5em", width: "20em" }}
              ></Card>
              <br />
            </div>
          ))}
        </ul>
      </Dialog>
    </div>
  );
};

export default PatientProfile;
