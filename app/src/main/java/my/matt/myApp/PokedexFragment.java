package my.matt.myApp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class PokedexFragment extends Fragment {

    Button buttonRevenu, buttonDepense, closePopupDepenseMois,closePopupRevenuMois;
    TextView textCapital, textDevise,textRevenuTotal,textExpenseTotal,textTotal, textRevenuSurLeMois, textDepenseSurLeMois,textSumactures, textSumNourriture, textSumCharges,textSumEducation,textSumTransport,textSumAutres,textSumDepenseTotal, textSumAutresRevenus,textSumInteret,textSumSalaire,textSumRevenuTotal;
    private DatabaseReference mDatabase;
    ConstraintLayout popUpDepenseMois,popUpRevenuMois;
    PieChart pieChart;
    PieChart pieChart2;
    ArrayList<PieEntry> depenses = new ArrayList<>();
    ArrayList<PieEntry> revenus = new ArrayList<>();
    private String capital;
    private double capitalDouble;
    private double capitalWithoutManyNumbers;
    double sumRevenu = 0;
    String sumRevenuString;
    double sumDepense=0;
    String sumDepenseString;
    Spinner spinner;
    float numeroAnnee;
    float anneeForFilter;
    double total;
    String montant;
    String categorie;
    String compteSelectionnee="compte";
    List<Float> listMontantRevenu = new ArrayList<>();
    List<Float> listMontantDepenses = new ArrayList<>();
    ArrayList<Float> montantSalaire = new ArrayList<>();
    ArrayList<Float> montantInteret = new ArrayList<>();
    ArrayList<String> categorieRevenu = new ArrayList<>();
    ArrayList<String> categorieDepense = new ArrayList<>();

    ArrayList<Float> montantNourriture = new ArrayList<>();
    ArrayList<Float> montantEducation= new ArrayList<>();
    ArrayList<Float> montantTransport = new ArrayList<>();

    ArrayList<Float> montantCharges = new ArrayList<>();
    ArrayList<Float> montantFacture = new ArrayList<>();
    ArrayList<Float> montrantAutresDepenses = new ArrayList<>();
    ArrayList<Float> montantAutres = new ArrayList<>();
    Spinner spinnerChooseCompte;
    String compteChoisi="";





    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentHomePage);
        }

        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        mDatabase= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        buttonDepense = view.findViewById(R.id.buttonDepense);
        buttonRevenu = view.findViewById(R.id.buttonRevenu);
        textCapital = view.findViewById(R.id.textCapital);
        textDevise = view.findViewById(R.id.textDevise);
        pieChart = view.findViewById(R.id.pieChart_view);
        pieChart2 = view.findViewById(R.id.pieChart_viewDepense);
        textRevenuTotal=view.findViewById(R.id.textRevenuTotal);
        textExpenseTotal=view.findViewById(R.id.textExpenseTotal);
        spinner=view.findViewById(R.id.spinnerHomepage);
        spinnerChooseCompte=view.findViewById(R.id.spinnerChooseCompte);


        textTotal=view.findViewById(R.id.textTotal);
        textDepenseSurLeMois=view.findViewById(R.id.textDepenseSurLeMois);
        textRevenuSurLeMois=view.findViewById(R.id.textRevenuSurLeMois);
        textSumactures = view.findViewById(R.id.textSumactures);
        textSumNourriture=view.findViewById(R.id.textSumNourriture);
        textSumCharges=view.findViewById(R.id.textSumCharges);
        textSumTransport=view.findViewById(R.id.textSumTransport);
        textSumEducation=view.findViewById(R.id.textSumEducation);
        textSumAutres=view.findViewById(R.id.textSumAutres);
        textSumDepenseTotal=view.findViewById(R.id.textSumDepenseTotal);
        popUpDepenseMois=view.findViewById(R.id.popUpDepenseMois);
        popUpRevenuMois=view.findViewById(R.id.popUpRevenuMois);
        closePopupDepenseMois=view.findViewById(R.id.closePopupDepenseMois);
        closePopupRevenuMois=view.findViewById(R.id.closePopupRevenuMois);
        textSumAutresRevenus=view.findViewById(R.id.textSumAutresRevenus);
        textSumRevenuTotal=view.findViewById(R.id.textSumRevenuTotal);
        textSumSalaire=view.findViewById(R.id.textSumSalaire);
        textSumInteret=view.findViewById(R.id.textSumInteret);
        popUpDepenseMois.setVisibility(View.GONE);
        popUpRevenuMois.setVisibility(View.GONE);

        closePopupRevenuMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpRevenuMois.setVisibility(View.GONE);
                buttonDepense.setVisibility(View.VISIBLE);
                buttonRevenu.setVisibility(View.VISIBLE);
            }
        });

        closePopupDepenseMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDepenseMois.setVisibility(View.GONE);
                buttonDepense.setVisibility(View.VISIBLE);
                buttonRevenu.setVisibility(View.VISIBLE);
            }
        });

        textDepenseSurLeMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                popUpRevenuMois.setVisibility(View.GONE);
                popUpDepenseMois.setVisibility(View.VISIBLE);
                buttonDepense.setVisibility(View.GONE);
                buttonRevenu.setVisibility(View.GONE);


            }
        });

        textRevenuSurLeMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("je clique sur popup");


                popUpDepenseMois.setVisibility(View.GONE);
                popUpRevenuMois.setVisibility(View.VISIBLE);
                buttonDepense.setVisibility(View.GONE);
                buttonRevenu.setVisibility(View.GONE);
            }
        });







        List annee = new ArrayList();
        annee.add("2025");
        annee.add("2024");
        annee.add("2023");
        annee.add("2022");
        annee.add("2021");
        annee.add("2020");

        ArrayAdapter adapterAnnee = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, annee);
        adapterAnnee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List compte = new ArrayList();
        compte.add("Principal");
        compte.add("Secondaire");




        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, compte);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseCompte.setAdapter(adapter2);

        spinnerChooseCompte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                compteChoisi = parent.getItemAtPosition(position).toString();
                System.out.println(compteChoisi);
                if(compteChoisi.equals("Principal")){
                    compteSelectionnee="compte";
                }else{
                    compteSelectionnee="compte2";
                }
                getData();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        List moisAnnee = new ArrayList();
        moisAnnee.add("Janvier");
        moisAnnee.add("Février");
        moisAnnee.add("Mars");
        moisAnnee.add("Avril");
        moisAnnee.add("Mai");
        moisAnnee.add("Juin");
        moisAnnee.add("Juillet");
        moisAnnee.add("Aout");
        moisAnnee.add("Septembre");
        moisAnnee.add("Octobre");
        moisAnnee.add("Novembre");
        moisAnnee.add("Décembre");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moisAnnee);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getAdapter().getItem(position).toString().equals("Janvier")){
                    numeroAnnee=01;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Février")){
                    numeroAnnee=02;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Mars")){
                    numeroAnnee=03;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Avril")){
                    numeroAnnee=04;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Mai")){
                    numeroAnnee=05;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Juin")){
                    numeroAnnee=06;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Juillet")){
                    numeroAnnee=07;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Aout")){
                    numeroAnnee=08f;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Septembre")){
                    numeroAnnee=09f;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Octobre")){
                    numeroAnnee=10;
                }else if(spinner.getAdapter().getItem(position).toString().equals("Novembre")){
                    numeroAnnee=11;
                }
                else if(spinner.getAdapter().getItem(position).toString().equals("Décembre")){
                    numeroAnnee=12;
                }

                getData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        buttonRevenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRevenu = new Intent(getActivity(), RevenuActivity.class);
                startActivity(intentRevenu);
            }
        });

        buttonDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDepense = new Intent(getActivity(), DepenseActivity.class);
                startActivity(intentDepense);
            }
        });

        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase", "Error getting data", task.getException());
                }else{
                    textDevise.setText(String.valueOf((task.getResult().getValue())));
                }
            }
        });

        getDataCompte();

        return view;
    }








    @Override
    public void onResume() {
        super.onResume();

        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("firebase", "Error getting data", task.getException());
                }else{
                    System.out.println(String.valueOf(task.getResult().getValue()));
                    capital = String.valueOf(task.getResult().getValue());
                    capitalDouble = Double.parseDouble(capital);
                    capitalWithoutManyNumbers = (double) Math.round(capitalDouble*100)/100;
                    textCapital.setText(String.valueOf(capitalWithoutManyNumbers));
                }
            }
        });

    }

    public void getDataCompte(){
        Query recupCompte2 = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2");
        recupCompte2
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postSnaphot : snapshot.getChildren()){
                            String montant =postSnaphot.child("capital").getValue().toString();

                            Log.d("dfh", montant);



                            if(Integer.parseInt(montant)>0){

                                spinnerChooseCompte.setVisibility(View.VISIBLE);
                            }


                            Double capitalDouble = Double.parseDouble(montant);
                            Double capitalWithoutManyNumbers = (double) Math.round(capitalDouble*100)/100;


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void getData(){



        Query recupDepense = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(compteSelectionnee).child("0").child("expenses");
        recupDepense.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                montantNourriture.clear();
                montantEducation.clear();
                montantTransport.clear();
                montantCharges.clear();
                montantFacture.clear();
                montrantAutresDepenses.clear();
                depenses.clear();


                System.out.println("je passe par icic 2 "+depenses);


                for (DataSnapshot postSnaphot : snapshot.getChildren()) {






                    System.out.println("je passe apr icic" + depenses);
                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("categorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();

                    Float month = Float.parseFloat(date.substring(3, 5));
                    Float year = Float.parseFloat(date.substring(6, 10));
                    Float day = Float.parseFloat(date.substring(0, 2));

                    System.out.println(month);
                    System.out.println(numeroAnnee);
                    System.out.println(year);
                    System.out.println(anneeForFilter);



                    if (month.equals(numeroAnnee)) {

                        listMontantDepenses.clear();
                        depenses.clear();
                        System.out.println("je suis dedans");


                        if (categorie.equals("Nourriture")) {

                            montantNourriture.add(Float.parseFloat(montant));
                            System.out.println("total de nourritre : "+montantNourriture);


                        }
                        if (categorie.equals("Education")) {

                            montantEducation.add(Float.parseFloat(montant));


                        }
                        if (categorie.equals("transport")) {

                            montantTransport.add(Float.parseFloat(montant));


                        }
                        if (categorie.equals("Charges")) {

                            montantCharges.add(Float.parseFloat(montant));


                        }
                        if (categorie.equals("Factures")) {

                            montantFacture.add(Float.parseFloat(montant));


                        }
                        if (categorie.equals("autres")) {

                            montrantAutresDepenses.add(Float.parseFloat(montant));


                        }

                        double sumEducation=0;
                        double sumNourriture=0;
                        double sumFacture=0;
                        double sumCharges=0;
                        double sumTransport=0;
                        double sumAutresDepense=0;
                        sumDepense=0;
                        total=0;


                        textExpenseTotal.setText(sumDepenseString);
                        textRevenuTotal.setText(sumRevenuString);
                        textSumNourriture.setText(String.valueOf(sumNourriture));
                        textSumEducation.setText(String.valueOf(sumEducation));
                        textSumTransport.setText(String.valueOf(sumTransport));
                        textSumactures.setText(String.valueOf(sumFacture));
                        textSumAutres.setText(String.valueOf(sumAutresDepense));
                        textSumCharges.setText(String.valueOf(sumCharges));






                        for (int i = 0; i < montantNourriture.size(); i++) {

                            sumNourriture += montantNourriture.get(i);
                            textSumNourriture.setText(String.valueOf(sumNourriture));

                        }

                        for (int i = 0; i < montantEducation.size(); i++) {

                            sumEducation += montantEducation.get(i);
                            textSumEducation.setText(String.valueOf(sumEducation));

                        }

                        for (int i = 0; i < montantTransport.size(); i++) {

                            sumTransport += montantTransport.get(i);
                            textSumTransport.setText(String.valueOf(sumTransport));
                        }


                        for (int i = 0; i < montantCharges.size(); i++) {
                            sumCharges += montantCharges.get(i);

                            textSumCharges.setText(String.valueOf(sumCharges));
                        }

                        for (int i = 0; i < montantFacture.size(); i++) {
                            sumFacture += montantFacture.get(i);
                            textSumactures.setText(String.valueOf(sumFacture));
                        }

                        for (int i = 0; i < montrantAutresDepenses.size(); i++) {
                            sumAutresDepense += montrantAutresDepenses.get(i);
                            textSumAutres.setText(String.valueOf(sumAutresDepense));
                        }

                            listMontantDepenses.add(Float.parseFloat(String.valueOf(Math.round(sumNourriture))));
                            categorieDepense.add("Nourriture");


                            listMontantDepenses.add(Float.parseFloat(String.valueOf(Math.round(sumEducation))));
                            categorieDepense.add("Education");


                            listMontantDepenses.add(Float.parseFloat(String.valueOf(Math.round(sumTransport))));
                            categorieDepense.add("Transport");



                            listMontantDepenses.add(Float.parseFloat(String.valueOf(Math.round(sumCharges))));
                            categorieDepense.add("Charges");


                            listMontantDepenses.add(Float.parseFloat(String.valueOf(Math.round(sumFacture))));
                            categorieDepense.add("Factures");

                            listMontantDepenses.add(Float.parseFloat(String.valueOf(Math.round(sumAutresDepense))));
                            categorieDepense.add("Autres");



                            for (int i = 0; i < listMontantDepenses.size(); i++) {


                            depenses.add(new PieEntry(Float.parseFloat(String.valueOf((listMontantDepenses.get(i)))), categorieDepense.get(i)));
                            System.out.println("dépenses "+depenses);


                        }

                            for(int i =0; i < listMontantDepenses.size();i++){
                                sumDepense +=listMontantDepenses.get(i);

                        }
                        sumDepenseString = String.valueOf(Math.round(sumDepense));
                        textExpenseTotal.setText(sumDepenseString);
                        textSumDepenseTotal.setText(sumDepenseString);

                        total = Math.round(sumRevenu - sumDepense);
                        System.out.println("depenses toal sur lr mois " +sumDepense);
                        textTotal.setText(String.valueOf(total));


                    }

                    if(depenses.size()==0){
                        sumDepenseString="0";
                        textTotal.setText("0");
                        textExpenseTotal.setText("0");
                        textSumDepenseTotal.setText("0");
                        textSumNourriture.setText("0");
                        textSumEducation.setText("0");
                        textSumTransport.setText("0");
                        textSumactures.setText("0");
                        textSumactures.setText("0");
                        textSumAutres.setText("0");
                        textSumCharges.setText("0");


                    }

                }


                    //
                    //   initPieChart();

                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(Color.parseColor("#E11E1E"));
                    colors.add(Color.parseColor("#1EAFE1"));
                    colors.add(Color.parseColor("#26B724"));
                    colors.add(Color.parseColor("#DE8231"));
                    colors.add(Color.parseColor("#DFCF13"));
                    colors.add(Color.parseColor("#1313DF"));
                    colors.add(Color.parseColor("#13DFBA"));


                    System.out.println("list montant depsenses = " + listMontantDepenses);




                    //collecting the entries with label name
                    PieDataSet pieDataSet2 = new PieDataSet(depenses, "");
                    //setting text size of the value
                    pieDataSet2.setValueTextSize(15f);
                    //providing color list for coloring different entries
                    pieDataSet2.setColors(colors);
                    //grouping the data set from entry to chart
                    PieData pieData2 = new PieData(pieDataSet2);
                    //showing the value of the entries, default true if not set
                    pieData2.setDrawValues(true);
                pieChart2.getDescription().setEnabled(false);

                    pieChart2.setData(pieData2);
                    pieChart2.invalidate();






            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query recupRevenu = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(compteSelectionnee).child("0").child("revenus");
        recupRevenu.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                montantSalaire.clear();
                montantInteret.clear();
                montantAutres.clear();

                revenus.clear();

                for (DataSnapshot postSnaphot : snapshot.getChildren()) {
                    String description = postSnaphot.child("description").getValue().toString();
                    montant = postSnaphot.child("montant").getValue().toString();
                    categorie = postSnaphot.child("catagorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();

                    Float month = Float.parseFloat(date.substring(3, 5));
                    Float year = Float.parseFloat(date.substring(6, 10));
                    Float day = Float.parseFloat(date.substring(0, 2));

                    if (month.equals(numeroAnnee)) {

                        revenus.clear();
                        listMontantRevenu.clear();

                        if (categorie.equals("Salaire")) {

                            montantSalaire.add(Float.parseFloat(montant));


                        }
                        if (categorie.equals("Interet")) {

                            montantInteret.add(Float.parseFloat(montant));


                        }
                        if (categorie.equals("autres")) {

                            montantAutres.add(Float.parseFloat(montant));


                        }

                        double sumSalaire =0;
                        double sumInteret=0;
                        double sumAutres=0;
                        sumRevenu=0;
                        total=0;





                        for (int i = 0; i < montantSalaire.size(); i++) {
                            sumSalaire += montantSalaire.get(i);
                            textSumSalaire.setText(String.valueOf(Math.round(sumSalaire)));
                        }

                        for (int i = 0; i < montantInteret.size(); i++) {
                            sumInteret += montantInteret.get(i);
                            textSumInteret.setText(String.valueOf(Math.round(sumInteret)));
                        }

                        for (int i = 0; i < montantAutres.size(); i++) {
                            sumAutres += montantAutres.get(i);
                            textSumAutresRevenus.setText(String.valueOf(Math.round(sumAutres)));
                        }


                            listMontantRevenu.add(Float.parseFloat(String.valueOf(Math.round(sumSalaire))));
                            categorieRevenu.add("Salaire");


                            listMontantRevenu.add(Float.parseFloat(String.valueOf(Math.round(sumInteret))));
                            categorieRevenu.add("Interet");

                            listMontantRevenu.add(Float.parseFloat(String.valueOf(Math.round(sumAutres))));
                            categorieRevenu.add("Autres");


                        for(int i =0; i < listMontantRevenu.size(); i++){
                            revenus.add(new PieEntry(Float.parseFloat(String.valueOf((listMontantRevenu.get(i)))), categorieRevenu.get(i)));

                        }
                        for(int i =0; i < listMontantRevenu.size();i++){
                            sumRevenu +=listMontantRevenu.get(i);

                        }
                        sumRevenuString = String.valueOf(Math.round(sumRevenu));
                        textRevenuTotal.setText(sumRevenuString);
                        textSumRevenuTotal.setText(sumRevenuString);

                        total = Math.round(sumRevenu-sumDepense);
                        textTotal.setText(String.valueOf(total));








                    }

                    if(revenus.size()==0) {
                        textTotal.setText("0");
                        sumRevenuString = "0";
                        textRevenuTotal.setText("0");
                        textSumRevenuTotal.setText("0");
                        textSumSalaire.setText("0");
                        textSumInteret.setText("0");
                        textSumAutresRevenus.setText("0");







                    }


                }

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.parseColor("#E11E1E"));
                colors.add(Color.parseColor("#1EAFE1"));
                colors.add(Color.parseColor("#26B724"));
                colors.add(Color.parseColor("#DE8231"));
                colors.add(Color.parseColor("#DFCF13"));
                colors.add(Color.parseColor("#1313DF"));
                colors.add(Color.parseColor("#13DFBA"));



                System.out.println(listMontantRevenu);





                //collecting the entries with label name
                PieDataSet pieDataSet = new PieDataSet(revenus,"");
                //setting text size of the value
                pieDataSet.setValueTextSize(15f);
                //providing color list for coloring different entries
                pieDataSet.setColors(colors);
                //grouping the data set from entry to chart
                PieData pieData = new PieData(pieDataSet);
                //showing the value of the entries, default true if not set
                pieData.setDrawValues(true);

                pieChart.setData(pieData);
                pieChart.invalidate();
                pieChart
                        .getDescription().setEnabled(false);










            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}