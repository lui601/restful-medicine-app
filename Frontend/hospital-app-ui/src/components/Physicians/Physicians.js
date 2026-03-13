import React, { useState, useEffect } from "react";
import { Paginator } from "primereact/paginator";
import { Card } from "primereact/card";
import { Button } from "primereact/button";
import { RadioButton } from "primereact/radiobutton";
import { InputText } from "primereact/inputtext";
import { Link } from "react-router-dom";
import useToken from "../../hooks/useToken";
import { Dropdown } from "primereact/dropdown";
import { Dialog } from "primereact/dialog";
import "./Physicians.css";

export default function Physicians() {
  const { token, setToken } = useToken();
  const [physicians, setPhysicians] = useState([]);
  const [first, setFirst] = useState(0);
  const [rows, setRows] = useState(5);
  const [page, setPage] = useState(0);
  const [totalRecords, setTotalRecords] = useState(0);

  const [lastName, setLastName] = useState(null);
  const exactMatchOptions = [
    { name: "NO", value: false },
    { name: "YES", value: true },
  ];

  const [selectedExactMatch, setSelectedExactMatch] = useState(
    exactMatchOptions[0]
  );
  const specializations = [
    "any",
    "Cardiology",
    "Dermatology",
    "FamilyMedicine",
  ];
  const [selectedSpecialization, setSelectedSpecialization] = useState(
    specializations[0]
  );
  const [displayResults, setDisplayResults] = useState(false);

  const onSpecializationChange = (e) => {
    setSelectedSpecialization(e.value);
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

  const onFilterClick = () => {
    getPhysicians(page, rows);
  };

  const getPhysicians = (page, rows) => {
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
    let url = `http://localhost:5000/api/medical-office/physicians?page=${page}&itemsPerPage=${rows}`;

    if (lastName !== null) {
      url += `&lastName=${lastName}&exactMatch=${selectedExactMatch.value}`;
    }

    if (selectedSpecialization !== "any") {
      url += `&specialization=${selectedSpecialization}`;
    }

    fetch(url, requestOptions)
      .then((response) => response.json())
      .then((data) => {
        setPhysicians(data.physicians);
        setTotalRecords(data.items);
        setDisplayResults(true);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const onHide = () => {
    setDisplayResults(false);
  };

  return (
    <div>
      <div className="physicians-wrapper">
        <h2>Filters</h2>
        <h3>Last Name</h3>
        <InputText
          className="LastNameInput"
          placeholder="Last Name"
          onChange={(e) => setLastName(e.target.value)}
        />
        <h3>Exact match</h3>
        {exactMatchOptions.map((option) => {
          return (
            <div key={option.name} className="field-radiobutton">
              <RadioButton
                inputId={option.name}
                name="option"
                value={option}
                onChange={(e) => setSelectedExactMatch(e.value)}
                checked={selectedExactMatch.name === option.name}
              />
              <label htmlFor={option.name}>{option.name}</label>
            </div>
          );
        })}

        <h3>Specialization</h3>
        <Dropdown
          value={selectedSpecialization}
          options={specializations}
          onChange={onSpecializationChange}
          placeholder="Select Specialization"
        />
        <br />
        <br />
        <Button label="Filter physicians" onClick={onFilterClick} />
      </div>
      <Dialog
        header="Physicians"
        visible={displayResults}
        style={{ width: "50vw" }}
        onHide={() => onHide()}
      >
        <ul>
          {physicians.map((physician) => (
            <div>
              <Card
                title={
                  <Link to={`/physicians/${physician.idDoctor}`}>
                    {physician.lastName}
                  </Link>
                }
                subTitle={physician.firstName}
                style={{ height: "10em", width: "20em" }}
              >
                <p>Specialization: {physician.specialization}</p>
              </Card>
              <br />
            </div>
          ))}
        </ul>
        <Paginator
          first={first}
          rows={rows}
          totalRecords={totalRecords}
          rowsPerPageOptions={[1, 3, 5]}
          onPageChange={onPageChange}
        ></Paginator>
      </Dialog>
    </div>
  );
}
