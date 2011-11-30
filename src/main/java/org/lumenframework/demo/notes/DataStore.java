package org.lumenframework.demo.notes;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

public class DataStore {
	
	private static DataStore INSTANCE = new DataStore();
	private static String DB_URL = "jdbc:h2:file:~/lumennote.db";
	
	private ConnectionSource connectionSource;
	
	public static DataStore getInstance(){
		return INSTANCE;
	}
	
	private DataStore(){
		try {
			connectDatabase();
			updateDatabase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void connectDatabase() throws SQLException{
		connectionSource = new JdbcPooledConnectionSource(DB_URL);
	}
	
	private void updateDatabase() throws SQLException{
		ConnectionSource connectionSource = getConnectionSource();
		TableUtils.createTableIfNotExists(connectionSource, Note.class);
	}
	
	public ConnectionSource getConnectionSource() throws SQLException {
		if (connectionSource == null) {
			// create a connection source to our database
			connectionSource = new JdbcPooledConnectionSource(databaseUrl);
		}

		return connectionSource;
	}

}
