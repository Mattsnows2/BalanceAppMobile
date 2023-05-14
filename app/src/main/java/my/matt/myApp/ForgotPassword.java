package my.matt.myApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {


    EditText enteryouremailforpassword;
     Button buttonForgotPassword;
        ImageView imageRetourForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(ForgotPassword.this, LoginActivity.class);
            startActivity(intentHomePage);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        enteryouremailforpassword = findViewById(R.id.enteryouremailforpassword);
        buttonForgotPassword=findViewById(R.id.buttonForgotPassword);
        imageRetourForgotPassword=findViewById(R.id.imageRetourForgotPassword);

        imageRetourForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHomePage = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intentHomePage);
            }
        });



        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = enteryouremailforpassword.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(ForgotPassword.this, "S'il vous plait entrer un eamil correct", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         Intent intentTest = new Intent(ForgotPassword.this, ForgotPasswordSuite.class);
                                         startActivity(intentTest);

                                     }else{
                                          Intent intentTest = new Intent(ForgotPassword.this, ForgotPasswordSuite.class);
                                         startActivity(intentTest);
                                     }
                                }
                            });
                }
            }
        });
    }
}