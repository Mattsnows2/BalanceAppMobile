package my.matt.myApp.models;

import java.util.List;

public class Compte {

    private String Capital;
    private List<Depense> depsense;
    private List<Revenu> revenu;

    public Compte(String capital, List<Depense> depsense, List<Revenu> revenu) {
        Capital = capital;
        this.depsense = depsense;
        this.revenu = revenu;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public List<Depense> getDepsense() {
        return depsense;
    }

    public void setDepsense(List<Depense> depsense) {
        this.depsense = depsense;
    }

    public List<Revenu> getRevenu() {
        return revenu;
    }

    public void setRevenu(List<Revenu> revenu) {
        this.revenu = revenu;
    }

    public Compte(String Capital) {
        this.Capital = Capital;
    }


}
