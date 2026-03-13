import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import useToken from "../../hooks/useToken";
import { Calendar } from "primereact/calendar";
import { useNavigate } from "react-router-dom";
import { Toast } from "primereact/toast";
import "./PhysicianDetails.css";

const PhysicianDetails = ({ userId }) => {
  const navigate = useNavigate();
  const toast = useRef(null);
  const { token, setToken } = useToken();
  const params = useParams();
  const idDoctor = params.idDoctor;
  const [physician, setPhysician] = useState({});
  const [date, setDate] = useState(null);

  let now = new Date();
  now.setMinutes(now.getMinutes() + 15);
  now = new Date(now);

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
    getPhysician();
  }, []);

  const createAppointment = (cnp) => {
    var datestring =
      ("0" + date.getDate()).slice(-2) +
      "-" +
      ("0" + (date.getMonth() + 1)).slice(-2) +
      "-" +
      date.getFullYear() +
      " " +
      ("0" + date.getHours()).slice(-2) +
      ":" +
      ("0" + date.getMinutes()).slice(-2) +
      ":00";

    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        idPatient: cnp,
        idDoctor: idDoctor,
        dateOfAppointment: datestring,
      }),
    };
    fetch(
      `http://localhost:5000/api/medical-office/appointments`,
      requestOptions
    )
      .then((response) => {
        if (response.status == 409) {
          // conflict
          showError(
            "The appointment overlaps with another one or you already scheduled an appointment with this doctor"
          );
        }

        console.log(response.body);
        if (response.status != 201) {
          throw new Error();
        }
        return response.json();
      })
      .then((data) => {
        showSuccess("Success");
        navigate("/patient-profile");
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const getPhysician = () => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    fetch(
      `http://localhost:5000/api/medical-office/physicians/${idDoctor}`,
      requestOptions
    )
      .then((response) => response.json())
      .then((data) => {
        setPhysician(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
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
        let cnp = data[0].cnp;
        createAppointment(cnp);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  return (
    <div className="physician-details-wrapper">
      <Toast ref={toast} />
      <h1>Physician Details</h1>
      <div>
        <p>
          <strong>ID:</strong> {physician.idDoctor}
        </p>
        <p>
          <strong>First Name:</strong> {physician.firstName}
        </p>
        <p>
          <strong>Last Name:</strong> {physician.lastName}
        </p>
        <p>
          <strong>Specialization:</strong> {physician.specialization}
        </p>
      </div>
      <br />
      <br />
      <h3>Create an appointment</h3>
      <div class="rules">
        <p>
          You cannot schedule more than one appointment per day with the same
          doctor.
        </p>
        <p>Appointments must not overlap.</p>
        <p>
          Appointments must be booked with a minimum 15-minute advance notice.
        </p>
      </div>

      <form onSubmit={handleSubmit}>
        <label>
          <p>Select date & time</p>
          <Calendar
            value={date}
            onChange={(e) => setDate(e.value)}
            minDate={now}
            showTime
            hourFormat="24"
          />
        </label>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  );
};

export default PhysicianDetails;
