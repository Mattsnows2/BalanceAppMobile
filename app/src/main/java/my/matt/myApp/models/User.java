package my.matt.myApp.models;

public class User {

    private String id;
    private String email;

    private float capital;

    private String devise;

    public User(String id,String email, float capital, String devise) {

        this.email = email;

        this.capital = capital;

        this.devise=devise;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public float getCapital() {
        return capital;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }
}
