--Display table names and tablespace names:
SELECT tablespace_name, table_name from user_tables 2 ;

--Show values in the entity tables:

SELECT * FROM Medical_Clinic ;

SELECT * FROM Doctor ;

SELECT * FROM Patient ;

--Show values of relationship tables:

SELECT * FROM works_for ;

SELECT * FROM patron_of ;

SELECT * FROM patient_of ;

--Views created from entity tables:

CREATE VIEW RYERSON_DOCTOR AS (SELECT * FROM DOCTOR WHERE EMPL_NUMBER >=900000) ;

SELECT * FROM RYERSON_DOCTOR ;

CREATE VIEW ADULT_PATIENT AS (SELECT * FROM PATIENT WHERE PATIENT_AGE >=18) ;

SELECT * FROM ADULT_PATIENT ;
