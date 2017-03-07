package util;

import java.io.InputStream;
import java.sql.*;

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
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            state = conn.createStatement();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public DBConnection(String sql){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            this.prepareStatement(sql);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }
    public void prepareStatement(String sql){
        try{
            prep = conn.prepareStatement(sql);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setString(int index, String value){
        try{
            prep.setString(index, value);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setInt(int index, int value){
        try{
            prep.setInt(index, value);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setBoolean(int index, boolean value){
        try{
            prep.setBoolean(index, value);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setDate(int index, Date value){
        try{
            prep.setDate(index, value);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setLong(int index, Long value){
        try{
            prep.setLong(index, value);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setFloat(int index, float value){
        try{
            prep.setFloat(index, value);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setBinaryStream(int index, InputStream in, int length){
        try{
            prep.setBinaryStream(index, in, length);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void clearParameters() throws SQLException {
        try{
            prep.clearParameters();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public PreparedStatement getPreparedStatement(){
        return prep;
    }
    public Statement getStatement(){
        return state;
    }
    public ResultSet executeQuery(String sql){
        try{
            if(state != null){
                return state.executeQuery(sql);
            } else{
                return null;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet executeQuery(){
        try{
            if(prep != null){
                return prep.executeQuery();
            } else{
               return null;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void executeUpdate(String sql){
        try{
            if(state != null){
                state.executeUpdate(sql);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void executeUpdate(){
        try{
            if(prep != null){
                prep.executeUpdate();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void close(){
        try{
            if(state != null){
                state.close();
                state = null;
            }
            if(prep != null){
                prep.close();
                prep = null;
            }
            conn.close();
            conn = null;
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
