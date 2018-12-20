CREATE TABLE edu_university(
     univ_code        CHAR(4)      PRIMARY KEY, 
     university_name  VARCHAR(100) NOT NULL
);

    
CREATE TABLE edu_department(
      dept_code       CHAR(4)      PRIMARY KEY , 
      dept_name       VARCHAR(50)  NOT NULL , 
      univ_code       CHAR(4),
                      KEY k_edu_department_edu_university (univ_code),                         
                      CONSTRAINT fk_edu_department_edu_university 
                      FOREIGN KEY (univ_code) REFERENCES edu_university (univ_code) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE edu_college(
    id                INT           PRIMARY KEY, 
    `code`            CHAR(4)       NOT NULL, 
    `name`            VARCHAR(100) NOT NULL, 
    univ_code         CHAR(4), 
    city              VARCHAR(50)   NOT NULL, 
    state             VARCHAR(50)   NOT NULL, 
    year_opened        YEAR(4)       NOT NULL,   
                      KEY  k_edu_college_edu_university (univ_code),
                      CONSTRAINT fk_edu_college_edu_university 
                      FOREIGN KEY (univ_code) REFERENCES edu_university (univ_code)    ON DELETE NO ACTION ON UPDATE NO ACTION         
);  

                     
CREATE TABLE edu_college_department(
    cdept_id        INT            PRIMARY KEY , 
    udept_code      CHAR(4) , 
    college_id      INT ,
                    KEY k_edu_college_department_edu_department (udept_code),
                    CONSTRAINT fk_edu_college_department_edu_department 
                    FOREIGN KEY (udept_code) REFERENCES edu_department(dept_code) ON DELETE NO ACTION ON UPDATE NO ACTION,
                    KEY k_edu_college_department_edu_college (udept_code),
                    CONSTRAINT fk_edu_college_department_edu_college 
                    FOREIGN KEY (college_id) REFERENCES edu_college(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);              



CREATE TABLE edu_designation(
     id               INT            PRIMARY KEY,
     `name`           VARCHAR(30)    NOT NULL,
     rank             CHAR(1)        NOT NULL                                        
);        

                                
CREATE TABLE edu_employee(
    id                INT          PRIMARY KEY,
    `name`            VARCHAR(100) NOT NULL,
    dob               DATE         NOT NULL,
    email             VARCHAR(50)  NOT NULL,
   phone              BIGINT       NOT NULL,
    college_id        INT ,
    cdept_id          INT ,
    desig_id          INT ,
                      KEY k_edu_employee_edu_college (college_id),
                      CONSTRAINT fk_edu_employee_college_id 
                      FOREIGN KEY (college_id) REFERENCES edu_college(id),
                      KEY k_edu_employee_edu_college_department (cdept_id),
                      CONSTRAINT fk_edu_employee_cdept_id 
                      FOREIGN KEY (cdept_id) REFERENCES edu_college_department(cdept_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
                      KEY k_edu_employee_edu_designation (desig_id),
                      CONSTRAINT fk_edu_employee_desig_id 
                      FOREIGN KEY (desig_id) REFERENCES edu_designation(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);          
                                 

CREATE TABLE edu_syllabus(
    id               INT          PRIMARY KEY,
    cdept_id         INT,
    syllabus_code    CHAR(4)      NOT NULL,
    syllabus_name    VARCHAR(100) NOT NULL,
                     KEY k_edu_syllabus_edu_college_department (cdept_id),
                     CONSTRAINT fk_edu_syllabus_cdept_id 
                     FOREIGN KEY (cdept_id) REFERENCES edu_college_department(cdept_id) ON DELETE NO ACTION ON UPDATE NO ACTION                                  
);  
                             

CREATE TABLE edu_professor_syllabus(
     emp_id          INT,
     syllabus_id     INT,
     semister        TINYINT      NOT NULL,
                     KEY k_edu_professor_syllabus_edu_employee (emp_id),
                     CONSTRAINT fk_edu_professor_syllabus_edu_employee 
                     FOREIGN KEY (emp_id) REFERENCES edu_employee(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
                     KEY k_edu_professor_syllabus_edu_syllabus (syllabus_id),
                     CONSTRAINT fk_edu_profsyl_syllabus_id 
                     FOREIGN KEY (syllabus_id) REFERENCES edu_syllabus(id)  ON DELETE NO ACTION ON UPDATE NO ACTION                             
);  
    
                                     
CREATE TABLE edu_student(
   id               INT          PRIMARY KEY,
   roll_number      CHAR(8)      NOT NULL,
   `name`           VARCHAR(100) NOT NULL,
   dob              DATE         NOT NULL,
   gender           CHAR(1)      NOT NULL,
   email            VARCHAR(50)  NOT NULL,
   phone            BIGINT       NOT NULL,
   address          VARCHAR(200) NOT NULL,
   cdept_id         INT,
   college_id       INT,  
                    KEY k_edu_student_edu_college_department (cdept_id),
                          CONSTRAINT fk_edu_student_edu_college_department 
                          FOREIGN KEY (cdept_id) REFERENCES edu_college_department(cdept_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
                          KEY k_edu_student_edu_college (college_id),
                          CONSTRAINT fk_edu_student_college_id 
                          FOREIGN KEY (college_id) REFERENCES edu_college(id) ON DELETE NO ACTION ON UPDATE NO ACTION                                                                
);  
                                                        
                                
CREATE TABLE edu_semester_fee(
   cdept_id        INT,
   stud_id         INT,
   semester        TINYINT       NOT NULL,
   amount          DOUBLE(18,2)  NULL,
   paid_year       YEAR(4)       NULL,
   paid_status     VARCHAR(10)   NOT NULL,
                   KEY k_edu_semester_fee_edu_college_department (cdept_id),
                   CONSTRAINT fk_edu_semester_fee_edu_college_department 
                   FOREIGN KEY (cdept_id) REFERENCES edu_college_department(cdept_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
                   KEY k_edu_semester_fee_edu_student (stud_id),
                   CONSTRAINT fk_edu_semester_fee_stud_id 
                   FOREIGN KEY (stud_id) REFERENCES edu_student(id) ON DELETE NO ACTION ON UPDATE NO ACTION   
);      
        
                                      
CREATE TABLE edu_semester_result(
   stud_id         INT,
    syllabus_id    INT,
    semester       TINYINT      NOT NULL,
    grade          VARCHAR(2)   NOT NULL,
    credits        FLOAT        NOT NULL,
    result_date    DATE         NOT NULL,
                   KEY k_edu_semester_result_edu_student (stud_id),
                   CONSTRAINT fk_edu_semester_result_edu_student 
                   FOREIGN KEY (stud_id) REFERENCES edu_student(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
                   KEY k_edu_semester_result_edu_syllabus (syllabus_id),
                   CONSTRAINT k_edu_semester_result_edu_syllabus 
                   FOREIGN KEY (syllabus_id) REFERENCES edu_syllabus(id) ON DELETE NO ACTION ON UPDATE NO ACTION  
);                                                                                          

                                                                                                                                                                                                    