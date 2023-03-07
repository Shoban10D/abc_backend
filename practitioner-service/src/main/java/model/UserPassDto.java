package model;

public class UserPassDto {
    private String username;
    private String password;
    private Long id;

    @Override
    public String toString() {
        return "UserPassDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserPassDto(entity entity) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.id = entity.getId();
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

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
}
