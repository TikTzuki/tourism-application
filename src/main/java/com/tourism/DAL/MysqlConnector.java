package com.tourism.DAL;

import java.sql.*;
import java.util.logging.Logger;

import com.tourism.ProjectProperties;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
public class MysqlConnector extends Connector {
  private static Logger logger = Logger.getLogger(MysqlConnector.class.getName());
  public static int count = 0; 

  public MysqlConnector() {
	super();
    this.host = ProjectProperties.getProperties("mysql.host");
    this.userName = ProjectProperties.getProperties("mysql.user");
    this.password = ProjectProperties.getProperties("mysql.password");
    this.database = ProjectProperties.getProperties("mysql.database");
  }

  public void getConnect() {
    	String url = "jdbc:mysql://" + this.host + "/" + this.database;
        try {
          connection = DriverManager.getConnection(url, this.userName, this.password);
        } catch (Exception e) {
        	e.printStackTrace();
        }
  }
 
	@Override
	public void setStatement() {
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
  public int executeUpdate(String query) {
    int res = Integer.MIN_VALUE;
    try {
    	this.getConnect();
    	this.setStatement();
    	this.statement.execute(query);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.closeConnection();
    }
    return res;
  }
  
  public ResultSet executeQuery(String query) {
	ResultSet rs = this.resultSet;
    try {
    	this.getConnect();
    	this.setStatement();
    	rs = this.statement.executeQuery(query);
    } catch (Exception e) {
    	e.printStackTrace();
    }
    return rs;
  }

  @Override
  public void closeConnection() {
    try {
      if (connection != null ) {
        connection.close();
      }
      
      if(this.statement != null ) {
    	  this.statement.close();
      }
      
      if(this.resultSet!=null) {
    	  this.resultSet.close();
      }
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    Connector connector = new MysqlConnector();
    connector.getConnect();
  }
}
