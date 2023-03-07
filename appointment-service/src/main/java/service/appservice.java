package service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.google.gson.Gson;
import model.entity;
import model.modelface;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Narrative;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class appservice {

    public String TotalValue;

    public List<modelface> sendAppointmentData(){
        List<entity> listall = entity.findAll().list();
        return listall.stream().map(data->{
            return new modelface(data.id,data.obj);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void createAppointment(Object appointmentvalues){
        entity object = new entity();

        JSONObject jsonObject = new JSONObject(new Gson().toJson(appointmentvalues));

        FhirContext ctx4 = FhirContext.forR4();

        Appointment appointment = new Appointment();

        appointment.setId("Example");
        appointment.getText().setStatus(Narrative.NarrativeStatus.GENERATED)
                .setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Brian MRI results discussion</div>");
        appointment.setStatus(Appointment.AppointmentStatus.BOOKED);
        appointment.addServiceCategory().addCoding().setSystem("http://example.org/service-category")
                .setCode("gp")
                .setDisplay("General Practice");
        appointment.addServiceType().addCoding().setCode("52")
                .setDisplay("General Discussion");
        appointment.addSpecialty().addCoding().setSystem("http://snomed.info/sct")
                .setCode("394814009")
                .setDisplay("General practice");


        //User values starts entering here
        appointment.getAppointmentType().addCoding().setSystem("http://terminology.hl7.org/CodeSystem/v2-0276")
                .setCode(jsonObject.getString("appointmenttype"))
                .setDisplay(jsonObject.getString("appointmenttype")=="new"?"A New visit for appointment":"A follow up visit from a previous appointment");
        appointment.addReasonReference().setReference("Condition/example")
                .setDisplay(jsonObject.getString("reason"));
        appointment.setPriority(1);
        appointment.setDescription(jsonObject.getString("description").equals("null")?"Follow up":jsonObject.getString("description"));
        appointment.setComment(jsonObject.getString("comments"));
        appointment.addBasedOn().setReference("ServiceRequest/myringotomy");
        //setting patinet name
        appointment.addParticipant().setRequired(Appointment.ParticipantRequired.REQUIRED)
                .setStatus(Appointment.ParticipationStatus.ACCEPTED)
                .getActor().setReference("Patient/example")
                .setDisplay(jsonObject.getString("patientname"));
        //setting doctor name
        appointment.addParticipant().setRequired(Appointment.ParticipantRequired.REQUIRED)
                .setStatus(Appointment.ParticipationStatus.ACCEPTED)
                .getActor().setReference("Practitioner/example")
                .setDisplay(jsonObject.getString("doctorname"));
        //setting address
        appointment.addParticipant().setRequired(Appointment.ParticipantRequired.REQUIRED)
                .setStatus(Appointment.ParticipationStatus.ACCEPTED)
                .getActor().setReference("Location/1")
                .setDisplay("South Wing, second floor");
        //setting default again
        appointment.addParticipant().addType().addCoding()
                .setSystem("http://terminology.hl7.org/CodeSystem/v3-ParticipationType").setCode("ATND");


        IParser jsonParser = ctx4.newJsonParser();
        jsonParser.setPrettyPrint(true);
        TotalValue = jsonParser.encodeResourceToString(appointment);
        System.out.println(TotalValue);
        object.obj = TotalValue;
        System.out.println("*****************************************"+TotalValue);
        object.persist();

    }

}
