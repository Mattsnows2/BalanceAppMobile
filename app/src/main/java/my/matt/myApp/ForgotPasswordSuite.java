package my.matt.myApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordSuite extends AppCompatActivity {

    Button buttonOpenMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(ForgotPasswordSuite.this, LoginActivity.class);
            startActivity(intentHomePage);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_suite);
        buttonOpenMail= findViewById(R.id.buttonOpenMail);

        buttonOpenMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com"));
                startActivity(intent);
            }
        });
    }
}