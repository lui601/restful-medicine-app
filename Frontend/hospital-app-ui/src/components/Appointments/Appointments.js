import React, { useState, useRef } from "react";
import useToken from "../../hooks/useToken";
import { useEffect } from "react";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { Dropdown } from "primereact/dropdown";
import { Dialog } from "primereact/dialog";
import "./Appointments.css";
import { Toast } from "primereact/toast";

const Appointments = ({ userId }) => {
  const { token, setToken } = useToken();
  const [appointments, setAppointments] = useState([]);
  const [selectedAppointment, setSelectedAppointment] = useState(null);
  const [displayDetails, setDisplayDetails] = useState(false);
  const details = {
    cnp: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    dateOfBirth: "",
  };

  const [patient, setPatient] = useState(details);
  const [examinations, setExaminations] = useState([]);

  const statusList = [
    { name: "Honored", value: "Honored" },
    { name: "Cancelled", value: "Cancelled" },
    { name: "NoShow", value: "NoShow" },
  ];
  const [selectedStatus, setSelectedStatus] = useState(statusList[0]);
  const diagnosticList = [
    { name: "Healthy", value: "Healthy" },
    { name: "Ill", value: "Ill" },
  ];
  const [selectedDiagnostic, setSelectedDiagnostic] = useState(
    diagnosticList[0]
  );
  const [displayInvestigations, setDisplayInvestigations] = useState(false);
  const [selectedExamination, setSelectedExamination] = useState(null);

  // add new investigation
  const [name, setName] = useState(null);
  const [processingTime, setProcessingTime] = useState(null);
  const [result, setResult] = useState(null);
  const toast = useRef(null);

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

  useEffect(() => {
    loadAppointments();
  }, []);

  const loadAppointments = () => {
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
        // noi returnam un array cu un elem in medical-office (get physicians cu query param iduser)
        getAppointments(data.physicians[0].idDoctor); // ca sa asteptam doctorul
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const getAppointments = (idDoctor) => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/physicians/${idDoctor}/appointments`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        setAppointments(
          data.map((app) => {
            app["examinations"] = [];
            let url = `http://localhost:5000/api/medical-examination/patients/${app.idPatient}/examinations?idDoctor=${idDoctor}&dateOfExamination=${app.dateOfAppointment}`;
            fetch(url, requestOptions)
              .then((response) => response.json())
              .then((data2) => {
                app["examinations"] = data2;
              })
              .catch((err) => {
                console.log(err.message);
              });
            return app;
          })
        );
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const updateDetails = (appointment) => {
    setDisplayDetails(true);
    setSelectedAppointment(appointment);
    setSelectedStatus(appointment.status);

    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/patients/${appointment.idPatient}`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        setPatient(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onStatusChange = (e) => {
    setSelectedStatus(e.value);
  };

  const onDiagnosticChange = (e) => {
    setSelectedDiagnostic(e.value);
  };

  const onHide = () => {
    setDisplayDetails(false);
  };

  const onUpdateStatusClick = () => {
    const requestOptions = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        idPatient: selectedAppointment.idPatient,
        idDoctor: selectedAppointment.idDoctor,
        dateOfAppointment: selectedAppointment.dateOfAppointment,
        status: selectedStatus,
      }),
    };
    fetch(
      `http://localhost:5000/api/medical-office/appointments/${selectedAppointment.id}`,
      requestOptions
    )
      .then((response) => response.json())
      .then((data) => {
        selectedAppointment.status = selectedStatus;
        updateDetails(selectedAppointment);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onAddExaminationClick = () => {
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        idPatient: selectedAppointment.idPatient,
        idDoctor: selectedAppointment.idDoctor,
        dateOfExamination: selectedAppointment.dateOfAppointment,
        diagnostic: selectedDiagnostic,
      }),
    };
    fetch(
      `http://localhost:5000/api/medical-examination/examinations`,
      requestOptions
    )
      .then((response) => response.json())
      .then((data) => {
        selectedAppointment.examinations.push(data);
        updateDetails(selectedAppointment);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onHideInvestigations = () => {
    setDisplayInvestigations(false);
  };

  const deleteExamination = (examination) => {
    const requestOptions = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    };
    fetch(
      `http://localhost:5000/api/medical-examination/examinations/${examination.id}`,
      requestOptions
    )
      .then((response) => {
        let app = selectedAppointment;
        app.examinations = app.examinations.filter((e) => e != examination);
        setSelectedAppointment(app);
        updateDetails(selectedAppointment);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        name: name,
        processingTime: processingTime,
        result: result,
      }),
    };
    let url = `http://localhost:5000/api/medical-examination/examinations/${selectedExamination.id}/investigations`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        let app = selectedAppointment;
        app.examinations = app.examinations.filter(
          (e) => e != selectedExamination
        );
        app.examinations.push(data);
        setSelectedExamination(data);
        setSelectedAppointment(app);
        updateDetails(selectedAppointment);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const deleteInvestigation = (investigation) => {
    const requestOptions = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    };
    fetch(
      `http://localhost:5000/api/medical-examination/examinations/${selectedExamination.id}/investigations/${investigation.id}`,
      requestOptions
    )
      .then((response) => {
        let ex = selectedExamination;
        ex.investigations = ex.investigations.filter((i) => i != investigation);
        setSelectedExamination(ex);

        let app = selectedAppointment;
        app.examinations = app.examinations.filter(
          (e) => e != selectedExamination
        );
        app.examinations.push(ex);
        setSelectedAppointment(app);
        updateDetails(selectedAppointment);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const deleteAppointment = (appointment) => {
    const requestOptions = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    };
    fetch(
      `http://localhost:5000/api/medical-office/appointments/${appointment.id}`,
      requestOptions
    )
      .then((response) => {
        if (response.ok) {
          console.log("Appointment deleted successfully!");
          showSuccess("Success");
          window.location.reload(true);
        } else {
          console.log("Eroare");
          showError("Error");
        }
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  return (
    <div>
      <Toast ref={toast} />
      <h1>Appointments</h1>
      <div className="appointments-wrapper">
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
                      updateDetails(appointment);
                    }}
                  />
                  <Button
                    label="Delete"
                    onClick={() => {
                      deleteAppointment(appointment);
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
          <h2>Patient details</h2>
          <p>
            <strong>Cnp:</strong> {patient.cnp}
          </p>
          <p>
            <strong>First Name:</strong> {patient.firstName}
          </p>
          <p>
            <strong>Last Name:</strong> {patient.lastName}
          </p>
          <p>
            <strong>Email:</strong> {patient.email}
          </p>
          <p>
            <strong>Phone Number:</strong> {patient.phoneNumber}
          </p>
          <p>
            <strong>Date of Birth:</strong> {patient.dateOfBirth}
          </p>

          <h2>Appointment Details</h2>
          <p>
            <strong>Id:</strong> {selectedAppointment?.id}
          </p>
          <p>
            <strong>Id Doctor:</strong> {selectedAppointment?.idDoctor}
          </p>
          <p>
            <strong>Id Patient:</strong> {selectedAppointment?.idPatient}
          </p>
          <p>
            <strong>Date of appointment:</strong>{" "}
            {selectedAppointment?.dateOfAppointment}
          </p>
          <p>
            <strong>Status:</strong> {selectedAppointment?.status}
          </p>
          <div>
            <Dropdown
              value={selectedStatus}
              options={statusList}
              onChange={onStatusChange}
              optionLabel="name"
              placeholder="Select a new status"
            />
            <Button label="Update status" onClick={onUpdateStatusClick} />
          </div>
          <p>
            <strong>Examinations:</strong>
          </p>
          <Dropdown
            value={selectedDiagnostic}
            options={diagnosticList}
            onChange={onDiagnosticChange}
            optionLabel="name"
            placeholder="Select diagnostic"
          />
          <Button label="Add new examination" onClick={onAddExaminationClick} />
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
                    <Button
                      label="Delete"
                      onClick={() => {
                        deleteExamination(examination);
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
          <h3>Add an investigation</h3>
          <form onSubmit={handleSubmit}>
            <label>
              <p>Name</p>
              <input type="text" onChange={(e) => setName(e.target.value)} />
            </label>
            <label>
              <p>Processing time (minutes)</p>
              <input
                type="text"
                onChange={(e) => setProcessingTime(e.target.value)}
              />
            </label>
            <label>
              <p>Result</p>
              <input type="text" onChange={(e) => setResult(e.target.value)} />
            </label>
            <div>
              <button type="submit">Submit</button>
            </div>
          </form>
          <ul>
            {selectedExamination?.investigations.map((investigation) => (
              <div>
                <Card
                  title={
                    <div>
                      {investigation.name}: {investigation.result}
                    </div>
                  }
                  style={{ height: "5em", width: "10em" }}
                >
                  <Button
                    label="Delete"
                    onClick={() => {
                      deleteInvestigation(investigation);
                    }}
                  />
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

export default Appointments;
