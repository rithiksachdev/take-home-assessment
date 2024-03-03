import React, { useEffect, useState } from 'react';
import './PatientDBStyles.css'
const PatientsDB = () => {
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    setIsLoading(true);
    try {
      // Using jsonplaceholder's /users endpoint as a mock
      const response = await fetch('http://127.0.0.1:8080/api/v1/patients');
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const users = await response.json();
      console.log(users)
      // Transforming user data into mock patient data
      const patients = users.map(user => ({
        id: user.id,
        name: user.firstName, // Assuming first part of name is the given name
        surname: user.lastName, // Assuming second part of name is the surname
        dob: user.dob,
        bloodType: user.bloodType
      }));
      setData(patients);
    } catch (error) {
      setError(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="table-container">
      <table className="styled-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>DOB</th>
            <th>Blood Type</th>
            
          </tr>
        </thead>
        <tbody>
          {data.map(patient => (
            <tr key={patient.id}>
              <td>{patient.id}</td>
              <td>{patient.name}</td>
              <td>{patient.surname}</td>
              <td>{patient.dob}</td>
              <td>{patient.bloodType}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PatientsDB;
