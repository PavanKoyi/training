CREATE TABLE student (
	sno        INT           NOT NULL,
	rgno       INT           PRIMARY KEY,
	`name`     VARCHAR(20)   NOT NULL,
	college    VARCHAR(10)   NOT NULL,
	yop        INT     	    NOT NULL,
	address    VARCHAR(10)   NOT NULL
);


CREATE TABLE faculty (
	id          INT          PRIMARY KEY,
	`name`      VARCHAR(20)  NOT NULL,
	domain      VARCHAR(10)  NOT NULL,
	designation  VARCHAR(50) NOT NULL	
);


CREATE TABLE project (
	sno          INT         NOT NULL,
	proj_id      VARCHAR(4)  NOT NULL,
	proj_title   VARCHAR(20) NOT NULL,
	guide_id     INT,
	student_id   INT, 
	due_date     DATE,  
	             KEY k_project_faculty (guide_id),
	             CONSTRAINT fk_project_faculty 
					 FOREIGN KEY (guide_id) REFERENCES faculty (id),
					 KEY k_project_student (student_id),
	             CONSTRAINT fk_project_student 
					 FOREIGN KEY (student_id) REFERENCES student(rgno)
);


INSERT INTO student (sno,rgno,`name`,college,yop,address)
VALUES (1,401,'Pavan Kumar','KITS',2018,'Guntur'),
       (2,402,'Hemanth Kumar','KITS',2018,'Hyderabad'),
       (3,403,'Anil Kumar','KITS',2018,'Hyderabad'),
       (4,404,'Lohith Kumar','KITS',2018,'Nandhyala'),
       (5,405,'Janam Venkat','KITS',2018,'Guntur'),
       (6,406,'Vivek ','KITS',2018,'Bangalore'),
       (7,407,'Sumanth','KITS',2018,'Perecharla');
       

INSERT INTO faculty (id,`name`,domain,designation)
VALUES (101,'Sarath','Networks','AssocProf'),
		 (102,'Tirumala','DICA','AssocProf'),
	  	 (103,'Ashok','Comm','Lecturer'),
		 (104,'Raju','VLSI','Prof'),
		 (105,'Narayana','STLD','Prof'),
		 (106,'Aadinarayana','SS','HOD-ECE'),
		 (107,'Kalpana','Maths','HOD-S&H');
		      

INSERT INTO project (sno,proj_id,proj_title,guide_id,student_id,due_date)
VALUES (1,'PJ1','Automation',101,401,20180718);

INSERT INTO project (sno,proj_id,proj_title,guide_id,student_id,due_date)
VALUES (3,'PJ3','Developement',103,402,20180812),
       (4,'PJ4','HFSS Simulation',104,406,20180718),
       (5,'PJ5','Aurdiuno',105,407,20180718),
       (6,'PJ6','Irriogation',106,403,20180718),
       (7,'PJ7','VLSI',107,404,20180718),
       (2,'PJ2','RF Freq',102,405,20180718);
		      
		      

SELECT sno,rgno,`name`,college,yop,address 
  FROM student;	
SELECT id,`name`,domain,designation 
  FROM faculty;
SELECT sno,proj_id,proj_title,guide_id,student_id,due_date 
  FROM project;
 
 
 
 -- Get the details of the student that doing project under the guide Aadinarayana (Without joins)
SELECT  s.rgno, s.`name`,p.proj_id,p.proj_title,f.id,f.`name`,f.designation 
  FROM faculty AS f , 
       student AS s , 
		 project AS p
 WHERE s.rgno= p.student_id 
   AND f.id=p.guide_id 
	AND f.`name`='Aadinarayana';



 -- Get the details of the student that doing project under the guide Aadinarayana (With joins)

SELECT s.rgno,s.`name`,p.proj_id,p.proj_title,f.id,f.`name`,f.designation  
  FROM project AS p 
       INNER JOIN student AS s 
		 ON s.rgno= p.student_id 
       INNER JOIN faculty AS f 
		 ON f.id=p.guide_id AND f.id=106;

