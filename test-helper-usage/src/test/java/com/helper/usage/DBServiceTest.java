package com.helper.usage;

import com.helper.enumeration.DatabaseType;
import com.helper.repo.DatabaseServiceRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helper.DBRowColumn;
import com.helper.services.DatabaseService;

public class DBServiceTest {
	
	DatabaseService dbService;
	DatabaseServiceRepo repo = DatabaseServiceRepo.getInstance();
	String key = "sybase-db";
	
	@Before
	public void init(){
		dbService = repo.register(key, DatabaseType.SYBASE);
	}

	@Test
	public void testDbService(){
		DBRowColumn result = dbService.executeSingleResultQuery("SELECT * FROM student");
		System.out.println(result.getString("firstname"));
		System.out.println(result.getInt("id"));
	}
	
	@After
	public void teardown(){
		repo.remove(key);
	}
	
}
