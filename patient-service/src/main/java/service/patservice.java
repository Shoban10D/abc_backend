package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Address.AddressUse;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.HumanName.NameUse;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;
import org.json.JSONObject;

import com.google.gson.Gson;

import ca.uhn.fhir.context.FhirContext;
import model.entity;
import model.modelface;


@ApplicationScoped
public class patservice {
    public String[] NameType = {"usual","official","temp","nickname","anonymous","old","maiden"};
    public String[] PhoneSystemType = {"phone","fax","email","pager","url","sms","other"};
    public String[] PhoneType = {"home","work","temp","old","mobile"};
    public String[] AddressType = {"home","work","temp","old","billing"};
    public String TotalValue;

    public List<modelface> sendPatientData(){
        List<entity> listall = entity.findAll().list();
        return listall.stream().map(data->{
            return new modelface(data.id,data.obj, data.username, data.password);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void create(Object patientmodel){
        //Creating entity instance for persisting values in database
        entity object = new entity();

        //Converting Object into JsonObject using GSON
        JSONObject jsonObject = new JSONObject(new Gson().toJson(patientmodel));

        //Initializing the FHIR factory using FHIRContext
        FhirContext ctxr4 = FhirContext.forR4();

        //Creating an instance for Patient class from fhir
        Patient patient = new Patient();

        if(Arrays.asList(NameType).contains(jsonObject.getString("NameType"))){
            int index1 = Arrays.asList(NameType).indexOf(jsonObject.getString("NameType"));
            if(index1 == 0){
                patient.addName().setUse(NameUse.USUAL);
            }else if(index1 == 1){
                patient.addName().setUse(NameUse.OFFICIAL);
            }else if(index1 == 2){
                patient.addName().setUse(NameUse.TEMP);
            }else if(index1 == 3){
                patient.addName().setUse(NameUse.NICKNAME);
            }else if(index1 == 4){
                patient.addName().setUse(NameUse.ANONYMOUS);
            }else if(index1 == 5){
                patient.addName().setUse(NameUse.OLD);
            }else if(index1 == 6){
                patient.addName().setUse(NameUse.MAIDEN);
            }
        }
        patient.addName().setFamily(jsonObject.getString("FamilyName")).addGiven(jsonObject.getString("GivenName"));

        if(Arrays.asList(AddressType).contains(jsonObject.getString("UseAddress"))){
            int index2 =  Arrays.asList(AddressType).indexOf(jsonObject.getString("UseAddress"));
            if(index2 == 0){
                patient.addAddress().setUse(AddressUse.HOME);
            }else if(index2 == 1){
                patient.addAddress().setUse(AddressUse.WORK);
            }else if(index2 == 2){
                patient.addAddress().setUse(AddressUse.TEMP);
            }else if(index2 == 3){
                patient.addAddress().setUse(AddressUse.OLD);
            }else if(index2 == 4){
                patient.addAddress().setUse(AddressUse.BILLING);
            }

        }
        patient.addAddress().setText(jsonObject.getString("Address1")).setCity(jsonObject.getString("City")).setState(jsonObject.getString("State"))
                .setCountry(jsonObject.getString("Country")).setPostalCode(jsonObject.getString("PostalCode"));
        patient.addIdentifier().setUse(IdentifierUse.USUAL).getType().addCoding().setSystem("http://terminology.hl7.org/CodeSystem/v2-0203").setCode("MR");
        patient.addIdentifier().getAssigner().setDisplay("ABC Healthcare");
        patient.setActive(true);

        if(Arrays.asList(PhoneType).contains(jsonObject.getString("PhoneType"))){
            int index3 =  Arrays.asList(PhoneType).indexOf(jsonObject.getString("PhoneType"));
            if(index3 == 0){
                patient.addTelecom().setUse(ContactPointUse.HOME);
            }else if(index3 == 1){
                patient.addTelecom().setUse(ContactPointUse.WORK);
            }else if(index3 == 2){
                patient.addTelecom().setUse(ContactPointUse.TEMP);
            }else if(index3 == 3){
                patient.addTelecom().setUse(ContactPointUse.OLD);
            }else if(index3 == 4){
                patient.addTelecom().setUse(ContactPointUse.MOBILE);
            }
        }

        if(Arrays.asList(PhoneSystemType).contains(jsonObject.getString("SystemType"))){
            int index4 =  Arrays.asList(PhoneSystemType).indexOf(jsonObject.getString("SystemType"));
            if(index4 == 0){
                patient.addTelecom().setSystem(ContactPointSystem.PHONE).setValue(jsonObject.getString("Phone"));

            }else if(index4 == 1){
                patient.addTelecom().setSystem(ContactPointSystem.FAX).setValue(jsonObject.getString("Phone"));

            }else if(index4 == 2){
                patient.addTelecom().setSystem(ContactPointSystem.EMAIL).setValue(jsonObject.getString("Phone"));

            }else if(index4 == 3){
                patient.addTelecom().setSystem(ContactPointSystem.PAGER).setValue(jsonObject.getString("Phone"));

            }else if(index4 == 4){
                patient.addTelecom().setSystem(ContactPointSystem.URL).setValue(jsonObject.getString("Phone"));

            }else if(index4 == 5){
                patient.addTelecom().setSystem(ContactPointSystem.SMS).setValue(jsonObject.getString("Phone"));

            }else if(index4 == 6){
                patient.addTelecom().setSystem(ContactPointSystem.OTHER).setValue(jsonObject.getString("Phone"));

            }

        }


        // patient.addContact().addRelationship().addCoding().setSystem("http://terminology.hl7.org/CodeSystem/v2-0131").setCode("N");

        if(jsonObject.getString("Gender")=="male"){
            patient.setGender(AdministrativeGender.MALE);
        }else if(jsonObject.getString("Gender")=="women"){
            patient.setGender(AdministrativeGender.FEMALE);
        }else{
            patient.setGender(AdministrativeGender.OTHER);
        }

        patient.setLanguage("English");
        patient.getManagingOrganization().setReference("Organization/1");

        IParser jsonParser = ctxr4.newJsonParser();
        jsonParser.setPrettyPrint(true);
        TotalValue = jsonParser.encodeResourceToString(patient);
        System.out.println("******************"+TotalValue+"*************************");
        String FhirValue = TotalValue.toString();
        System.out.println("**********sef********"+FhirValue+"**********jkshf***************");
        object.obj = FhirValue;
        object.username = jsonObject.getString("username");
        object.password = jsonObject.getString("password");
        object.persist();
    }

}
