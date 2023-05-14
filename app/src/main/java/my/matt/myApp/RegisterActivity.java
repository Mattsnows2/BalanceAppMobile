package my.matt.myApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    EditText email, password;
    TextView textEmailDejaPrise;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton=findViewById(R.id.buttonRegisterPage);
        email=findViewById(R.id.fieldEmail);
        password=findViewById(R.id.fieldPassword);
        textEmailDejaPrise=findViewById(R.id.textEmailDejaPrise);

        mAuth=FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(email.getText().toString().replaceAll("\\s", ""), password.getText().toString());
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if(currentUser!=null){
            reload();
        }
    }
    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("yo","createWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();



                            updateUI(user);


                            Intent intentStep1 = new Intent(RegisterActivity.this, EnterCapital.class);
                            startActivity(intentStep1);





                        }else{

                            textEmailDejaPrise.setVisibility(View.GONE);

                            updateUI(null);
                        }
                    }
                });
    }



    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}