import React from 'react';
import Navbar from './Navbar/Navbar'; // Import the NavBar component
import PatientsDB from './PatientsDB'; // Import the PatientsDB component
import CreatePatient from './CreatePatient/CreatePatient';
import UpdatePatient from './UpdatePatient/UpdatePatient';
import DeletePatient from './DeletePatient/DeletePatient';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import Routes instead of Switch
import GetPatient from './GetPatient/GetPatient';

function App() {
  return (
    <Router>
      <Navbar />
      <Routes> {/* Use Routes instead of Switch */}
        <Route path="/" element={<PatientsDB />} /> {/* Update to use element prop */}
        {/* Update the rest of your routes to use the element prop */}
        <Route path="/create" element={<CreatePatient />} />
        <Route path="/update" element={<UpdatePatient/> }/>
        <Route path="/delete" element={<DeletePatient/>} />
        <Route path="/get" element={<GetPatient/>} />
      </Routes>
    </Router>
  );
}

export default App;