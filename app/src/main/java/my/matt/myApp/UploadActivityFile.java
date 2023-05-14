package my.matt.myApp;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivityFile extends AppCompatActivity {


    public TextView editTextDeposeFile;
    public Button buttonDeposeFile, buttonRecupFile;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public String nomFichierRib="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        editTextDeposeFile= findViewById(R.id.editTextSelectFile);
        buttonDeposeFile = findViewById(R.id.buttonUploadFile);
        buttonRecupFile=findViewById(R.id.buttonRecupFile);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("uploadPDF");

        buttonDeposeFile.setEnabled(false);
        editTextDeposeFile.setText(nomFichierRib);

        buttonRecupFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               download();
            }
        });

        editTextDeposeFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });




    }

    public void download(){
        databaseReference= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        StorageReference reference = storageReference.child("uploadPDF");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFiles(UploadActivityFile.this, "uploadPDF1681643085112",  ".pdf", DIRECTORY_DOWNLOADS, url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager =(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName+fileExtension);

        downloadManager.enqueue(request);
    }

    private void selectPDF(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data !=null && data.getData() !=null){
            buttonDeposeFile.setEnabled((true));
            System.out.println(data.getDataString());
            nomFichierRib=data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/")+1);
            editTextDeposeFile.setText(data.getDataString()
            .substring(data.getDataString().lastIndexOf("/")+1));

            buttonDeposeFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPDFFileFirebase(Uri data){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fichier en train de charger");
        progressDialog.show();

        StorageReference reference = storageReference.child("Uploads").child("uploadPDF" + System.currentTimeMillis()+".pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri =  uriTask.getResult();

                        PutPDF putPDF = new PutPDF(editTextDeposeFile.getText().toString(), uri.toString());
                        databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(databaseReference.push().getKey()).setValue(putPDF);
                        Toast.makeText(UploadActivityFile.this, "File Upload", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Fichier téléverser" + (int) progress+ "%");

            }
        });
    }
    }
