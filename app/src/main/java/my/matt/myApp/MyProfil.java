package my.matt.myApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyProfil extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    TextView textYourEmail, textYourPassword,buttonBack;
    EditText editTextNewPassword, editTextNewEmail;
    Button buttonChangeMail, buttonChangePassword,buttonFichier, pageCompte;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentHomePage);
        }

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_my_profil, container, false);
        mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        textYourEmail = view.findViewById(R.id.textYourMail);
        textYourPassword = view.findViewById(R.id.textYourPassword);
        editTextNewPassword = view.findViewById(R.id.EditTextNewPassword);
        editTextNewEmail = view.findViewById(R.id.EditTextNewEmail);
        buttonChangeMail = view.findViewById(R.id.buttonChangeMail);
        buttonChangePassword = view.findViewById(R.id.buttonChangePassword);
        buttonBack=view.findViewById(R.id.buttonBack);
        buttonFichier=view.findViewById(R.id.pageFichier);
        pageCompte=view.findViewById(R.id.pageCompte);
        String email = mAuth.getCurrentUser().getEmail().toString();

        textYourEmail.setText(email);

        buttonFichier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadActivityFile.class);
                startActivity(intent);
            }
        });

        pageCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PageCompte.class);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTest = new Intent(getActivity(), MainActivity.class);
                startActivity(intentTest);
            }
        });
        buttonChangeMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail(firebaseUser);
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(firebaseUser);
            }
        });

        return view;

    }

    public void changeEmail(FirebaseUser firebaseUser){
        String newEmail = editTextNewEmail.getText().toString();
        firebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Email changé", Toast.LENGTH_SHORT).show();
                    mAuth.getInstance().signOut();
                    Intent intentTest = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentTest);

                }else{
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mAuth.getInstance().signOut();
    }

    public void changePassword(FirebaseUser firebaseUser){

        String newPassword = editTextNewPassword.getText().toString();
        firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Mot de passe changé", Toast.LENGTH_SHORT).show();
                    mAuth.getInstance().signOut();
                    Intent intentTest = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentTest);

                }else{
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mAuth.getInstance().signOut();

    }
}