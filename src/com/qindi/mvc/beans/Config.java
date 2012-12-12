package com.qindi.mvc.beans;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;




public class Config {

	/**
	 * Title of application
	 */
	private static String 	title;	
	/**
	 * Path to event configuration file
	 */				
	private static String 	eventConfigFile;	
	/**
	 * Verbose level while parsing event configuration file
	 */
	private static int		eventConfigVerbose;
	private static boolean  breadcrumb;
	/**
	 * Path to log4j configuration file 
	 */
	private static String 	log4jConfigFile;	
	/**
	 *	User defined function to test database connectivity
	 */
	private static String 	dbConnectivityTester;	
	/**
	 * Database name
	 */
	private static String 	dbName;	
	/**
	 * Database access driver name
	 */
	private static String 	dbDriver;	
	/**
	 * Location of database
	 */
	private static String dbLocation;
	/**
	 * Database access user name
	 */
	private static String 	dbUsername;
	/**
	 * Database access password
	 */
	private static String 	dbPassword;
	/**
	 * Indicates whether to perform default database connection test or not
	 */
	private static boolean dbTest;
	
	public static void initConfiguration(String filePath) throws Exception
	{
		try
		{
			System.out.println(filePath);
			XMLConfiguration cfg = new XMLConfiguration(filePath);
			setTitle(cfg.getString("title"));
			setEventConfigFile(cfg.getString("event-file"));
			setEventConfigVerbose(cfg.getInt("event-file[@verbose]"));
			setBreadcrumb(cfg.getBoolean("event-file[@breadcrumb]"));
			setLog4jConfigFile(cfg.getString("log4j-config-file"));
			setDbConnectivityTester(cfg.getString("db-connectivity-tester"));
			setDbName(cfg.getString("database"));
			setDbPassword(cfg.getString("database[@password]"));
			setDbDriver(cfg.getString("database[@driver]"));
			setDbLocation(cfg.getString("database[@location]"));
			
			// TODO test database connection
		}
		catch (ConfigurationException e) {
			// TODO Auto-generated catch block
		
		}
	}	
	
	public static String getTitle() {
		return title;
	}
	public static void setTitle(String t) {
		t = trim(t);
		title = t == null? "qindi" : t;
	}
	public static String getEventConfigFile() {
		return eventConfigFile;
	}
	public static void setEventConfigFile(String e) {	
		e = trim(e);
		eventConfigFile = e;
	}
	public static int getEventConfigVerbose() {
		return eventConfigVerbose;
	}
	public static void setEventConfigVerbose(int val) {
		eventConfigVerbose = val;
	}	
	public static boolean isBreadcrumb() {
		return breadcrumb;
	}
	public static void setBreadcrumb(boolean breadcrumb) {
		Config.breadcrumb = breadcrumb;
	}

	public static String getLog4jConfigFile() {
		return log4jConfigFile;
	}
	public static void setLog4jConfigFile(String file) {
		log4jConfigFile = trim(file);
	}
	public static String getDbConnectivityTester() {
		return dbConnectivityTester;
	}
	public static void setDbConnectivityTester(String tester) {
		dbConnectivityTester = trim(tester);
	}
	public static String getDbName() {
		return dbName;
	}
	public static void setDbName(String name) {
		dbName = trim(name);
	}
	public static String getDbDriver() {
		return dbDriver;
	}
	public static void setDbDriver(String driver) {
		dbDriver = trim(driver);
	}
	public String getDbLocation() {
		return dbLocation;
	}
	public static void setDbLocation(String location) {
		dbLocation = trim(location);
	}
	public static String getDbUsername() {
		return dbUsername;
	}
	public void setDbUsername(String username) {
		dbUsername = trim(username);
	}
	public static String getDbPassword() {
		return dbPassword;
	}
	public static void setDbPassword(String password) {
		dbPassword = trim(password);
	}
	public static boolean isDbTest() {
		return dbTest;
	}
	public static void setDbTest(boolean val) {
		dbTest = val;
	}
	
	private static String trim(String val){
		return val == null? null : val.trim();
	}
	
}
