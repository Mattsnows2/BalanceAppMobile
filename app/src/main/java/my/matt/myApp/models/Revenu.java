package my.matt.myApp.models;

public class Revenu {

    private long id;
    private String description;
    private String montant;
    private String catagorie;
    private String date;


    public Revenu(Long id,String description, String montant, String categorie, String date) {
        this.description = description;
        this.montant = montant;
        this.catagorie= categorie;
        this.date = date;
        this.id=id;

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCatagorie() {
        return catagorie;
    }

    public void setCatagorie(String catagorie) {
        this.catagorie = catagorie;
    }
}
