package model;
public class docDetail {
   private String Doctor;
    private String Timing;


    private Long id;

    public docDetail(){}
    public docDetail(entity entity){
        this.Doctor = entity.getUsername();
        this.Timing = entity.getAppointment();
        this.id = entity.getId();
    }


    public String getDoctor() {
        return Doctor;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

}
