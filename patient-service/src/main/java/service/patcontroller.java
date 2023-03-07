package service;

import com.google.gson.Gson;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import model.entity;
import model.modelface;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;


@Path("/patient")
@ApplicationScoped
public class patcontroller {


    private static final Logger LOGGER = LoggerFactory.getLogger(patcontroller.class);
@Inject
patservice service;

@Inject
Mailer mailer;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<modelface> getPatientdata(){
        LOGGER.info("xxxxxxxxxxxxxxg GET METHOD FOR PATIENT CALLED");
        return service.sendPatientData();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object post(Object patientModel){
        JSONObject jsonObject0 = new JSONObject(new Gson().toJson(patientModel));
        String email = jsonObject0.getString("email");
        String body = "<strong>Hello!</strong>" + "\n" +
                "<p>Thank you for registering with ABC health &times; \n  : <img src=\"cid:my-image@quarkus.io\"/></p>" +
                "<p>Regards</p>";
        mailer.send(Mail.withHtml(email,"A message from ABC health corp",body)
                .addInlineAttachment("doctor.jpg",
                        new File("src/main/resources/META-INF/resources/doctor.jpg")
                        ,"image/jpg","<my-image@quarkus.io>"));
        service.create(patientModel);
        return patientModel;

    }

}
