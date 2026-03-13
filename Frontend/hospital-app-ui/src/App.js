import "./App.css";
import useToken from "./hooks/useToken";
import useUserId from "./hooks/useUserId";
import useUserRole from "./hooks/useUserRole";
import { useEffect } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Menu from "./components/Menu/Menu";

import classes from "primereact/resources/primereact.css";
import theme from "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import Login from "./components/Login/Login";
import Home from "./components/Home/Home";
import Register from "./components/Register/Register";
import PatientProfile from "./components/PatientProfile/PatientProfile";
import PhysicianProfile from "./components/PhysicianProfile/PhysicianProfile";
import ManagePhysicians from "./components/ManagePhysicians/ManagePhysicians";
import Physicians from "./components/Physicians/Physicians";
import PhysicianDetails from "./components/PhysicianDetails/PhysicianDetails";
import Appointments from "./components/Appointments/Appointments";
import Patients from "./components/Patients/Patients";

function App() {
  const { token, setToken } = useToken();
  const { userId, setUserId } = useUserId();
  const { userRole, setUserRole } = useUserRole();

  useEffect(() => {}, []);

  return (
    <div className="wrapper">
      <BrowserRouter>
        <Menu token={token} userRole={userRole} />
        <Routes>
          <Route
            path="/login"
            element={
              <Login
                setToken={setToken}
                setUserId={setUserId}
                setUserRole={setUserRole}
              />
            }
          />

          <Route path="/home" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/patient-profile"
            element={<PatientProfile userId={userId} />}
          />
          <Route
            path="/physician-profile"
            element={<PhysicianProfile userId={userId} />}
          />
          <Route
            path="/appointments"
            element={<Appointments userId={userId} />}
          />
          <Route path="/patients" element={<Patients userId={userId} />} />
          <Route path="/manage-physicians" element={<ManagePhysicians />} />
          <Route path="/physicians" element={<Physicians />} />
          <Route
            path="/physicians/:idDoctor"
            element={<PhysicianDetails userId={userId} />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
