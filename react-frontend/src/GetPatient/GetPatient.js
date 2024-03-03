import React, { useState } from 'react';
import './GetPatient.css';
import axios from 'axios';

const GetPatient = () => {
  const [formData, setFormData] = useState({ name: '', surname: '', dob: '' });
  const [patient, setPatient] = useState(null); // Correct for a single patient

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const url = `http://localhost:8080/api/v1/patients/${formData.name}/${formData.surname}/${formData.dob}`;
    try {
      const response = await axios.get(url, {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
      });
      console.log(response);
      setPatient(response.data); // Corrected to use setPatient for a single patient
    } catch (error) {
      console.error('Error fetching patient:', error);
      alert('Error fetching patient');
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit} className="get-patient-form">
        <label>
          Enter Patient's Name
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Enter Patient's Surname
          <input
            type="text"
            name="surname"
            value={formData.surname}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Enter Patient's DOB
          <input
            type="text" // Changed to type="date" for better date input handling
            name="dob"
            value={formData.dob}
            onChange={handleChange}
            required
          />
        </label>
        <button type="submit">Get Patient Details</button>
      </form>

      {patient && (
        <table className="patients-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Surname</th>
              <th>DOB</th>
              <th>Blood Type</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{patient.firstName}</td>
              <td>{patient.lastName}</td>
              <td>{patient.dob}</td>
              <td>{patient.bloodType}</td>
            </tr>
          </tbody>
        </table>
      )}
    </>
  );
};

export default GetPatient;
