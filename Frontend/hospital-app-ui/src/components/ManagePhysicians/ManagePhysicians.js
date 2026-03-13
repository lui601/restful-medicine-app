import React, { useState, useEffect, useRef } from "react";
import { Paginator } from "primereact/paginator";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { Splitter, SplitterPanel } from "primereact/splitter";
import useUserId from "../../hooks/useUserId";
import useToken from "../../hooks/useToken";
import "./ManagePhysicians.css";
import { Dropdown } from "primereact/dropdown";
import { Dialog } from "primereact/dialog";
import { Toast } from "primereact/toast";

export default function ManagePhysicians() {
  const { userId, setUserId } = useUserId();
  const toast = useRef(null);
  const { token, setToken } = useToken();

  const [physicians, setPhysicians] = useState([]);
  const [selectedPhysician, setSelectedPhysician] = useState(null);
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [email, setEmail] = useState();
  const [phoneNumber, setPhoneNumber] = useState();

  const specializations = ["Cardiology", "Dermatology", "FamilyMedicine"];
  const [specialization, setSpecialization] = useState(specializations[0]);

  const [first, setFirst] = useState(0);
  const [rows, setRows] = useState(5);
  const [page, setPage] = useState(0);
  const [totalRecords, setTotalRecords] = useState(0);

  const [displayDetails, setDisplayDetails] = useState(false);

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
    getPhysicians(page, rows);
  }, []);

  const onPageChange = (event) => {
    setFirst(event.first);
    setRows(event.rows);
    setPage(event.page);

    getPhysicians(event.page, event.rows);
  };

  const getPhysicians = (page, rows) => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/physicians?page=${page}&itemsPerPage=${rows}`;

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        setPhysicians(data.physicians);
        setTotalRecords(data.items);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onCreateClick = () => {
    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName,
        email: email,
        phoneNumber: phoneNumber,
        specialization: specialization,
      }),
    };
    fetch(`http://localhost:5000/api/physicians`, requestOptions)
      .then((response) => {
        console.log(response);
        if (response.status == 400) {
          // bad request
          showError("All fields are required");
        } else if (response.status == 409) {
          // conflict
          showError("Username, or email is already in use");
        } else if (response.status == 422) {
          // unprocessable entity
          showError("Email or phone number is incorrect");
        }

        console.log(response.body);
        if (response.status != 201) {
          throw new Error();
        }
        return response.json();
      })
      .then((_data) => {
        showSuccess("Success");
        getPhysicians(page, rows);
        setUserName("");
        setPassword("");
        setFirstName("");
        setLastName("");
        setEmail("");
        setPhoneNumber("");
        setSpecialization(specializations[0]);
        window.location.reload(true);
        ///////////////////////////////////////////////nu se goleste formul
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  ///////////////////sa se stearga si appointmenturile
  const onDeleteClick = (idUser) => {
    const requestOptions = {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    fetch(`http://localhost:5000/api/users/${idUser}`, requestOptions).then(
      () => {
        showSuccess("Success");
        getPhysicians(page, rows);
      }
    );
  };

  const onSpecializationChange = (e) => {
    setSpecialization(e.value);
  };

  const onHide = () => {
    setDisplayDetails(false);
  };

  return (
    <div>
      <Toast ref={toast} />
      <Splitter layout="horizontal">
        <SplitterPanel>
          <h2>Create New Physician</h2>
          <div className="manage-physicians-wrapper">
            <label>
              <p>Username</p>
              <input
                type="text"
                onChange={(e) => setUserName(e.target.value)}
              />
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
              <input
                type="text"
                onChange={(e) => setFirstName(e.target.value)}
              />
            </label>
            <label>
              <p>Last Name</p>
              <input
                type="text"
                onChange={(e) => setLastName(e.target.value)}
              />
            </label>
            <label>
              <p>Email</p>
              <input type="text" onChange={(e) => setEmail(e.target.value)} />
            </label>
            <label>
              <p>Phone number</p>
              <input
                type="text"
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </label>

            <h3>Specialization</h3>
            <Dropdown
              value={specialization}
              options={specializations}
              onChange={onSpecializationChange}
              placeholder="Select Specialization"
            />
            <br />
            <br />

            <Button label="Create" onClick={onCreateClick} />
          </div>
        </SplitterPanel>

        <SplitterPanel style={{ overflowY: "scroll" }}>
          <h2>Existing Physicians</h2>
          <div className="existing-physicians-wrapper">
            <ul>
              {/* pt fiecare physician avem un card cu informatii */}
              {physicians.map((physician) => (
                <div>
                  <Card
                    title={physician.lastName}
                    subTitle={physician.specialization}
                    style={{ height: "10em", width: "20em" }}
                  >
                    <div>
                      <Button
                        label="View Details"
                        onClick={() => {
                          setDisplayDetails(true);
                          setSelectedPhysician(physician);
                        }}
                      />
                      &emsp;
                      <Button
                        label="Delete"
                        onClick={() => {
                          onDeleteClick(physician.idUser);
                        }}
                      />
                    </div>
                  </Card>
                  <br />
                </div>
              ))}
            </ul>
          </div>

          <div className="paginator-wrapper">
            <Paginator
              first={first}
              rows={rows}
              totalRecords={totalRecords}
              rowsPerPageOptions={[1, 3, 5]}
              onPageChange={onPageChange}
            ></Paginator>
          </div>
        </SplitterPanel>
      </Splitter>

      <Dialog
        header="Appointment details"
        visible={displayDetails}
        style={{ width: "50vw" }}
        onHide={() => onHide()}
      >
        <h2>Physician Details</h2>
        <h3>First Name: {selectedPhysician?.firstName}</h3>
        <h3>Last Name: {selectedPhysician?.lastName}</h3>
        <h3>Specialization: {selectedPhysician?.specialization}</h3>
      </Dialog>
    </div>
  );
}
