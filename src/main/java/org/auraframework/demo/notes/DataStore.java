/*
 * Copyright (C) 2014 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.demo.notes;

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
	private static final String DB_URL = "jdbc:h2:~/auranote.db;AUTO_SERVER=TRUE;IGNORECASE=TRUE";
	
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
