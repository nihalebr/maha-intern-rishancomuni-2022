package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
/**
 * Hello world!
 *
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log2 {

  public static void DateExtraction(String regex)
    throws SQLException, ClassNotFoundException {
    Pattern pattern = Pattern.compile(regex);
    String line;
    Class.forName("org.postgresql.Driver");
    Connection con = DriverManager.getConnection(
      "jdbc:postgresql://localhost/postgres",
      "postgres",
      "!sHuman"
    );
    Statement stmt = con.createStatement();
    String sql =
      "CREATE TABLE IF NOT EXISTS LOG2(" +
      "ClientIp TEXT," +
      "URL TEXT," +
      "SessionId TEXT);";
    stmt.execute(sql);

    try {
      //Reading the data present in a given file from FileReader using the BufferedReader.
      //Here, the location is the path of the file. Kindly change the location to the correct location of the required file in your system.
      String location =
        "C:\\Users\\jsrk\\Desktop\\maha-intern-rishancomuni-2022\\log.txt";
      BufferedReader lineReader = new BufferedReader(new FileReader(location));
      ArrayList<ArrayList> data = new ArrayList<ArrayList>();
      //Reading the contents of the file line by line

      while ((line = lineReader.readLine()) != null) {
        //Matching each line with the regular expression pattern to check if there are any dates present.
        Matcher matchPattern = pattern.matcher(line);
        ArrayList<String> sList = new ArrayList<>();
        while (matchPattern.find()) {
          sList.add(matchPattern.group());
        }
        data.add(sList);
      }
      //  sorting load time in descending order
      for (int i = 0; i < data.toArray().length; i++) {
        for (int j = i; j < data.toArray().length; j++) {
          if (
            Integer.parseInt(
              data
                .get(i)
                .get(12)
                .toString()
                .substring(1, data.get(i).get(12).toString().length() - 1)
            ) <
            Integer.parseInt(
              data
                .get(j)
                .get(12)
                .toString()
                .substring(1, data.get(j).get(12).toString().length() - 1)
            )
          ) {
            ArrayList<String> temp = data.get(i);
            data.set(i, data.get(j));
            data.set(j, temp);
          }
        }
      }

      // inserting data into database
      for (int i = 0; i < data.toArray().length; i++) {
        ArrayList<String> temp = data.get(i);
        sql = "INSERT INTO LOG2 VALUES(";
        for (int j = 0; j < temp.size(); j++) {
          if (j == 2 || j == 6 || j == 14) sql =
            sql +
            "'" +
            temp.get(j).substring(1, temp.get(j).length() - 1) +
            "',";
        }
        sql = sql.substring(0, sql.length() - 1) + ");";
        System.out.println(sql);
        stmt.execute(sql);
      }
      lineReader.close();
    } catch (IOException e) {
      // Occurs whenever an input or output operation is failed or interpreted.
      //For example, trying to read from a file that does not exist.
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void main(String[] args)
    throws SQLException, ClassNotFoundException {
    //Regular Expression to recognize all the dates of the format 'dd/mm/yyyy' or 'dd-mm-yyyy'.
    String regex = "\"[^\"]*\"";
    DateExtraction(regex);
  }
}
