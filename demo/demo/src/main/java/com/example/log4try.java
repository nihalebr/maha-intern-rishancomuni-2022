package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class log4try {

  public static void MaxTime() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    Connection con = DriverManager.getConnection(
      "jdbc:postgresql://localhost/postgres",
      "postgres",
      "!sHuman"
    );
    Statement stmt = con.createStatement(
      ResultSet.TYPE_SCROLL_INSENSITIVE,
      ResultSet.CONCUR_UPDATABLE
    );
    Statement stmt2 = con.createStatement(
      ResultSet.TYPE_SCROLL_INSENSITIVE,
      ResultSet.CONCUR_UPDATABLE
    );
    String sql =
      "select distinct clientip,sessionid from log group by clientip,sessionid;";
    ResultSet rs = stmt.executeQuery(sql);
    Map<String, List<String>> pageGrp = new HashMap<>();
    while (rs.next()) {
      String qryHighTimeUrl =
        "select clientip,sessionid,url,timetaken_in_ms from log where clientip='" +
        rs.getString(1) +
        "' and sessionid='" +
        rs.getString(2) +
        "' order by timetaken_in_ms desc;";
      System.out.println(qryHighTimeUrl);
      ResultSet timeBigURL = stmt2.executeQuery(qryHighTimeUrl);
      long prevTime = 0;
      List<String> pageUrl = new ArrayList<>();
      String pageName = "NewPage ";
      int pageNo = 0;
      while (timeBigURL.next()) {
        System.out.println(
          timeBigURL.getString(1) +
          ";" +
          timeBigURL.getString(2) +
          ";" +
          timeBigURL.getString(3) +
          ";" +
          timeBigURL.getString(4)
        );
        if (prevTime == 0 || (prevTime - timeBigURL.getLong(4)) > 2000) { //in ms.
          // map get and put
          pageUrl = new ArrayList<>();
          pageNo++;
        }
        pageUrl.add(timeBigURL.getString(3));
        pageGrp.put(pageName + pageNo, pageUrl);
        prevTime = timeBigURL.getLong(4);
      }
    }

    for (String pgName : pageGrp.keySet()) {
      List<String> urlLst = pageGrp.get(pgName);
      // Print the out put and pgName.
      System.out.println(pgName + urlLst);
    }
  }

  public static void main(String[] args)
    throws SQLException, ClassNotFoundException {
    MaxTime();
  }
}
