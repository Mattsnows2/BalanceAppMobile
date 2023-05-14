package my.matt.myApp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private DatabaseReference mDatabase;
    private String currentDevise;
    private double capital;
    private Button buttonDeleteAccount;
    private ImageView imageRetourSettings;

    private FirebaseUser mUser;
    private double d=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentHomePage);
        }
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mDatabase= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        getDevise();
        getCapital();

        loadLocale();

        imageRetourSettings=view.findViewById(R.id.imageRetourSettings);

        imageRetourSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTest = new Intent(getActivity(), MainActivity.class);
                startActivity(intentTest);
            }
        });

        buttonDeleteAccount = view.findViewById(R.id.buttonDeleteAccount);

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Etes vous sur de vouloir supprimer votre compte ?");
                dialog.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Compte supprimé", Toast.LENGTH_SHORT).show();
                                    Intent intentTest = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intentTest);
                                }else{
                                    Toast.makeText(getActivity(), "Votre compte n'a pas pu etre supprimé", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        TextView textChangePassword = (TextView) view.findViewById(R.id.textChangePassword);
        textChangePassword.setOnClickListener(v->{
            Intent intentTest = new Intent(getActivity(), TransitionbetweenSettingsMyProfil.class);
            startActivity(intentTest);
        });

        TextView changeEmail = (TextView) view.findViewById(R.id.textChangeEmail);
        changeEmail.setOnClickListener(v->{
            Intent intentTest = new Intent(getActivity(), TransitionbetweenSettingsMyProfil.class);
            startActivity(intentTest);
        });


        TextView changeLanguage = (TextView) view.findViewById(R.id.textChangeLanguage);
        changeLanguage.setOnClickListener(v -> {


            showLanguageDialog();

        });

        TextView changeDevise = (TextView) view.findViewById(R.id.textChangeDevise);
        changeDevise.setOnClickListener(v-> {

                showDeviseDialog();

        });

        return view;
    }

    private void showDeviseDialog(){
        final String[] listDevise ={"€","$","£","Yen","Yuan"};
        AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(getActivity());
        mBuilder2.setTitle("Choisissez votre devise");
        mBuilder2.setSingleChoiceItems(listDevise, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    euro("€");
                    getActivity().recreate();
                }
                else if(i ==1){
                    dollard("$");
                    getActivity().recreate();
                }
                else if(i==2){
                    livre("£");
                    getActivity().recreate();
                }
                else if(i==3){
                    yen("Yen");
                    getActivity().recreate();
                }
                else if(i==4){
                    yuan("Yuan");
                    getActivity().recreate();
                }
            }
        });
        AlertDialog mDialog = mBuilder2.create();
        mDialog.show();
    }

    private void getDevise(){
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase", "Error getting data", task.getException());
                }else{
                    currentDevise = String.valueOf(task.getResult().getValue());
                    System.out.println(String.valueOf(task.getResult().getValue()));
                }
            }
        });


    }

    private void getCapital(){
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase", "Error getting data", task.getException());
                }else{
                    capital = Double.parseDouble(String.valueOf(task.getResult().getValue()));
                    System.out.println(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void euro(String euro){
        getDevise();
        getCapital();

        if(currentDevise==euro){

        }else{
            if(currentDevise.equals("£")){

                double tauxDeChange=1.15446;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }

            if(currentDevise.equals("$")){
                double tauxDeChange=1.00482;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;

            }

            if(currentDevise.equals("Yen")){
                double tauxDeChange=0.0068165;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            if(currentDevise.equals("Yuan")){
                double tauxDeChange= 0.13862;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").setValue(euro.toString());
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(capital);
        }
    }

    private void dollard(String dollard){
        getDevise();
        getCapital();

        if(currentDevise==dollard){

        }else{
            if(currentDevise.equals("£")){

                double tauxDeChange=1.16139;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }

            if(currentDevise.equals("€")){
                double tauxDeChange=0.9966;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;

            }

            if(currentDevise.equals("Yen")){
                double tauxDeChange=0.00678;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            if(currentDevise.equals("Yuan")){
                double tauxDeChange= 0.13788;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").setValue(dollard.toString());
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(capital);
        }
    }

    private void livre(String livre){

        getDevise();
        getCapital();
        if(currentDevise==livre){

        }else{
            if(currentDevise.equals("$")){

                double tauxDeChange=0.86393;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }

            if(currentDevise.equals("€")){
                double tauxDeChange=0.85798;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;

            }

            if(currentDevise.equals("Yen")){
                double tauxDeChange=0.00586;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            if(currentDevise.equals("Yuan")){
                double tauxDeChange= 0.11912;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").setValue(livre.toString());
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(capital);
        }
    }

    private void yen(String yen){
        getDevise();
        getCapital();
        if(currentDevise==yen){

        }else{
            if(currentDevise.equals("$")){

                double tauxDeChange=147.4855;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }

            if(currentDevise.equals("€")){
                double tauxDeChange=146.775;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;

            }

            if(currentDevise.equals("£")){
                double tauxDeChange=171.26;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            if(currentDevise.equals("Yuan")){
                double tauxDeChange= 20.33584;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").setValue(yen.toString());
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(capital);
        }
    }

    private void yuan(String yuan){
        getDevise();
        getCapital();

        if(currentDevise==yuan){

        }else{
            if(currentDevise.equals("$")){

                double tauxDeChange=7.25249;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }

            if(currentDevise.equals("€")){
                double tauxDeChange=7.22721;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;

            }

            if(currentDevise.equals("£")){
                double tauxDeChange=8.39476;
                capital = capital*tauxDeChange;
                d = (double) Math.round(capital*100)/100;
            }
            if(currentDevise.equals("yen")){
                double tauxDeChange= 0.04917;
                capital = capital*tauxDeChange;

                d = (double) Math.round(capital*100)/100;
            }
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").setValue(yuan.toString());
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(capital);
        }
    }



    private void showLanguageDialog(){

        final String[]  listItems = {"French","Deutsche","English","Español"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.getActivity());
        mBuilder.setTitle("Choisissez votre langue");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    setLocale("fr");
                    getActivity().recreate();
                }
                else if(i==1){
                    setLocale("de");
                    getActivity().recreate();
                }
                else if(i==2) {
                    setLocale("en");
                    getActivity().recreate();
                }
                else if(i==3){
                    setLocale("es");
                    getActivity().recreate();
                }

                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", getActivity().MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }
}
