import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './DeletePatient.css';
import axios from 'axios';

const DeletePatient = () => {
  const [formData, setFormData] = useState({ name: '', surname: '', dob: ''});
  const [response, setResponse] = useState(null);
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };



const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const id = `${formData.name}${formData.surname}${formData.dob}`.toLowerCase();
    const url = `http://localhost:8080/api/v1/patients/${id}`;
    await axios.delete(url, {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    });
    // Assuming setResponse() updates some state to reflect deletion, you might not have a response to set.
    // setResponse(data); // Omitted since the method return type is void
    navigate('/');
  } catch (error) {
    console.error('Error deleting patient:', error);
    // Optionally handle the error more gracefully, update UI, etc.
    navigate('/');
  }
};

  return (
    <form onSubmit={handleSubmit} className="delete-patient-form">
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
      type="text"
      name="dob"
      value={formData.dob}
      onChange={handleChange}
      required
    />
  </label>
  <button type="submit">Delete Patient</button>
</form>
  );
};

export default DeletePatient;
