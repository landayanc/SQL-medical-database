--Creating Entity Tables:

CREATE TABLE Medical_Clinic(
	Clinic_Name VARCHAR2(30),
Clinic_Number NUMBER(3) PRIMARY KEY,
	Clinic_Location VARCHAR2(30),
Clinic_Phone_Number NUMBER(10) UNIQUE
);


CREATE TABLE Doctor(
	Doctor_Name VARCHAR2(20),
Empl_Number NUMBER(6) PRIMARY KEY,
Ext_Number NUMBER(5) UNIQUE,
	Specialty VARCHAR2(20),
	Doctor_Location VARCHAR2(30) --Same as Medical Clinic Location
);


CREATE TABLE Patient(
	Patient_Phone_Number NUMBER(10) UNIQUE,
	OHIP VARCHAR2(12) PRIMARY KEY,
	Birthdate DATE,
	Patient_Age NUMBER(3)
	Patient_Name VARCHAR2(20),
	Patient_Location VARCHAR2(30)
);



--Creating Relationship Tables:

CREATE TABLE works_for(
	Start_Date DATE(8),
	Empl_Number NUMBER(5) PRIMARY KEY,
	Clinic_Number NUMBER(5)
);


CREATE TABLE patron_of(
	Register_Date DATE(8),
	OHIP VARCHAR2(12) PRIMARY KEY,
	Clinic_Number NUMBER(5)
);


CREATE TABLE patient_of (
	OHIP VARCHAR2(12) PRIMARY KEY,
	Empl_Number NUMBER(5)
);
