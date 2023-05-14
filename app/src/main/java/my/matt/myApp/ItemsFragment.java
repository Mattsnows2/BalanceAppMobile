package my.matt.myApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.sql.SQLOutput;

import my.matt.myApp.models.TRModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsFragment extends Fragment{

  public Spinner sp1,sp2;
  TextView txtResult;
  EditText ed1;
  Button b1;
  String fromDevise="";
  String toDevise="";

    String[] from = {"USD-Dollar des Etats-Unis","EUR-Euro","GPD-Livre britannique","JPY-Yen japonais","CHF-Franc suisse"};
    int flags[] = {R.drawable.drapeauusa, R.drawable.drapeauue, R.drawable.drapeauuk,R.drawable.drapeaujapon, R.drawable.drapeausuisse};
    String[] to = {"USD-Dollar des Etats-Unis","EUR-Euro","GPD-Livre britannique","JPY-Yen japonais","CHF-Franc suisse"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_items, container, false);

        ed1=view.findViewById(R.id.txtamount);
        sp1=view.findViewById(R.id.spfrom);
        sp2=view.findViewById(R.id.spto);
        b1=view.findViewById(R.id.btn1);
        txtResult=view.findViewById(R.id.txtResult);


        sp1.setOnItemSelectedListener(new FromSpinnerClass());
        CustomAdapter ad = new CustomAdapter(getContext(),flags, from);
        sp1.setAdapter(ad);


        sp2.setOnItemSelectedListener(new ToSpinnerClass());
        CustomAdapter ad2 = new CustomAdapter(getContext(),flags, to);
        sp2.setAdapter(ad2);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double tot;

                Double amount=Double.parseDouble(ed1.getText().toString());

                //CONVERSION DEPUIS USD
                if(fromDevise.equals("USD-Dollar des Etats-Unis") && toDevise.equals("EUR-Euro")){
                    tot=amount*0.89876;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString()+" Euro");
                }else  if(fromDevise.equals("USD-Dollar des Etats-Unis") && toDevise.equals("GPD-Livre britannique")){
                    tot=amount*0.79144;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Livre britannique");
                }else  if(fromDevise.equals("USD-Dollar des Etats-Unis") && toDevise.equals("JPY-Yen japonais")){
                    tot=amount*134.8395;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() + " Yen japonais");
                }else  if(fromDevise.equals("USD-Dollar des Etats-Unis") && toDevise.equals("CHF-Franc suisse")){
                    tot=amount*0.89744;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() + " Franc suisse");
                }else  if(fromDevise.equals("USD-Dollar des Etats-Unis") && toDevise.equals("USD-Dollar des Etats-Unis")){
                    tot=amount*1;
                    txtResult.setText(tot.toString() + " Dollar des États-Unis");
                }

                //CONVERSION DEPUIS EUR
                if(fromDevise.equals("EUR-Euro") && toDevise.equals("USD-Dollar des Etats-Unis")){
                    tot=amount*1.11265;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Dollar des États-Unis");
                }else  if(fromDevise.equals("EUR-Euro") && toDevise.equals("GPD-Livre britannique")){
                    tot=amount*0.8872;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Livre britannique");
                }else  if(fromDevise.equals("EUR-Euro") && toDevise.equals("JPY-Yen japonais")){
                    tot=amount*150;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString()  + " Yen japonais");
                }else  if(fromDevise.equals("EUR-Euro") && toDevise.equals("CHF-Franc suisse")){
                    tot=amount* 0.98755;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() + " Franc suisse");
                }else  if(fromDevise.equals("EUR-Euro") && toDevise.equals("EUR-Euro")){
                    tot=amount* 1;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() + " Euro");
                }

                //CONVERSION DEPUIS GPD
                if(fromDevise.equals("GPD-Livre britannique") && toDevise.equals("USD-Dollar des Etats-Unis")){
                    tot=amount*1.2639;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Dollar des États-Unis");
                }else  if(fromDevise.equals("GPD-Livre britannique") && toDevise.equals("EUR-Euro")){
                    tot=amount*1.1357;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Euro");
                }else  if(fromDevise.equals("GPD-Livre britannique") && toDevise.equals("JPY-Yen japonais")){
                    tot=amount*170.39;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString()  + " Yen japonais");
                }else  if(fromDevise.equals("GPD-Livre britannique") && toDevise.equals("CHF-Franc suisse")){
                    tot=amount* 1.1342;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() + " Franc suisse");
                }else  if(fromDevise.equals("GPD-Livre britannique") && toDevise.equals("GPD-Livre britannique")){
                    tot=amount* 1;
                    txtResult.setText(tot.toString() + " Livre britannique");
                }

                //CONVERSION DEPUIS JPY
                if(fromDevise.equals("JPY-Yen japonais") && toDevise.equals("USD-Dollar des Etats-Unis")){
                    tot=amount*0.00742;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Dollar des États-Unis");
                }else  if(fromDevise.equals("JPY-Yen japonais") && toDevise.equals("EUR-Euro")){
                    tot=amount*0.00667;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Euro");
                }else  if(fromDevise.equals("JPY-Yen japonais") && toDevise.equals("GPD-Livre britannique")){
                    tot=amount*0.00587;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Livre britannique");
                }else  if(fromDevise.equals("JPY-Yen japonais") && toDevise.equals("CHF-Franc suisse")){
                    tot=amount* 0.00666;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() + " Franc suisse");
                }else  if(fromDevise.equals("JPY-Yen japonais") && toDevise.equals("JPY-Yen japonais")){
                    tot=amount* 1;
                    txtResult.setText(tot.toString() + " Yen japonais");
                }

                //CONVERSION DEPUIS CHF
                if(fromDevise.equals("CHF-Franc suisse") && toDevise.equals("USD-Dollar des Etats-Unis")){
                    tot=amount*1.11436;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Dollar des États-Unis");
                }else  if(fromDevise.equals("CHF-Franc suisse") && toDevise.equals("EUR-Euro")){
                    tot=amount*1.00154;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Euro");
                }else  if(fromDevise.equals("CHF-Franc suisse") && toDevise.equals("GPD-Livre britannique")){
                    tot=amount*0.88195;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString() +" Livre britannique");
                }else  if(fromDevise.equals("CHF-Franc suisse") && toDevise.equals("JPY-Yen japonais")){
                    tot=amount* 151.685;
                    tot = (double) Math.round(tot*100)/100;
                    txtResult.setText(tot.toString()  + " Yen japonais");
                }else  if(fromDevise.equals("CHF-Franc suisse") && toDevise.equals("CHF-Franc suisse")){
                    tot=amount* 1;
                    txtResult.setText(tot.toString()  + " Franc suisse");
                }
            }
        });


       return view;
    }


    class FromSpinnerClass implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            fromDevise=from[position];
            System.out.println(fromDevise);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class ToSpinnerClass implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            toDevise=to[position];
            System.out.println(toDevise);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}