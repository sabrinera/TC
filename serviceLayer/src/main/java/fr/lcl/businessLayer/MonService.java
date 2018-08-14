package fr.lcl.businessLayer;

import fr.lcl.businessLayer.infrastructure.DatabaseDriver;
import fr.lcl.businessLayer.infrastructure.PostgreSDriver;

public class MonService {

	DatabaseDriver databaseDriver;
	
	public void findAll() {
		databaseDriver = new PostgreSDriver();
		databaseDriver.getConnection();
		
		//reqûete sql;
	}
}
