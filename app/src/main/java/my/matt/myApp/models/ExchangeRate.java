package my.matt.myApp.models;

import com.google.gson.annotations.SerializedName;

public class ExchangeRate {

    private String currency;

    private Double rate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
