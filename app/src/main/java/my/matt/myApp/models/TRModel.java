package my.matt.myApp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TRModel {

    @SerializedName("base")
    private String currency;

    @SerializedName("last_updated")
    private String rate;

    @SerializedName("exchange_rates")
    private List taux_rate;

    public List getTaux_rate() {
        return taux_rate;
    }

    public void setTaux_rate(List taux_rate) {
        this.taux_rate = taux_rate;
    }

    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
