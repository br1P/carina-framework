package myfitnesspal.objects;

public class MFPUser {
    private String name;
    private String birthDate;
    private String weight;
    private String heightFeet;
    private String email;
    private String password;
    private String username;

    // Constructor
    public MFPUser(){};
    public MFPUser(String name, String birthDate, String weight, String heightFeet, String email, String password, String username) {
        this.name = name;
        this.birthDate = birthDate;
        this.weight = weight;
        this.heightFeet = heightFeet;

        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(String heightFeet) {
        this.heightFeet = heightFeet;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}