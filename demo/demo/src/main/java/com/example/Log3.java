package com.example;

import java.sql.*;

public class Log3 {
    
    public static void MaxTime() throws SQLException,ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","postgres","Kamal2002*");
        Statement stmt = con.createStatement();
        String sql = "select distinct clientip,sessionid from log group by clientip,sessionid;";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }

        String[] ip= {"select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.68' and sessionid='-' order by timetaken_in_ms desc;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.105' and sessionid='-' order by timetaken_in_ms;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.105' and sessionid='3F8B490ADD6EFE07E0A92897237886E0' order by timetaken_in_ms;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.234' and sessionid='-' order by timetaken_in_ms;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.47' and sessionid='-' order by timetaken_in_ms;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.68' and sessionid='913294CE06A6D888419F3EB8B3159B81' order by timetaken_in_ms;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.80' and sessionid='-' order by timetaken_in_ms;","select clientip,sessionid,url,timetaken_in_ms from log where clientip='192.168.1.80' and sessionid='0BA4ADC41C08CC64C0ABA8A511C45B78' order by timetaken_in_ms;"};
        for (int i = 0; i < ip.length; i++) {
            ResultSet rs1 = stmt.executeQuery(ip[i]);
            while (rs1.next()) {
                System.out.println(rs1.getString(1) + " " + rs1.getString(2)+ " " + rs1.getString(3)+ " " + rs1.getString(4));
            }
        }
    }
    public static void main(String[] args) throws SQLException,ClassNotFoundException {
        MaxTime();
    }
}
