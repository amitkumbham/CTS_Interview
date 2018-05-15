package com.vehicle.crud.operations;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to manage SQLite DB
 *
 */
public class SQLiteDriver {
	
	public static String url = "jdbc:sqlite:C://sqlite/db/vehicleDB.db";
	
	//public static String url = "jdbc:sqlite:vehicleDB.db";
	
	//Create Db first
	public static void createVehicleDB() {
		 
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	//Create table for vehicle details
	public static void createVehicleTable() {
        // Connection Establish
        
        
        String sql = "CREATE TABLE IF NOT EXISTS VEHICLE_DETAILS(\n"
                + "	vehicle_number varchar(20),\n"
                + "	vehicle_type vatchar(10),\n"
                + "	vehicle_brand\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	//insert values
	public static void insertValues(String vehicleNumber, String vehicleType, String brand)throws Exception {
		
        String sql = "INSERT INTO VEHICLE_DETAILS(vehicle_number,vehicle_type,vehicle_brand) VALUES(?,?,?)";
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,vehicleNumber);
            pstmt.setString(2, vehicleType);
            pstmt.setString(3, brand);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	//delete values
		public static void deleteValues(String vehicleNumber)throws Exception {
			
	        String sql = "DELETE from VEHICLE_DETAILS where VEHICLE_NUMBER in(?)";
	        Class.forName("org.sqlite.JDBC");
	        try (Connection conn = DriverManager.getConnection(url);
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1,vehicleNumber);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
	//selectValues
	public static List<VehicleVO> selectVehicleDetail(){
		List<VehicleVO> vos = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String sql = "SELECT vehicle_number, vehicle_type, vehicle_brand FROM VEHICLE_DETAILS";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
            	VehicleVO vo = new VehicleVO();
            	vo.setVehicleNumber(rs.getString("vehicle_number"));
            	vo.setVehicleBrand(rs.getString("vehicle_brand"));
            	vo.setVehicleType(rs.getString("vehicle_type"));
            	vos.add(vo);
                                   
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vos;
    }
	
	public static List<VehicleVO> selectVehicleDetailByVehicleNumber(String number){
		List<VehicleVO> vos = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String sql = "SELECT vehicle_number, vehicle_type, vehicle_brand FROM VEHICLE_DETAILS where vehicle_number like '"+number+"'";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
            	VehicleVO vo = new VehicleVO();
            	vo.setVehicleNumber(rs.getString("vehicle_number"));
            	vo.setVehicleBrand(rs.getString("vehicle_brand"));
            	vo.setVehicleType(rs.getString("vehicle_type"));
            	vos.add(vo);
                                   
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vos;
    }
	
	//Update
	
	public static void updateValues(String vehicleNumber, String vehicleType, String brand)throws Exception {
		
        String sql = "UPDATE VEHICLE_DETAILS set vehicle_type=?,vehicle_brand=? where vehicle_number=?";
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vehicleType);
            pstmt.setString(2, brand);
            pstmt.setString(3,vehicleNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }	
 
	
	public static void main(String args[]){
		//insertValues("AP01234","CAR","Innova");
		selectVehicleDetail();
	}

}
