package service;

import com.sun.istack.NotNull;
import io.vertx.core.dns.AddressResolverOptionsConverter;
import model.UserPassDto;
import model.entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import model.modelface;

@Path("/practitioner")
public class pracontroller {

    private static final Logger LOGGER = LoggerFactory.getLogger(pracontroller.class);

    @Inject
    pracservice servicess;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<entity> getPractitioner(){
       return servicess.sendDoctorData();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(Object practitionerModel){
        LOGGER.info("this is called");
        servicess.postingvales(practitionerModel);
    }

    @GET
    @Path("availableTimings")
    @Produces(MediaType.APPLICATION_JSON)
    public Object ReturnTime(@QueryParam("id") Long id){
        entity object = entity.findById(id);
        String time = object.getAppointment();
        return time;
    }

    @GET
    @Path("fixedTimings")
    @Produces(MediaType.APPLICATION_JSON)
    public Object ReturnFixed(@QueryParam("id") Long id){
        entity object = entity.findById(id);
        String fixed = object.getFixed();
        return fixed;
    }
    @GET
    @Path("docdetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<model.docDetail> DoctorReturn(){
        List<entity> listall = entity.findAll().list();
        List<model.docDetail> result = listall.stream().map(model.docDetail::new).collect(Collectors.toList());
        return result;
    }



    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String UpdateTime(@QueryParam("id") Long id, @NotNull @Valid Object value){
        return servicess.UpdatingTime(id,value);
    }

    @PUT
    @Path("fixed")
    @Produces(MediaType.APPLICATION_JSON)
    public String UpdateFixed(@QueryParam("id") Long id,@NotNull @Valid Object value){
        return servicess.UpdateFixed(id,value);
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Long pracLogin(Object LoginCredentials){
        return servicess.LoginCheck(LoginCredentials);
    }


}

