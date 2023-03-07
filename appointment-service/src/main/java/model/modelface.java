package model;


public class modelface {

    private Long id;
    private String obj;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public modelface(){}

    public modelface(Long id,String obj){
        this.id = id;
        this.obj = obj;
    }

}
