import React from "react";
import { Menubar } from "primereact/menubar";
import useToken from "../../hooks/useToken";
import useUserId from "../../hooks/useUserId";
import useUserRole from "../../hooks/useUserRole";
import "./Menu.css";
import { useNavigate } from "react-router-dom";

// import { FontAwesomeIcon } from '@fontawesome-free/react-fontawesome';
// import { faHome, faUsers, faUser, faSignOutAlt } from '@fontawesome=free/free-solid-svg-icons';

export default function Menu() {
  const navigate = useNavigate();
  const { setUserId } = useUserId();
  const { userRole, setUserRole } = useUserRole();
  const { token, setToken } = useToken();

  const adminRole = 1;
  const patientRole = 2;
  const physicianRole = 3;

  let menuItems = [];

  const unauthenticatedUserMenu = [
    {
      label: "Home",
      url: "/home",
    },
    {
      label: "Login",
      url: "/login",
    },
    {
      label: "Register",
      url: "/register",
    },
  ];

  const adminMenu = [
    {
      //label: <><FontAwesomeIcon icon={faUsers} />See physicians</>,
      label: "Manage Physicians",
      // aici buton de create in sectiune
      url: "/manage-physicians",
    },
    // eventual meniu de vazut pacienti
  ];

  const patientMenu = [
    {
      label: "Home",
      url: "/home",
    },
    {
      label: "Physicians",
      url: "/physicians",
    },
    // {
    //   label: "My calendar",
    //   url: "/calendar",
    // },
    {
      label: "My profile",
      url: "/patient-profile",
    },
  ];

  const physicianMenu = [
    {
      label: "Home",
      url: "/home",
    },
    {
      label: "My appointments",
      url: "/appointments",
    },
    {
      label: "My patients",
      url: "/patients",
    },
    {
      label: "My profile",
      url: "/physician-profile",
    },
  ];

  const onLogoutClick = () => {
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        token: token,
      }),
    };
    fetch(`http://localhost:5000/api/logout`, requestOptions)
      .then((response) => {
        setToken({ token: null });
        setUserId({ userId: null });
        setUserRole({ userRole: null });
        navigate("/home");
        window.location.reload(true);
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  const logoutMenu = {
    label: "Logout",
    command: () => onLogoutClick(),
  };

  switch (userRole) {
    case adminRole:
      menuItems = adminMenu;
      menuItems.push(logoutMenu);
      break;
    case patientRole:
      menuItems = patientMenu;
      menuItems.push(logoutMenu);
      break;
    case physicianRole:
      menuItems = physicianMenu;
      menuItems.push(logoutMenu);
      break;
    default:
      menuItems = unauthenticatedUserMenu;
  }

  return (
    <div>
      <div className="menu">
        <Menubar model={menuItems} className="menu" activeClassName="active" />
      </div>
    </div>
  );
}
