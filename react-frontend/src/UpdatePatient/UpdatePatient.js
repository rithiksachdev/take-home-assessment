import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './UpdatePatient.css';
import axios from 'axios';

const UpdatePatient = () => {
  const [formData, setFormData] = useState({ name: '', surname: '', dob: '', bloodType: '' });
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
      const payload = {
        firstName: formData.name,
        lastName: formData.surname,
        dob: formData.dob,
        bloodType: formData.bloodType,
      };
      
      const response = await axios.put(url, payload, {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
      });
      
      setResponse(response.data);
      navigate('/');
    } catch (error) {
      console.error('Error updating patient:', error);
      alert('Error updating patient');
    }
  };
  
  

  return (
    <form onSubmit={handleSubmit} className="update-patient-form">
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
  <label>
  Enter Patient's Blood Type:
  <input
    type="text"
    name="bloodType"
    value={formData.bloodType}
    onChange={handleChange}
    required
  />
</label>
  <button type="submit">Update Patient</button>
</form>

  );
};

export default UpdatePatient;
