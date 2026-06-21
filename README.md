# 🏥 CHU Management System

A web-based Hospital Management System developed using **Java EE (JEE)** and several **Software Design Patterns** to manage the organizational structure and daily operations of a University Hospital Center (CHU).

---

## 📌 Project Overview

This project was developed as part of a Software Engineering and Design Patterns course.

The system manages:

* Buildings and departments
* Medical and administrative staff
* Patients and sections
* Hospital services
* Appointments
* Department heads communication

The application follows a modular architecture and integrates multiple software design patterns to improve maintainability, scalability, and code reusability.

---

## 🚀 Technologies Used

* Java EE (JEE)
* JSP & Servlets
* JDBC
* MySQL
* Apache Tomcat
* HTML / CSS
* Eclipse IDE

---

## 🏗️ System Architecture

The CHU is composed of:

### Buildings

* Administration
* Emergency
* Laboratories
* Radiology
* Surgery
* Medical Specialties

### Staff

* Director
* Administrators
* Doctors
* Nurses
* Department Heads

### Services

* Medical Services
* Paramedical Services
* Emergency Services
* Radiology Services
* Laboratory Services

### Patients

* Patient registration
* Medical information management
* Section assignment

---

## 🎯 Design Patterns Implemented

### Singleton Pattern

Ensures a single database connection instance throughout the application.

### Factory Method Pattern

Used for creating different hospital building types dynamically.

### Composite Pattern

Allows grouping hospital services and service groups into a hierarchical structure.

### Mediator Pattern

Facilitates communication and coordination between department heads.

---

## ⚙️ Main Features

* Authentication system
* Admin dashboard
* Appointment management
* Building management (CRUD)
* Service management (CRUD)
* Department head management
* Patient management
* Internal communication between department heads
* Design pattern demonstrations

---

## 📂 Project Structure

```text
src/
├── controller/
├── dao/
├── model/
├── service/
├── mediator/
├── factory/
├── singleton/
└── composite/

WebContent/
├── jsp/
├── css/
└── images/
```

---

## 🖥️ Application Screens

### Home Page

* MediBook landing page

### Administrator Panel

* Manage buildings
* Manage appointments
* Manage services
* Manage department heads

### Department Head Dashboard

* Communication system using Mediator Pattern

### Service Management

* Composite Pattern implementation

### Building Management

* Factory Method implementation

---

## 🗄️ Database

The application uses a MySQL database to store:

* Buildings
* Services
* Patients
* Doctors
* Department Heads
* Appointments
* Sections

Database script:

```sql
application_chu.sql
```

---

## ▶️ Installation

### 1. Clone Repository

```bash
git clone https://github.com/Achraf-AR/chu-management-system.git
```

### 2. Import into Eclipse

* File → Import
* Existing Projects into Workspace

### 3. Configure Database

Create a MySQL database and import:

```sql
application_chu.sql
```

Update JDBC credentials in the project configuration.

### 4. Deploy

* Install Apache Tomcat
* Add project to server
* Run the application

---

## 👨‍💻 Author

**Achraf Adardor**

Computer Science Student

University Project – Java EE & Design Patterns

---

## 📄 License

This project is developed for educational purposes.
