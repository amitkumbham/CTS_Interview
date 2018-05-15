package com.vehicle.crud.operations;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/vehiclemanage")
public class VehicleManagementService {
 
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
	
	@POST
	@Path("/addVehicle")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addVehicle(@FormParam("vehicleNumber") String vehicleNumber,
            @FormParam("vehicleBrand") String vehicleBrand,
            @FormParam("vehicleType") String vehicleType) {
 
		try{
			SQLiteDriver.insertValues(vehicleNumber, vehicleType, vehicleBrand);
		}catch(Exception ex){
			
		}
		List<VehicleVO> vos= SQLiteDriver.selectVehicleDetail();
		
		
		return Response.status(200).entity(buildVehicleDetails(vos)).build();
 
	}
	
	private String buildVehicleDetails(List<VehicleVO> vos){
		String responseHTML= "<html><head><b>Vehicle Details After CRUD operation</b></head><body><table style='border: 1px solid black;'><tr><td>Vehicle Number</td><td>Vehicle Type</td><td>Vehicle Brand</td></tr>";
		for(VehicleVO vo :vos){
			responseHTML = responseHTML.concat("<tr><td>").concat(vo.getVehicleNumber()).concat("</td>");
			responseHTML = responseHTML.concat("<td>").concat(vo.getVehicleType()).concat("</td>");
			responseHTML = responseHTML.concat("<td>").concat(vo.getVehicleBrand()).concat("</td></tr>");
		}
		
		responseHTML.concat("</table></body></html>");
		return responseHTML;
	}
	
	@POST
	@Path("/deleteVehicle")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteVehicle(@FormParam("vehicleNumber") String vehicleNumber) {
 
		try{
			SQLiteDriver.deleteValues(vehicleNumber);
		}catch(Exception ex){
			
		}
		List<VehicleVO> vos= SQLiteDriver.selectVehicleDetail();
		return Response.status(200).entity(buildVehicleDetails(vos)).build();
 
	}
	
	@POST
	@Path("/searchVehicle")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response searchVehicle(@FormParam("vehicleNumber") String vehicleNumber) {
 
		
		List<VehicleVO> vos= SQLiteDriver.selectVehicleDetailByVehicleNumber(vehicleNumber);
		return Response.status(200).entity(buildVehicleDetails(vos)).build();
 
	}
	
	@POST
	@Path("/updateVehicle")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateVehicle(@FormParam("vehicleNumber") String vehicleNumber,@FormParam("vehicleBrand") String vehicleBrand,
            @FormParam("vehicleType") String vehicleType) {
 
		try{
			SQLiteDriver.updateValues(vehicleNumber, vehicleType, vehicleBrand);
		}catch(Exception ex){
			
		}
		List<VehicleVO> vos= SQLiteDriver.selectVehicleDetail();
		return Response.status(200).entity(buildVehicleDetails(vos)).build();
 
	}

 
}
