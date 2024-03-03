import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CreatePatient.css';
import axios from 'axios';

const CreatePatient = () => {
  const [formData, setFormData] = useState({ name: '', surname: '', dob: '', bloodType: '' });
  const [response, setResponse] = useState(null);
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };



  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/v1/patients', {
        firstName: formData.name,
        lastName: formData.surname,
        dob: formData.dob,
        bloodType: formData.bloodType,
      }, {
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
      });
      const data = response.data;
      setResponse(data);
      navigate('/');
    } catch (error) {
      console.error('Error creating patient:', error);
      alert('Error creating patient');
    }
  };
  
  

  return (
    <form onSubmit={handleSubmit} className="create-patient-form">
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
  <button type="submit">Create Patient</button>
</form>

  );
};

export default CreatePatient;
