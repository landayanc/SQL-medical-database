--Query that takes the average of patients' ages:

SELECT 'Average age of Patients is ', AVG(PATIENT_AGE)
FROM PATIENT ;

--Query that takes the maximum and minimum value of patients' ages:

SELECT 'Minimum and Maximum value for ages', MIN(PATIENT_AGE), MAX(PATIENT_AGE)
FROM PATIENT;

--Query that calculates variance and standard deviation of patients' ages:

SELECT 'Variance and Standard Deviation value for ages', STDDEV(PATIENT_AGE)
FROM PATIENT;

--Group the number of patients of each doctor by employee number:

SELECT DOCTOR, COUNT(PATIENT_NAME) AS PATIENTS_REGISTERED
FROM PATIENT
GROUP BY DOCTOR
;

--Group of number of patients that live in the same household;
SELECT PATIENT_LOCATION, COUNT(PATIENT_PHONE_NUMBER) AS PATIENTS_IN_ONE_HOUSEHOLD
FROM PATIENT
GROUP BY PATIENT_LOCATION
;
