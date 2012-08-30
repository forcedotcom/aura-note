package org.plumeframework.demo.notes;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.fulltext.FullText;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataStore {
	
	private static final DataStore INSTANCE = new DataStore();
	private static final String DB_URL = "jdbc:h2:file:~/plumenote.db;IGNORECASE=TRUE";
	
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
		
		JdbcDatabaseConnection connection = (JdbcDatabaseConnection)connectionSource.getReadWriteConnection();
		try{
			Connection jdbcConnection = connection.getInternalConnection();
			
			try{
				FullText.createIndex(jdbcConnection, "PUBLIC", "NOTE", "TITLE,BODY");
			}catch(Exception e){
				//Probably already exists.
			}
			
		}finally{
			connectionSource.releaseConnection(connection);
		}
	}
	
	public ConnectionSource getConnectionSource() throws SQLException {
		return connectionSource;
	}
	
	public Dao<Note, Long> getNoteDao() throws SQLException{
		
		return DaoManager.createDao(getConnectionSource(), Note.class);
	}
	
	public Connection getConnection() throws SQLException{
		ConnectionSource connectionSource = getConnectionSource();
		return ((JdbcDatabaseConnection)connectionSource.getReadWriteConnection()).getInternalConnection();
	}

}
