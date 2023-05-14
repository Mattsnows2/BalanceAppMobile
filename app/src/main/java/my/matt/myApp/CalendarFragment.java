package my.matt.myApp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment implements  CalendarAdapter.OnItemListener {


    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectDate;
    private Button buttonPreviousMonthAction, buttonNextMonthAction;
    private DatabaseReference mDatabase;
    int i=1;
    boolean test = false;
    boolean isDepenseFiltre=false, isRevenuFiltre=false;
    private Spinner spinnerCalendar;
    public ArrayList<String> montantList = new ArrayList();
    public ArrayList<String> montantListRevenu = new ArrayList<>();
    ArrayList<String> montantInMonthArray = new ArrayList<>();
    ArrayList<String> montantInMonthArrayRevenu = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentHomePage);
        }

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mDatabase= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYear);
        buttonNextMonthAction = view.findViewById(R.id.buttonNextMonthAction);
        buttonPreviousMonthAction = view.findViewById(R.id.buttonPreviousMonthAction);
        spinnerCalendar=view.findViewById(R.id.spinnerCalendar);


        selectDate = LocalDate.now();
         setMonthView();

        buttonPreviousMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("je suis ici 1");
                selectDate = selectDate.minusMonths(1); ;

                monthYearText.setText(monthYearFromDate(selectDate));
                test = true;
                setMonthView();
            }
        });

        buttonNextMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate= selectDate.plusMonths(1);



                 monthYearText.setText(monthYearFromDate(selectDate));
                test=true;
                setMonthView();
            }
        });

        List choiceSpinnerCalendar = new ArrayList();
        choiceSpinnerCalendar.add("");
        choiceSpinnerCalendar.add("Dépense");
        choiceSpinnerCalendar.add("Revenu");
        choiceSpinnerCalendar.add("Dépense/Revenu");

        ArrayAdapter adapterCalendar = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, choiceSpinnerCalendar);
        adapterCalendar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCalendar.setAdapter(adapterCalendar);
        spinnerCalendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String result  = parent.getItemAtPosition(position).toString();
                if(result.equals("Dépense")){
                   setMonthView();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {

    if(montantInMonthArray.size()>0 && test){
        montantInMonthArray.clear();
        test= false;
    }


        System.out.println(montantInMonthArray.size());


        monthYearText.setText(monthYearFromDate(selectDate));

        ArrayList<String> dayInMonth = daysInMonthArray(selectDate);
        ArrayList<String> montantOfDay= new ArrayList<>();
        ArrayList<String> montantOfDayRevenu= new ArrayList<>();





           montantOfDay = montantInMonthArray(selectDate);





        if(montantOfDay.size()>0 ){
            CalendarAdapter calendarAdapter = new CalendarAdapter(dayInMonth, montantOfDay, this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
            calendarRecyclerView.setLayoutManager(layoutManager);
            calendarRecyclerView.setAdapter(calendarAdapter);
        }





    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(date);


        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectDate.withDayOfMonth(1);

        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");


            } else {

                daysInMonthArray.add(String.valueOf(i - dayOfWeek));


            }
        }


        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> montantInMonthArray(LocalDate date) {




        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        for ( i = 1; i <= 42; i++) {
            if (i >= dayOfWeek || i < daysInMonth + dayOfWeek) {
                getData(String.valueOf(i - dayOfWeek), monthYearFromDate(selectDate));
            } else {
                montantInMonthArray.add("");
            }
        }


        return montantInMonthArray;
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")){
            getData(dayText, monthYearFromDate(selectDate));
            String message = "Selected Date" + dayText + " " + monthYearFromDate(selectDate);






        }
    }




    private List<String> getData(String dayText, String monthSelected) {

        montantList.clear();

       Query recupDepense = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("expenses");
        recupDepense.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                montantList.clear();
                for (DataSnapshot postSnaphot : snapshot.getChildren()) {

                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("categorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();


                    int month = Integer.parseInt(date.substring(3, 5));
                    int year = Integer.parseInt(date.substring(6, 10));
                    Integer day = Integer.parseInt(date.substring(0, 2));

                    String moisEnchiffre = "";

                    if(month==1){
                        moisEnchiffre="janvier";
                    }else if(month==2){
                        moisEnchiffre="février";
                    }else if(month==3){
                        moisEnchiffre="mars";
                    }else if(month==4){
                        moisEnchiffre="avril";
                    }else if(month==5){
                        moisEnchiffre="mai";
                    }else if(month==6){
                        moisEnchiffre="juin";
                    }else if(month==7){
                        moisEnchiffre="juillet";
                    }else if(month==8){
                        moisEnchiffre="aout";
                    }else if(month==9){
                        moisEnchiffre="septembre";
                    }else if(month==10){
                        moisEnchiffre="octobre";
                    }else if(month==11){
                        moisEnchiffre="novembre";
                    }
                    else if(month==12){
                        moisEnchiffre="decembre";
                    }
                    String moisAnnee = moisEnchiffre+" "+String.valueOf(year);





                    if(dayText.equals(String.valueOf(day)) && monthSelected.equals(moisAnnee)) {

                        float sum = 0;
                        montantList.add(montant);



                        if (montantList.size() == 1) {
                            sum = Float.parseFloat(montant);
                        } else if (montantList.size() >= 2) {
                            for (int i = 0; i < montantList.size(); i++) {
                                sum = sum + Float.parseFloat(montantList.get(i));
                            }
                        } else {
                            sum = 0;
                        }

                        System.out.println(montant+""+montantList);
                    }

                }

                if (montantList.size() == 0) {
                    montantInMonthArray.add("");
                }else{
                    montantInMonthArray.add("-"+montantList.get(0));

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        System.out.println(montantInMonthArray);
     //   montantInMonthArray.add(String.valueOf(montantList));


        return montantList;

    }

}