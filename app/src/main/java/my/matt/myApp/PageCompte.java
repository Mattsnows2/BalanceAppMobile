package my.matt.myApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.matt.myApp.models.Compte;
import my.matt.myApp.models.Depense;
import my.matt.myApp.models.Revenu;

public class PageCompte extends AppCompatActivity {


    private DatabaseReference mDatabase;
    ArrayList<Compte> compte = new ArrayList<Compte>();
    ArrayList<Compte> newCompte = new ArrayList<Compte>();
    ArrayList<Depense> expenses = new ArrayList<Depense>();
    ArrayList<Revenu> revenus = new ArrayList<Revenu>();
    ImageView imageRetourRevenu;
    TextView nomPremierCompte, nomSecondCompte, montantPremierCompte, montantSecondCompte, textSeparation;

    CardView cardViewAjouterCompte;
    ImageView imageAccountTwo;
    Button afficherPopupCardView,ajouterNouveauCompte;

    TextView textSecondCompte;
    EditText editTextCapital, editTextDevise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_compte);
        mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        cardViewAjouterCompte= findViewById(R.id.cardViewAjouterCompte);
        afficherPopupCardView=findViewById(R.id.afficherPopupCardView);
        ajouterNouveauCompte=findViewById(R.id.ajouterNouveauCompte);
        editTextCapital=findViewById(R.id.editTextCapital);
        editTextDevise=findViewById(R.id.editTextDevise);
        nomPremierCompte=findViewById(R.id.nomPremierCompte);
        nomSecondCompte = findViewById(R.id.nomSecondCompte);
        montantPremierCompte=findViewById(R.id.montantPremierCompte);
        montantSecondCompte = findViewById(R.id.montantSecondCompte);
        textSecondCompte=findViewById(R.id.textSecondCompte);
        imageAccountTwo=findViewById(R.id.imageAccountTwo);
        textSeparation=findViewById(R.id.textSeparation);
        imageRetourRevenu=findViewById(R.id.imageRetourRevenu);
        getData();

        montantSecondCompte.setVisibility(View.GONE);
        nomSecondCompte.setVisibility(View.GONE);
        textSecondCompte.setVisibility(View.GONE);
        imageAccountTwo.setVisibility(View.GONE);
        textSeparation.setVisibility(View.GONE);




        afficherPopupCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewAjouterCompte.setVisibility(View.VISIBLE);
            }
        });

        imageRetourRevenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTest = new Intent(PageCompte.this, MyProfil.class);
                startActivity(intentTest);
            }
        });

        ajouterNouveauCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newCompte.add(new Compte(editTextCapital.getText().toString()));
                mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2").setValue(newCompte);
                cardViewAjouterCompte.setVisibility(View.GONE);
            }
        });
    }

    public void getData(){
        Query recupCompte = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte");
        recupCompte.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnaphot : snapshot.getChildren()){
                    String montant =postSnaphot.child("Capital").getValue().toString();
                    compte.add(new Compte(montant, expenses, revenus));
                    Double capitalDouble = Double.parseDouble(montant);
                    Double capitalWithoutManyNumbers = (double) Math.round(capitalDouble*100)/100;
                    montantPremierCompte.setText(String.valueOf(capitalWithoutManyNumbers) + " €");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Query recupCompte2 = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte2");
        recupCompte2
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnaphot : snapshot.getChildren()){
                    String montant =postSnaphot.child("capital").getValue().toString();

                    Log.d("dfh", montant);

                    compte.add(new Compte(montant, expenses, revenus));

                    if(Integer.parseInt(montant)>0){
                        afficherPopupCardView.setVisibility(View.GONE);
                        montantSecondCompte.setVisibility(View.VISIBLE);
                        nomSecondCompte.setVisibility(View.VISIBLE);
                        textSecondCompte.setVisibility(View.VISIBLE);
                        imageAccountTwo.setVisibility(View.VISIBLE);
                        textSeparation.setVisibility(View.VISIBLE);
                    }


                    Double capitalDouble = Double.parseDouble(montant);
                    Double capitalWithoutManyNumbers = (double) Math.round(capitalDouble*100)/100;


                    montantSecondCompte.setText(String.valueOf(capitalWithoutManyNumbers)+" €");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}