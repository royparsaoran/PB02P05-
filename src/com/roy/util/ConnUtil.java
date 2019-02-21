package com.roy.util;

import com.roy.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author royparsaoran
 */
public class ConnUtil {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pbo220182";
    private static final String USERNAME = "root";
    private static final String Password = "";
    
    public static Connection creatConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, Password);
            conn.setAutoCommit(false);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
