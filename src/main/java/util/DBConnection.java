package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by scott on 2017/3/6.
 */
public class DBConnection {
    private Connection conn = null;
    private Statement state = null;
    private PreparedStatement prep = null;
    private String url = "jdbc:mysql://localhost/search_engine";
    private String user = "root";
    private String password = "root";

    public DBConnection() {
//        try{
//
//        }
    }
}
