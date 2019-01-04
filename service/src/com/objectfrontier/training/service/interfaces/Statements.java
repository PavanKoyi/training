package com.objectfrontier.training.service.interfaces;

public interface Statements {

	StringBuilder PERSON_CREATE_STATEMENT = new StringBuilder()
									.append("INSERT INTO service_person (first_name	         ")
									.append(" 						   , last_name			 ")
									.append("                          , email				 ")
									.append("                          , birth_date			 ")
									.append("                          , address_id			 ")
									.append("						   , created_date)       ");

	StringBuilder PERSON_UPDATE_STATEMENT = new StringBuilder()
									.append("UPDATE service_person						")
									.append("  SET 				  	  first_name =  ?	")
									.append("       				, last_name=    ?	")
									.append("       				, email =       ?	")
									.append("       				, birth_date =  ?	")
									.append(" WHERE 				, id = 		    ?	");

	StringBuilder PERSON_READ_STATEMENT = new StringBuilder()
									.append("SELECT  id			  ")
									.append("	   , first_name	  ")
									.append("	   , last_name	  ")
									.append("	   , email		  ")
									.append("	   , address_id	  ")
									.append("	   , birth_date	  ")
									.append("  FROM service_person")
									.append(" WHERE id = ?		  ");

	StringBuilder PERSON_READALL_STATEMENT =  new StringBuilder()
									.append("SELECT  id				")
									.append("		, first_name	")
									.append("		, last_name		")
									.append("		, email			")
									.append("		, address_id	")
									.append("		, birth_date 	")
									.append(" FROM service_person	");

	StringBuilder PERSON_DELETE_STATEMENT =   new StringBuilder()
									.append("DELETE 				")
									.append("  FROM service_person	")
									.append(" WHERE id = ?			");

	StringBuilder ADDRESS_CREATE_STATEMENT =  new StringBuilder()
									.append("INSERT INTO service_address  (street	 	  	  ")
									.append("							   , city,		 	  ")
									.append("							   , postal_code) 	  ")
									.append("							   , VALUES (?, ?, ?) ");

	StringBuilder ADDRESS_UPDATE_STATEMENT =  new StringBuilder()
									.append("UPDATE service_address   	")
									.append("   SET street = 		? 	")
									.append("	  , city = 		? 		")
									.append("	  , postal_code = ? 	")
			                        .append(" WHERE id = 			? 	");

	StringBuilder ADDRESS_READ_STATEMENT =  new StringBuilder()
									.append("SELECT id				")
									.append("	  , street			")
									.append("	  , city			")
									.append("	  , postal_code 	")
									.append("  FROM service_address ")
									.append(" WHERE id = ?			");

	StringBuilder ADDRESS_READALL_STATEMENT =  new StringBuilder()
									.append("SELECT id				")
									.append("	 , street			")
									.append("	 , city				")
									.append("	 , postal_code 		")
									.append("  FROM service_address	");

	StringBuilder ADDRESS_DELETE_STATEMENT =   new StringBuilder()
									.append("DELETE 				" )
									.append("  FROM service_address " )
									.append(" WHERE id = ?			" );

	StringBuilder NAME_VAIODATION_STATEMENT =  new StringBuilder()
									.append("SELECT first_name					 	 ")
									.append("		, last_name 				 	 ")
									.append("  FROM service_person 				 	 ")
									.append(" WHERE (service_person.first_name = ? 	 ")
									.append("		&& service_person.last_name = ?) ");

	StringBuilder EMAIL_VALIDATION_STATEMENT =  new StringBuilder()
									.append("SELECT email 			 		 ")
									.append("  FROM service_person 	 		 ")
									.append(" WHERE service_person.email = ? ");

	StringBuilder ADDRESS_SEARCH_STATEMENT =  new StringBuilder()
									.append("SELECT * 				 ")
									.append("  FROM service_address  ")
									.append(" WHERE street LIKE ?	 ");

}
