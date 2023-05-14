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
import my.matt.myApp.models.Compte;
import my.matt.myApp.models.Revenu;
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

public class RevenuActivity extends AppCompatActivity {

    ImageView imageRetourRevenu;
    EditText description, montant;
    Spinner spinnerChooseCompteRevenu;
    Button validateOperation, categorie1, categorie2, categorie3, buttonDateRevenu;
    TextView textCategorie, resultatDateRevenu, textMontantNonvide,textDescriptionNonVide,textCategorieNonVide,textDateNonVide;
    private DatePicker datePickerRevenu;
    private DatabaseReference mDatabase;
    ArrayList<Revenu> revenus = new ArrayList<Revenu>();
    ArrayList<Revenu> revenusCompte2 = new ArrayList<Revenu>();
    final String [] newCapital = new String[1];
    ArrayList<Categorie> categories = new ArrayList<Categorie>();
    String compteChoisi="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(RevenuActivity.this, LoginActivity.class);
            startActivity(intentHomePage);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenu);
        mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();


        description=findViewById(R.id.fieldRevenuDescription);
        montant=findViewById(R.id.fieldRevenuMontant);
        validateOperation=findViewById(R.id.buttonValidateRevenu);
        categorie1 = findViewById(R.id.categorie1);
        categorie2 = findViewById(R.id.categorie2);
        categorie3 = findViewById(R.id.categorie3);
        spinnerChooseCompteRevenu=findViewById(R.id.spinnerChooseCompteRevenu);
        textCategorie=findViewById(R.id.textCategorieRevenu);
        datePickerRevenu=findViewById(R.id.datePickerRevenu);
        resultatDateRevenu=findViewById(R.id.resultatDateRevenu);
        buttonDateRevenu=findViewById(R.id.buttonDateRevenu);
        imageRetourRevenu=findViewById(R.id.imageRetourRevenu);
        textMontantNonvide=findViewById(R.id.textMontantNonvide);
        textDescriptionNonVide=findViewById(R.id.textDescriptionNonVide);
        textCategorieNonVide=findViewById(R.id.textCategorieNonVide);
        textDateNonVide=findViewById(R.id.textDateNonVide);



        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        List compte = new ArrayList();
        compte.add("Principal");
        compte.add("Secondaire");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRevenu);


        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, compte);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseCompteRevenu.setAdapter(adapter);

        spinnerChooseCompteRevenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                compteChoisi = parent.getItemAtPosition(position).toString();
                System.out.println(compteChoisi);
                if(compteChoisi.equals("Principal")){
                    recyclerView.setAdapter((new MyAdapterRevenu(getApplicationContext(), revenus)));
                }else{
                    recyclerView.setAdapter((new MyAdapterRevenu(getApplicationContext(), revenusCompte2)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        imageRetourRevenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTest = new Intent(RevenuActivity.this, MainActivity.class);
                startActivity(intentTest);
            }
        });

        this.datePickerRevenu.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerChange(datePickerRevenu, year, monthOfYear, dayOfMonth);
            }
        });

        buttonDateRevenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerRevenu.setVisibility(View.VISIBLE);
                categorie1.setVisibility(View.GONE);
                categorie2.setVisibility(View.GONE);
                categorie3.setVisibility(View.GONE);
                buttonDateRevenu.setVisibility(View.GONE);
                validateOperation.setVisibility(View.GONE);
            }
        });






        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadOperation();
        loadOperationCompte2();
        getData();

        categorie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCategorie.setText(categorie1.getText().toString());
            }
        });

        categorie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCategorie.setText(categorie2.getText().toString());
            }
        });

        categorie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCategorie.setText(categorie3.getText().toString());
            }
        });

        validateOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (montant.getText().toString().equals("") || montant.getText() == null) {
                    textMontantNonvide.setVisibility(View.VISIBLE);
                    textDateNonVide.setVisibility(View.GONE);
                    textDescriptionNonVide.setVisibility(View.GONE);
                    textCategorieNonVide.setVisibility(View.GONE);
                } else if (resultatDateRevenu == null || resultatDateRevenu.getText().equals("")) {
                    textDateNonVide.setVisibility(View.VISIBLE);
                    textDescriptionNonVide.setVisibility(View.GONE);
                    textCategorieNonVide.setVisibility(View.GONE);
                    textMontantNonvide.setVisibility(View.GONE);
                } else if (description.getText() == null || description.getText().toString().equals("")) {
                    textDescriptionNonVide.setVisibility(View.VISIBLE);
                    textCategorieNonVide.setVisibility(View.GONE);
                    textMontantNonvide.setVisibility(View.GONE);
                    textDateNonVide.setVisibility(View.GONE );
                } else if (textCategorie.getText() == null ||textCategorie.getText().equals("")) {
                    textCategorieNonVide.setVisibility(View.VISIBLE);
                    textDateNonVide.setVisibility(View.GONE);
                    textMontantNonvide.setVisibility(View.GONE);
                    textDescriptionNonVide.setVisibility(View.GONE);
                } else if (resultatDateRevenu != null && description.getText() != null
                        && textCategorie.getText() != null) {


                    if(compteChoisi.equals("Principal")){
                        revenus.add(new Revenu(1L,description.getText().toString(), montant.getText().toString(), textCategorie.getText().toString(), resultatDateRevenu.getText().toString()));
                        recyclerView.setAdapter(new MyAdapterRevenu(getApplicationContext(), revenus));

                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("revenus").setValue(revenus);
                        Log.d("my revenus", revenus.toString());
                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").get()
                                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                        } else {
                                            newCapital[0] = String.valueOf(task.getResult().getValue());
                                            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(Double.parseDouble(newCapital[0]) + Double.parseDouble(montant.getText().toString()));
                                        }
                                    }
                                });
                    }else if(compteChoisi.equals(("Secondaire"))){
                        revenusCompte2.add(new Revenu(1L,description.getText().toString(), montant.getText().toString(), textCategorie.getText().toString(), resultatDateRevenu.getText().toString()));
                        recyclerView.setAdapter(new MyAdapterRevenu(getApplicationContext(), revenusCompte2));

                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("revenus").setValue(revenusCompte2);

                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("Capital").get()
                                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                        } else {
                                            newCapital[0] = String.valueOf(task.getResult().getValue());
                                            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("capital").setValue(Double.parseDouble(newCapital[0]) + Double.parseDouble(montant.getText().toString()));
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

                            Log.d("dfh", montant);



                            if(Integer.parseInt(montant)>0){

                                spinnerChooseCompteRevenu.setVisibility(View.VISIBLE);
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

    public void loadOperationCompte2(){

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRevenu);
        Query recupRevenu = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").child("0").child("revenus");
        recupRevenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnaphot : snapshot.getChildren()) {
                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("catagorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();




                    Long id = (Long) postSnaphot.child("id").getValue();
                    revenusCompte2.add(new Revenu(id, description, montant, categorie, date));



                }
                recyclerView.setAdapter((new MyAdapterRevenu(getApplicationContext(), revenusCompte2)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void loadOperation(){

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRevenu);
        Query recupRevenu = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("revenus");
        recupRevenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnaphot : snapshot.getChildren()) {
                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("catagorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();
                    Long id = (Long) postSnaphot.child("id").getValue();
                    revenus.add(new Revenu(id, description, montant, categorie, date));



                }
                recyclerView.setAdapter((new MyAdapterRevenu(getApplicationContext(), revenus)));

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
        resultatDateRevenu.setText(date);
        datePicker.setVisibility(View.GONE);
        categorie1.setVisibility(View.VISIBLE);
        categorie2.setVisibility(View.VISIBLE);
        categorie3.setVisibility(View.VISIBLE);
        buttonDateRevenu.setVisibility(View.VISIBLE);
        validateOperation.setVisibility(View.VISIBLE);
    }
}