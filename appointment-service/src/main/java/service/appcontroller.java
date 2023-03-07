package service;

import model.modelface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("/appointment")
public class appcontroller {

    private static final Logger LOGGER = LoggerFactory.getLogger(appcontroller.class);

    @Inject
    appservice serviceObject;



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<modelface> get(){
        return serviceObject.sendAppointmentData();
    }
    @GET
    @Path("appoint")
    @RolesAllowed("patient")
    @Produces(MediaType.TEXT_PLAIN)
    public String appoint(){
        return "hello appoint";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void posting(Object appointmentvalues){
       serviceObject.createAppointment(appointmentvalues);
    }
    /*
    {patientname=bear grylls, doctorname= Dr. William A. Abdu, M.D, M.S., appointmentdate=2023-01-13T18:30:00.000Z, appointmenttime=12.30pm, appointmenttype=new, reason=aswdasdasd, description=null, comments=asdasdasd}

     */

}
