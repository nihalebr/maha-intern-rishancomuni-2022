package com.example;

import java.sql.*;

public class log4_try {

  public static void MaxTime() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    Connection con = DriverManager.getConnection(
      "jdbc:postgresql://localhost/postgres",
      "postgres",
      "!sHuman"
    );
    Statement stmt = con.createStatement(
      ResultSet.TYPE_SCROLL_INSENSITIVE,
      ResultSet.CONCUR_READ_ONLY
    );
    String sql =
      "select distinct clientip,sessionid from log group by clientip,sessionid;";
    ResultSet rs = stmt.executeQuery(sql);
    while (rs.next()) {
      System.out.println(rs.getString(1) + " " + rs.getString(2));
    }
    rs.first();
    String qryHighTimeUrl =
      "select clientip,sessionid,url,timetaken_in_ms from log where clientip='" +
      rs.getString(1) +
      "' and sessionid='" +
      rs.getString(2) +
      "' order by timetaken_in_ms desc;";
    System.out.println(qryHighTimeUrl);
    ResultSet rs1 = stmt.executeQuery(qryHighTimeUrl);
    while (rs1.next()) {
      // System.out.println(rs.getString(1) + " " + rs.getString(2));
      System.out.println(
        rs1.getString(1) +
        " " +
        rs1.getString(2) +
        " " +
        rs1.getString(3) +
        " " +
        rs1.getString(4)
      );
    }
    rs.last();
    int rowCount = rs.getRow();
    rs.first();
    String[] ip = new String[rowCount];
    String[] id = new String[rowCount];
    int counter = 0;
    while (rs.next()) {
      ip[counter] = rs.getString("clientip");
      id[counter] = rs.getString("sessionid");
      counter++;
    }
    for (int i = 0; i < ip.length; i++) {
      String qryHighTimeString =
        "select clientip,sessionid,url,timetaken_in_ms from log where clientip='" +
        ip[i] +
        "' and sessionid='" +
        id[i] +
        "' order by timetaken_in_ms desc;";
      ResultSet rs2 = stmt.executeQuery(qryHighTimeString);
      while (rs2.next()) {
        // System.out.println(rs.getString(1) + " " + rs.getString(2));
        System.out.println(
          rs2.getString(1) +
          " " +
          rs2.getString(2) +
          " " +
          rs2.getString(3) +
          " " +
          rs2.getString(4)
        );
      }
    }
    // for (int i =0 ; i < ip.length; i++) {
    //     System.out.println(ip[i]);
    // }

  }

  public static void main(String[] args)
    throws SQLException, ClassNotFoundException {
    MaxTime();
  }
}
