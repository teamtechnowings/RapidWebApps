/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author technnowings
 */
public class ConnectionManager {

    public static final String db_driver = "com.mysql.jdbc.Driver";
    public static final String db_user = "root";
    public static final String db_pwd = "";
    public static final String db_url = "jdbc:mysql://localhost/farmerbuddy";

    public Connection getDBConnection() {
        Connection conn = null;
        try {
            Class.forName(db_driver);
            conn = DriverManager.getConnection(db_url,
                    db_user, db_pwd);
            System.out.println("Got Connection");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error Closing Connection");
            }
        }
    }

    public ArrayList<String[]> getAllStates() {
        ArrayList<String[]> data = new ArrayList<String[]>();
        try {
            Connection conn = getDBConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `states`");

            while (rs.next()) {
                String[] array = new String[2];
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                data.add(array);
            }
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public ArrayList<String[]> getAllDistricts(String stateId) {
        ArrayList<String[]> data = new ArrayList<String[]>();
        try {
            Connection conn = getDBConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `districtdata` where stateId=" + stateId);

            while (rs.next()) {
                String[] array = new String[2];
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                data.add(array);
            }
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public ArrayList<String[]> getAllVillages(String stateId, String districtId) {
        ArrayList<String[]> data = new ArrayList<String[]>();
        try {
            Connection conn = getDBConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `talukadata` where stateId=" + stateId + " and did=" + districtId);

            while (rs.next()) {
                String[] array = new String[2];
                array[0] = rs.getString(1);
                array[1] = rs.getString(2);
                data.add(array);
            }
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println(new ConnectionManager().getAllStates());
        System.out.println(new ConnectionManager().getAllDistricts("10").size());
        System.out.println(new ConnectionManager().getAllVillages("11", "1109").size());
    }
}
