package service;
import ca.uhn.fhir.parser.IParser;
import com.google.gson.Gson;

import model.UserPassDto;
import model.entity;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Narrative;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.Practitioner;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.modelface;

@ApplicationScoped
public class pracservice {
    private static final Logger LOGGER = LoggerFactory.getLogger(pracservice.class);
    public static String Total;

    public List<entity> sendDoctorData(){
        List<entity> listall = entity.findAll().list();
        return listall;
//        List<modelface> res = listall.stream().map(data->{
//            return new modelface(data.id, data.obj, data.username, data.password);
//        }).collect(Collectors.toList());
//        return res;
    }

    @Transactional
    public Long LoginCheck(Object LoginCredentials){
        int i;
        int count=0;
        Long id = 0l;
        List<entity> listall = entity.findAll().list();
        List<UserPassDto> result = listall.stream().map(UserPassDto::new).collect(Collectors.toList());
        JSONObject jsonObject = new JSONObject(new Gson().toJson(LoginCredentials));


       for(i=0;i<result.size();i++){
           if(jsonObject.getString("USERNAME").equals(result.get(i).getUsername())){
               if(jsonObject.getString("PASSWORD").equals(result.get(i).getPassword()) ){
                   count++;
                   id = result.get(i).getId();
                   break;
               }else{
                   break;
               }
           }
       }
       if(count==1){
           return id;
       }else if(count==0){
           return 0l;
       }else{
           return 0l;
       }

    }

    @Transactional
    public static String UpdatingTime(Long id,Object value){
       entity object = entity.findById(id);
       object.setAppointment(value.toString());
       object.persist();
       return "Updated";
    }

    @Transactional
    public static String UpdateFixed(Long id,Object value){
        entity object = entity.findById(id);
        object.setFixed(value.toString());
        object.persist();
        return "fixed updated";
    }


    @Transactional
    public static void postingvales(Object practitionerModel){

        entity object = new entity();

        JSONObject jsonObject = new JSONObject(new Gson().toJson(practitionerModel));

        FhirContext ctxr4 = FhirContext.forR4();

        Practitioner pract = new Practitioner();

        //Default values starts here
        pract.setId("Example");
        pract.getText().setStatus(Narrative.NarrativeStatus.GENERATED)
                .setDivAsString(jsonObject.getString("description"));

        pract.addIdentifier().setSystem("http://www.acme.org/practitioners").setValue("23");
        pract.setActive(true);
        //Default values ends here

        pract.addName().setFamily(jsonObject.getString("FamilyName"))
                        .addGiven(jsonObject.getString("firstName")).addGiven(jsonObject.getString("lastName"))
                        .addPrefix("Dr");

        pract.addAddress().setUse(Address.AddressUse.HOME)
                        .addLine(jsonObject.getString("Line1"))
                                .setCity(jsonObject.getString("city"))
                                        .setState(jsonObject.getString("state"))
                                                .setPostalCode(jsonObject.getString("postcode"));
        pract.addQualification().getCode().setText(jsonObject.getString("degree")).addCoding()
                .setSystem("http://example.org/UniversityIdentifier")
                        .setDisplay(jsonObject.getString("degree"));
        pract.addQualification().getIssuer().setDisplay(jsonObject.getString("instName"));

        IParser jsonParser = ctxr4.newJsonParser();
        jsonParser.setPrettyPrint(true);
        Total = jsonParser.encodeResourceToString(pract);
        object.obj = Total;
        object.username = jsonObject.getString("username");
        object.password = jsonObject.getString("password");
        object.persist();
        System.out.println("*************************************+++++++"+Total);

    }

}
