package my.matt.myApp.models;

public class Depense {

    private long id;
    private String description;
    private String montant;
    private String categorie;
    private String date;

    public Depense(String description, String montant, String categorie, String date ) {
        this.description = description;
        this.montant = montant;
        this.categorie= categorie;
        this.date= date;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
