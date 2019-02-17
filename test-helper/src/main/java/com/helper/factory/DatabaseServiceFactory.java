package com.helper.factory;

import com.helper.enumeration.DatabaseType;
import com.helper.exception.HelperException;
import com.helper.services.DatabaseService;
import com.helper.services.MySqlDatabaseService;
import com.helper.services.OracleDatabaseService;
import com.helper.services.SqlServerDatabaseService;
import com.helper.services.SybaseDatabaseService;

public class DatabaseServiceFactory {

	public static DatabaseService getDatabaseService(DatabaseType type, String application){
		DatabaseService service = null;
		switch (type) {
		case SQLSERVER:
			service = new SqlServerDatabaseService(application);
			break;
		case MYSQL:
			service = new MySqlDatabaseService(application);
			break;
		case ORACLE:
			service = new OracleDatabaseService(application);
			break;
		case SYBASE:
			service = new SybaseDatabaseService(application);
			break;
		default:
			throw new HelperException("Unsupported Database Service");
		}
		return service;
	}
}
