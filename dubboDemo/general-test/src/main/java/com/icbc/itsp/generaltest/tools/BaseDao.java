package com.icbc.itsp.generaltest.tools;

import org.apache.commons.codec.binary.Hex;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao {
    private String driver = "";
    private String url = "";
    private String userName = "";
    private String password = "";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public BaseDao(){

    }

    public BaseDao(String driver, String url, String userName, String password){
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 查询
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> executeQuery(String sql, Object... params) throws Exception{
        getConnection();
        ps = con.prepareStatement(sql);
        if(params != null){
            for(int i = 0; i < params.length; i++){
                ps.setObject(i + 1, params[i]);
            }
        }
        rs = ps.executeQuery();
        List<Map<String, String>> result = resultSet2Map();
        close();
        return result;
    }

    /**
     * 更新
     * @param sql
     * @param params
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int executeUpdate(String sql, Object... params) throws ClassNotFoundException, SQLException{
        int result = -1;
        getConnection();
        ps = con.prepareStatement(sql);
        if(params != null){
            for(int i = 0; i < params.length; i++){
                ps.setObject(i + 1, params[i]);
            }
        }
        result = ps.executeUpdate();
        close();
        return result;
    }

    private void getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        while (con == null){
            con = DriverManager.getConnection(url, userName, password);
        }
    }

    private void close() throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(ps != null){
            ps.close();
        }
        if(con != null){
            con.close();
        }
    }

    private List<Map<String, String>> resultSet2Map() throws Exception{
        List<Map<String, String>> result = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()){
            Map<String, String> map = new HashMap<>();
            for(int i =0; i <= columnCount; i++){
                String key = rsmd.getColumnLabel(i);
                Object valueObj = rs.getObject(i);
                String value = valueObj != null ? valueObj.toString().trim() : "";
                if(key.startsWith("HEX_")){
                    key = key.substring(4);
                    byte[] valueBytes = Hex.decodeHex(value.toCharArray());
                    value = new String(valueBytes, "GBK").trim();
                }
                map.put(key, value);
            }
            result.add(map);
        }
        return result;
    }
}
