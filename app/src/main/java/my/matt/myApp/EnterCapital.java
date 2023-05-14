package my.matt.myApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import my.matt.myApp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterCapital extends AppCompatActivity {

    EditText textCapital;
    Button buttonSuivant;
    private DatabaseReference mDatabse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(EnterCapital.this, LoginActivity.class);
            startActivity(intentHomePage);
        }
        super.onCreate(savedInstanceState);
        mDatabse= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String devise ="€";
        User newUser = new User(userId,email,0, devise);
        mDatabse.child("users").child(userId).setValue(newUser);
        setContentView(R.layout.activity_enter_capital);

        textCapital=findViewById(R.id.textFieldCapital);
        buttonSuivant=findViewById(R.id.buttonSuivant);

        buttonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabse.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("Capital").setValue(Float.parseFloat(textCapital.getText().toString()));
               mDatabse.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Devise").setValue(devise);
                Intent intentHomePage = new Intent(EnterCapital.this, MainActivity.class);
                startActivity(intentHomePage);


            }
        });
    }
}