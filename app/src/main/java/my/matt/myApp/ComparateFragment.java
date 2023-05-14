package my.matt.myApp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ComparateFragment extends Fragment {

    TextView textLastMonth, textCurrentMonth, textDepenseMoisDernier, textDepenseMoisActuel, pourcentageEntreDeuxMois;

    private DatabaseReference mDatabase;
    Calendar currentMonth = Calendar.getInstance();
    Calendar lastMonth = Calendar.getInstance();
    float sumTotalCurrentMonth=0;
    float sumTotalLastMonth=0;
    Button buttonGnerateFichierExcel;
    public File filePath = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/DépensesMois.xls");
    String [] headers  = new String [] {"Catégorie","Date", "Montant"};
    ArrayList<Float> listMontant = new ArrayList<>();
    ArrayList<String> listCategorie = new ArrayList<>();
    ArrayList<String> listDate = new ArrayList<>();

    @SuppressLint("Range")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentHomePage);
        }



        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        View view = inflater.inflate(R.layout.fragment_comparate, container, false);

        mDatabase= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        textCurrentMonth = view.findViewById(R.id.textCurrentMonth);
        textLastMonth = view.findViewById(R.id.textLastMonth);
        textDepenseMoisActuel = view.findViewById(R.id.textDepenseMoisActuel);
        textDepenseMoisDernier = view.findViewById(R.id.textDepenseMoisDernier);
        pourcentageEntreDeuxMois=view.findViewById(R.id.pourcentageEntreDeuxMois);
        buttonGnerateFichierExcel=view.findViewById(R.id.buttonGnerateFichierExcel);

        buttonGnerateFichierExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet =  hssfWorkbook.createSheet();

                Row header1 = hssfSheet.createRow(0);
                header1.createCell(1
                ).setCellValue("Dépenses du  mois");
               // CellRangeAddress reg = new CellRangeAddress(0, (short)0,0,(short)3);
                //hssfSheet.addMergedRegion(reg);


                Row headerDepenseTotalMontant = hssfSheet.createRow(3);
                headerDepenseTotalMontant.createCell(1).setCellValue(sumTotalCurrentMonth + " €");


                Row r2 = hssfSheet.createRow(1);
               for(int i=1; i< listCategorie.size()+1; i++){

                    r2.createCell(i).setCellValue(listCategorie.get(i-1));
                }

                Row r = hssfSheet.createRow(2);
                for(int i=1; i < listDate.size()+1; i++){

                    r.createCell(i).setCellValue(listDate.get(i-1));
                }
                Row r1 = hssfSheet.createRow(3);
                for(int i=1; i< listMontant.size()+1; i++){

                    r1.createCell(i).setCellValue(listMontant.get(i-1) + " €");
                }
                try{


                if(!filePath.exists()){
                    filePath.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                hssfWorkbook.write(fileOutputStream);

                if(fileOutputStream !=null){
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

                    Toast.makeText(getActivity(), "Votre fichier a bien été téléchargé", Toast.LENGTH_SHORT).show();


            }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });


        getData();





        return view;


    }

    public void getData() {
        Query recupDepense = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("expenses");
        recupDepense.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postSnaphot : snapshot.getChildren()) {

                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("categorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();


                    String month = (date.substring(3, 5));
                    Float year = Float.parseFloat(date.substring(6, 10));
                    Float day = Float.parseFloat(date.substring(0, 2));


                    DateFormat dateFormat = new SimpleDateFormat("MM");
                    Date date2 = new Date();
                    DateFormat dateFormat2 = new SimpleDateFormat("MM");



                   String dateCurrentMonth = dateFormat.format(date2);
                   int dateInt = Integer.parseInt(dateCurrentMonth);
                   int dateLastMonth = dateInt-1;

                   String dateLestMonthString = String.valueOf(dateLastMonth);
                   if(dateLestMonthString.length()==1){
                       dateLestMonthString="0"+dateLestMonthString;
                   }


                    System.out.println(dateCurrentMonth);
                   if(dateCurrentMonth.equals("01")){
                       textCurrentMonth.setText("Janvier");
                   }else if (dateCurrentMonth.equals("02")){

                       textCurrentMonth.setText("Février");
                   }else if (dateCurrentMonth.equals("03")){
                       textCurrentMonth.setText("Mars");
                   }else if (dateCurrentMonth.equals("04")){
                       textCurrentMonth.setText("Avril");
                   }else if (dateCurrentMonth.equals("05")){
                       textCurrentMonth.setText("Mai");
                   }else if (dateCurrentMonth.equals("06")){
                       textCurrentMonth.setText("Juin");
                   }else if (dateCurrentMonth.equals("07")){
                       textCurrentMonth.setText("Juillet");
                   }else if (dateCurrentMonth.equals("08")){
                       textCurrentMonth.setText("Aout");
                   }else if (dateCurrentMonth.equals("09")){
                       textCurrentMonth.setText("Septembre");
                   }else if (dateCurrentMonth.equals("10")){
                       textCurrentMonth.setText("Octobre");
                   }else if (dateCurrentMonth.equals("11")){
                       textCurrentMonth.setText("Novembre");
                   }else if (dateCurrentMonth.equals("12")){
                       textCurrentMonth.setText("Décembre");
                   }

                    if(dateLestMonthString.equals("01")){
                        textLastMonth.setText("Janvier");
                    }else if (dateLestMonthString.equals("02")){
                        textLastMonth.setText("Février");
                    }else if (dateLestMonthString.equals("03")){
                        textLastMonth.setText("Mars");
                    }else if (dateLestMonthString.equals("04")){
                        textLastMonth.setText("Avril");
                    }else if (dateLestMonthString.equals("05")){
                        textLastMonth.setText("Mai");
                    }else if (dateLestMonthString.equals("06")){
                        textLastMonth.setText("Juin");
                    }else if (dateLestMonthString.equals("07")){
                        textLastMonth.setText("Juillet");
                    }else if (dateLestMonthString.equals("08")){
                        textLastMonth.setText("Aout");
                    }else if (dateLestMonthString.equals("09")){
                        textLastMonth.setText("Septembre");
                    }else if (dateLestMonthString.equals("10")){
                        textLastMonth.setText("Octobre");
                    }else if (dateLestMonthString.equals("11")){
                        textLastMonth.setText("Novembre");
                    }else if (dateLestMonthString.equals("12")){
                        textLastMonth.setText("Décembre");
                    }



                    if (month.equals(dateCurrentMonth) ) {


                        listMontant.add(Float.parseFloat(montant));
                        listCategorie.add(categorie);
                        listDate.add(date);

                        sumTotalCurrentMonth = sumTotalCurrentMonth + Float.parseFloat(montant);
                        textDepenseMoisActuel.setText(String.valueOf(sumTotalCurrentMonth));




                    }else if(month.equals(dateLestMonthString)){


                        sumTotalLastMonth = sumTotalLastMonth + Float.parseFloat(montant);
                        textDepenseMoisDernier.setText(String.valueOf(sumTotalLastMonth));


                    }


                }

                float compareDepense= 0;

                compareDepense = Math.round((sumTotalCurrentMonth-sumTotalLastMonth)/sumTotalLastMonth)*100;
                System.out.println(sumTotalCurrentMonth);
                System.out.println(sumTotalLastMonth);

                if(compareDepense<0){
                    float abs = Math.abs(compareDepense);
                    pourcentageEntreDeuxMois.setText("Vous avez dépensé " + String.valueOf(abs)+"% en moins par rapport au mois précédant");
                }else{
                    pourcentageEntreDeuxMois.setText("Vous avez dépensé " + String.valueOf(compareDepense)+"% en plus par rapport au mois précédant");

                }




            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}