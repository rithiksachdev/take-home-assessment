# Answer to the sample questions

## 1. How did you choose your design/architecture and what characteristics did you look for?

### Contextual Design/Architecture Choice:

Given the goal of developing a prototype server application for managing patient details accessible from multiple devices, including during medical procedures, I prioritized **cross-platform compatibility**, **scalability**, and **ease of setup** in my design choices.

#### Microservices Architecture:
I chose a microservices architecture because it offers **modularity**, making it easier to develop, deploy, and **scale specific functionalities independently**. This architecture is particularly beneficial in a hospital setting where different types of devices, including surgical robots' interfaces, may need to access patient data. Microservices can provide dedicated endpoints for **different devices**, ensuring efficient and secure data access.



## 2. Why did you choose the particular technology/framework/coding language?

### Technology/Framework/Language Choice



#### Spring Boot for Backend:

- **Rationale:** I opted for Spring Boot due to its robustness and ease of development. Spring Boot's vast ecosystem and auto-configuration capabilities allow for creating a microservices architecture with minimal external dependencies, which is crucial for ensuring that the prototype can run on Mac, Windows, and Linux without extensive setup. 

- **Benefits for the Context:** In a hospital setting, where reliability and data integrity are paramount, Spring Boot's mature ecosystem ensures the backend is stable and secure. Its support for various data access and security configurations simplifies the integration with hospital databases and patient information systems. The ability to quickly develop microservices that cater to different hospital devices, including robots, makes Spring Boot an excellent choice for this healthcare application.

#### MongoDB as Database:

- **Rationale:** I selected **MongoDB** for its flexibility in handling JSON-like, schema-less data, which is beneficial for the varied and complex data structures typically found in patient health records.
- **Benefits for the Context:** MongoDB's dynamic schema is particularly useful in a medical context where patient data formats can vary widely. The ease of setup and cross-platform compatibility aligns with the project's goal of minimal external dependencies, ensuring the database can easily integrate with our server application on any operating system.

#### React for Frontend:

- **Rationale:** **React** was chosen for its component-based architecture, which facilitates the development of dynamic and responsive user interfaces. Its ability to update the UI efficiently without reloading the page is crucial for a seamless user experience, particularly in time-sensitive medical settings.
- **Benefits for the Context:** The use of React allows for the creation of a flexible and accessible user interface that can adapt to various devices, from desktops to tablets and specialized medical equipment with web capabilities. This ensures that doctors and medical staff can access patient information quickly and reliably, even during procedures. React's wide adoption and community support also mean that the application can be easily maintained and extended with new features as needed.



## 3. **If you had more time for prototyping, what functionality would you want to add?**

   ### Additional Functionality:
   - Enhancing user authentication and authorization features, including role-based access control (Doctor, staff, patient).
   - Implementing real-time notifications for appointments, book appointment and medical updates.

## 4. If we wanted to deploy this to a real hospital setting, what features would we need to add and what other development activities would you want to do before declaring it ready?

   ### Features for Real Hospital Deployment:
   - Robust security measures to ensure compliance and protect patient data.
   - Comprehensive testing, including load testing and security testing.
   - Integration with existing hospital systems such as Electronic Health Records systems.
   - Implementation of logging and monitoring tools for tracking system performance.
   - Compliance with healthcare regulations and standards.

   ### Development Activities Before Deployment:
   
   - User acceptance testing (UAT) with healthcare professionals to gather feedback.
   - Security audit to identify and mitigate potential security risks.
   - Disaster recovery plan and backup procedures to ensure data integrity and availability.
   - Comprehensive documentation for system administrators and end-users.
