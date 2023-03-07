package model;

public class modelface {

    private Long id;
    private String obj;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;

    private String password;
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

    public modelface(Long id,String obj,String username,String password){
        this.id = id;
        this.obj = obj;
        this.username = username;
        this.password = password;
    }




}
