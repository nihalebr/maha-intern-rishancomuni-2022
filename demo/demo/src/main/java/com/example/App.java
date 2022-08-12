package com.example;

/**
 * Hello world!
 *
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.*;
  
public class App {
	
	  
	  public static void DateExtraction(String regex) throws SQLException,ClassNotFoundException {
	      Pattern pattern = Pattern.compile(regex);
	      String line;
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","postgres","Kamal2002*");
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS LOG( RequestedTime TEXT,"
        + "RequestedTime_in_ms TEXT,"
        + "ClientIp TEXT,"
        + "LocalPort TEXT,"
        + "RemotePort TEXT,"
        + "ServerHostName TEXT,"
        + "URL TEXT,"
        + "RequestLine TEXT,"
        + "Content_Type_incoming TEXT,"
        + "Content_Type_outgoing TEXT,"
        + "Referer TEXT,"
        + "Status TEXT,"
        + "TimeTaken_in_ms NUMERIC,"
        + "Size TEXT,"
        + "SessionId TEXT,"
        + "Protocol TEXT,"
        + "UserAgent TEXT);";
        stmt.execute(sql);
	
	      try {
	        //Reading the data present in a given file from FileReader using the BufferedReader.
	        //Here, the location is the path of the file. Kindly change the location to the correct location of the required file in your system. 
	        String location = "C:\\Users\\jsrk\\Desktop\\maha-intern-rishancomuni-2022\\log.txt"; 
	        BufferedReader lineReader = new BufferedReader(new FileReader(location));
	        //Reading the contents of the file line by line

	        while ((line = lineReader.readLine()) != null) {
	             //Matching each line with the regular expression pattern to check if there are any dates present.
	             Matcher matchPattern = pattern.matcher(line);
	             //Enters the while loop only if there is a match found.
                 int count = 0;
                 ArrayList<String> sList = new ArrayList<String>();
	             while(matchPattern.find()) {
                    count++;
	                 //Printing all the dates present in that line. 
	                // System.out.println(matchPattern.group());
                    sList.add(matchPattern.group());
	              }
                  sql = "INSERT INTO LOG VALUES(";
                  for(int j =0;j<sList.size();j++) {
                      sql = sql + "'"+  sList.get(j).substring(1, sList.get(j).length()-1) + "',";
                  }
                    sql = sql.substring(0, sql.length()-1) + ");";
                    System.out.println(sql);
                    stmt.execute(sql);
                  System.out.println(sql);
                
	         }
            }
	       catch (IOException e) {
	         // Occurs whenever an input or output operation is failed or interpreted. 
	         //For example, trying to read from a file that does not exist. 
	         System.out.println("Error: " + e.getMessage());
	       }
	   }
	   
	   public static void main(String[] args) throws SQLException,ClassNotFoundException {
	         //Regular Expression to recognize all the dates of the format 'dd/mm/yyyy' or 'dd-mm-yyyy'.       
	         String regex = "\"[^\"]*\"";
	         DateExtraction(regex);  

    }
	
}