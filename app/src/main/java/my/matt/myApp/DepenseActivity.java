package my.matt.myApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import my.matt.myApp.models.Categorie;
import my.matt.myApp.models.Depense;
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
import java.util.Calendar;
import java.util.List;

public class DepenseActivity extends AppCompatActivity {

    ImageView imageRetourDepense;
    EditText textDescription, textMontant;
    Spinner spinnerChooseCompteDepense;
    Button buttonValidatedepense, categorie1, categorie2, categorie3, categorie4, categorie5, categorie6,  buttonDateDepense;
    TextView textCatgorie, resultatTextDepense , textMontantNonvide,textDescriptionNonVide,textCategorieNonVide,textDateNonVide;
    private DatePicker datePickerDepense;
    private DatabaseReference mDatabase;
    final String[] newCapital = new String[1];
    ArrayList<Depense> depenses = new ArrayList<Depense>();
    ArrayList<Depense> depensesCompte2 = new ArrayList<Depense>();
    ArrayList<Categorie> categories = new ArrayList<Categorie>();
    String compteChoisi="";
    boolean spinnerIsVisible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(DepenseActivity.this, LoginActivity.class);
            startActivity(intentHomePage);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense);
        mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        textDescription = findViewById(R.id.fieldDepenseDescription);
        textMontant = findViewById(R.id.fieldDepenseMontant);
        buttonValidatedepense = findViewById(R.id.buttonValidatedepense);
        textCatgorie = findViewById(R.id.textCategorie);
        categorie1 = findViewById(R.id.categorie1);
        categorie2 = findViewById(R.id.categorie2);
        categorie3 = findViewById(R.id.categorie3);
        categorie4 = findViewById(R.id.categorie4);
        categorie5 = findViewById(R.id.categorie5);
        categorie6 = findViewById(R.id.categorie6);
        spinnerChooseCompteDepense=findViewById(R.id.spinnerChooseCompteDepense);
        buttonDateDepense=findViewById(R.id.buttonDateDepense);
        resultatTextDepense=findViewById(R.id.resultatDateDepense);
        datePickerDepense=findViewById(R.id.datePickerDepense);
        imageRetourDepense=findViewById(R.id.imageRetourDepense);
        textMontantNonvide=findViewById(R.id.textMontantNonvide);
        textDescriptionNonVide=findViewById(R.id.textDescriptionNonVide);
        textCategorieNonVide=findViewById(R.id.textCategorieNonVide);
        textDateNonVide=findViewById(R.id.textDateNonVide);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        getData();

        List compte = new ArrayList();
            compte.add("Principal");
            compte.add("Secondaire");







        RecyclerView recyclerView = findViewById(R.id.recyclerViewDepense);





            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, compte);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerChooseCompteDepense.setAdapter(adapter);

            spinnerChooseCompteDepense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    compteChoisi = parent.getItemAtPosition(position).toString();
                    if (compteChoisi.equals("Principal")) {
                        recyclerView.setAdapter((new MyAdapter(getApplicationContext(), depenses)));
                    } else {
                        recyclerView.setAdapter((new MyAdapter(getApplicationContext(), depensesCompte2)));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });






        this.datePickerDepense.init(year, month, day, new  DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){




                datePickerChange(datePickerDepense, year, monthOfYear, dayOfMonth);
            }
        });

        buttonDateDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDepense.setVisibility(View.VISIBLE);
                categorie1.setVisibility(View.GONE);
                categorie2.setVisibility(View.GONE);
                categorie3.setVisibility(View.GONE);
                categorie4.setVisibility(View.GONE);
                categorie5.setVisibility(View.GONE);
                categorie6.setVisibility(View.GONE);
                buttonDateDepense.setVisibility(View.GONE);
                buttonValidatedepense.setVisibility(View.GONE);
            }
        });

        imageRetourDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTest = new Intent(DepenseActivity.this, MainActivity.class);
                startActivity(intentTest);
            }
        });

        categorie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCatgorie.setText(categorie1.getText().toString());
            }
        });

        categorie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCatgorie.setText(categorie2.getText().toString());
            }
        });

        categorie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCatgorie.setText(categorie3.getText().toString());
            }
        });

        categorie4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCatgorie.setText(categorie4.getText().toString());
            }
        });

        categorie5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCatgorie.setText(categorie5.getText().toString());
            }
        });

        categorie6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCatgorie.setText(categorie6.getText().toString());
            }
        });
        loadLastOperationCompte2();
        loadLastOperation();

       recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonValidatedepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textMontant.getText().toString().equals("") || textMontant.getText() == null) {
                    textMontantNonvide.setVisibility(View.VISIBLE);
                    textDateNonVide.setVisibility(View.GONE);
                    textDescriptionNonVide.setVisibility(View.GONE);
                    textCategorieNonVide.setVisibility(View.GONE);
                    System.out.println("text date ="+resultatTextDepense.getText().toString());
                } else if (resultatTextDepense == null || resultatTextDepense.getText().equals("")) {
                    textDateNonVide.setVisibility(View.VISIBLE);
                    textDescriptionNonVide.setVisibility(View.GONE);
                    textCategorieNonVide.setVisibility(View.GONE);
                    textMontantNonvide.setVisibility(View.GONE);


                } else if (textDescription.getText() == null || textDescription.getText().toString().equals("")) {
                    textDescriptionNonVide.setVisibility(View.VISIBLE);
                    textCategorieNonVide.setVisibility(View.GONE);
                    textMontantNonvide.setVisibility(View.GONE);
                    textDateNonVide.setVisibility(View.GONE);
                } else if (textCatgorie.getText() == null || textCatgorie.getText().equals("")) {
                    textCategorieNonVide.setVisibility(View.VISIBLE);
                    textDescriptionNonVide.setVisibility(View.GONE);
                    textMontantNonvide.setVisibility(View.GONE);
                    textDateNonVide.setVisibility(View.GONE);


                } else if (resultatTextDepense != null && textDescription.getText() != null
                        && textCatgorie.getText() != null) {


                    if(compteChoisi.equals("Principal")){

                        depenses.add(new Depense(textDescription.getText().toString(), textMontant.getText().toString(), textCatgorie.getText().toString(), resultatTextDepense.getText().toString()));

                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), depenses));

                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("expenses").setValue(depenses);
                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").get()
                                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {

                                        } else {

                                            newCapital[0] = String.valueOf(task.getResult().getValue());
                                            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(Double.parseDouble(newCapital[0]) - Double.parseDouble(textMontant.getText().toString()));
                                        }
                                    }
                                });
                    }else if(compteChoisi.equals("Secondaire")){

                        depensesCompte2.add(new Depense(textDescription.getText().toString(), textMontant.getText().toString(), textCatgorie.getText().toString(), resultatTextDepense.getText().toString()));

                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), depensesCompte2));


                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("expenses").setValue(depensesCompte2);
                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("capital").get()
                                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {

                                        } else {

                                            newCapital[0] = String.valueOf(task.getResult().getValue());
                                            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("capital").setValue(Double.parseDouble(newCapital[0]) - Double.parseDouble(textMontant.getText().toString()));
                                        }
                                    }
                                });
                    }


                    finish();
                    startActivity(getIntent());
                }

            }
        });


    }

    public void getData(){
        Query recupCompte2 = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2");
        recupCompte2
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postSnaphot : snapshot.getChildren()){
                            String montant =postSnaphot.child("capital").getValue().toString();


                            if(Integer.parseInt(montant)>0){
                                spinnerIsVisible=false;
                            }
                            System.out.println("je suis ici"+montant);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }



    public void loadLastOperation(){



        Query recupDepense = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("expenses");
        recupDepense.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnaphot : snapshot.getChildren()) {
                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("categorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();
                    depenses.add(new Depense(description, montant, categorie, date));



                }
                System.out.println(depenses);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void loadLastOperationCompte2(){




        Query recupDepense = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("expenses");
        recupDepense.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnaphot : snapshot.getChildren()) {
                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("categorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();



                    depensesCompte2.add(new Depense(description, montant, categorie, date));



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth){

        int month2= month+1;
        String fm=""+month2;
        String fd=""+dayOfMonth;
        if(month2<10){
            fm ="0"+month2;
        }
        if (dayOfMonth<10){
            fd="0"+dayOfMonth;
        }
        //tester cette fonction
        String date = fd+"-"+fm+"-"+year;

        System.out.println(date);
        resultatTextDepense.setText(date);
        datePicker.setVisibility(View.GONE);
        categorie1.setVisibility(View.VISIBLE);
        categorie2.setVisibility(View.VISIBLE);
        categorie3.setVisibility(View.VISIBLE);
        categorie4.setVisibility(View.VISIBLE);
        categorie5.setVisibility(View.VISIBLE);
        categorie6.setVisibility(View.VISIBLE);
        buttonDateDepense.setVisibility(View.VISIBLE);
        buttonValidatedepense.setVisibility(View.VISIBLE);
    }
}