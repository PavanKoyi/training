DESC test;


ALTER TABLE test   
  ADD (age INT);  --Adding a new column into table test.
  
  
UPDATE test 
   SET age = 10
 WHERE id=2;
	
	
UPDATE test 
   SET age = 20 
 WHERE id=1;

	
UPDATE test
   SET age = 30  \
 WHERE id=3;


UPDATE test 
   SET age = 50 
 WHERE id=4;


ALTER TABLE test 
  ADD (experience INT);
 DESC test;


ALTER TABLE  
 DROP (experience);


DELETE FROM test 
 WHERE id=4;


SELECT id, `name`,designation,age,experience  
  FROM test;
  
  
ALTER TABLE test 
 DROP COLUMN experience; --Dropping the experience column.  
 
 
SELECT id,`name`,designation,age 
  FROM test;

INSERT INTO test (id, `name`,designation,age)
VALUES (4,'janam','stu',24),
		 (5,'hemanth','bpo',22);
		 
		 
SELECT id,`name`,designation,age  
  FROM test;
  
  
ALTER TABLE test 
  ADD salary DOUBLE;  --Adding salary column to the table test.

INSERT INTO test (id,`name`,designation,age,salary)
VALUES (6,'vivek','HVE',24,35000),
		 (7,'lohith','NDL',25,10000);
		 
SELECT id,`name`,designation,age,salary 
  FROM test;

UPDATE test 
   SET salary= 19070 
 WHERE id=1;
 
 
UPDATE test 
   SET salary= 19070 
 WHERE id=2;
 
 
UPDATE test 
   SET salary= 19070 
WHERE id=3;


UPDATE test 
   SET salary= 33000
 WHERE id=4;
 
 
UPDATE test 
   SET salary= 9000  
 WHERE id=5;
 

SELECT id,`name`,designation,age,salary 
  FROM test 
 ORDER BY age ASC;


SELECT id,`name`,designation,age,salary
  FROM test
 ORDER BY age DESC;
 

SELECT id,`name`,designation,age,salary 
  FROM test 
 GROUP BY salary ;