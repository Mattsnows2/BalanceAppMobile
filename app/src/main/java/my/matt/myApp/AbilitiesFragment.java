package my.matt.myApp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesFragment extends Fragment {

    BarChart barChart, barChartRevenus;
    PieChart pieChartRevenu, pieChartDepense;

    TextView resulatMonthPeriod;


    Spinner spinner;
    float numeroAnnee;
    float anneeForFilter;

    double sumDepense=0;

    private DatabaseReference mDatabase;
    float sum = 0;

    ArrayList<BarEntry> depenses = new ArrayList<>();
    ArrayList<BarEntry> revenus = new ArrayList<>();




    List<Float> listMontantRevenu = new ArrayList<>();
    List<Float> listMontantDepenses = new ArrayList<>();
    ArrayList<Float> montantSalaire = new ArrayList<>();
    ArrayList<Float> montantInteret = new ArrayList<>();
    ArrayList<String> categorieRevenu = new ArrayList<>();
    ArrayList<String> categorieDepense = new ArrayList<>();

    ArrayList<Float> montantNourriture = new ArrayList<>();
    ArrayList<Float> montantEducation= new ArrayList<>();
    ArrayList<Float> montantTransport = new ArrayList<>();

    ArrayList<Float> montantCharges = new ArrayList<>();
    ArrayList<Float> montantFacture = new ArrayList<>();
    ArrayList<Float> montrantAutresDepenses = new ArrayList<>();
    ArrayList<Float> montantAutres = new ArrayList<>();

    ArrayList<Float> revenuJanvier = new ArrayList<>();
    ArrayList<Float> revenuFévrier= new ArrayList<>();
    ArrayList<Float> revenuMars = new ArrayList<>();
    ArrayList<Float> revenuAvril = new ArrayList<>();
    ArrayList<Float> revenuMai = new ArrayList<>();
    ArrayList<Float> revenuJuin = new ArrayList<>();
    ArrayList<Float> revenuJuillet = new ArrayList<>();
    ArrayList<Float> revenuAout = new ArrayList<>();
    ArrayList<Float> revenuSeptembre = new ArrayList<>();
    ArrayList<Float> revenuOctobre = new ArrayList<>();
    ArrayList<Float> revenuNovembre = new ArrayList<>();
    ArrayList<Float> revenuDecembre= new ArrayList<>();


    ArrayList<Float> depenseJanvier = new ArrayList<>();
    ArrayList<Float> depenseFévrier= new ArrayList<>();
    ArrayList<Float> depenseMars = new ArrayList<>();
    ArrayList<Float> depenseAvril = new ArrayList<>();
    ArrayList<Float> depenseMai = new ArrayList<>();
    ArrayList<Float> depenseJuin = new ArrayList<>();
    ArrayList<Float> depenseJuillet = new ArrayList<>();
    ArrayList<Float> depenseAout = new ArrayList<>();
    ArrayList<Float> depenseSeptembre = new ArrayList<>();
    ArrayList<Float> depenseOctobre = new ArrayList<>();
    ArrayList<Float> depenseNovembre = new ArrayList<>();
    ArrayList<Float> depenseDecembre= new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }else{
            Intent intentHomePage = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentHomePage);
        }

        View view = inflater.inflate(R.layout.fragment_abilities, container, false);
        mDatabase= FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        barChart = view.findViewById(R.id.barChartDepense);
        barChartRevenus=view.findViewById(R.id.barChartRevenu);
        pieChartRevenu=view.findViewById(R.id.pieChartRevenu);
        pieChartDepense=view.findViewById(R.id.pieChartDepense);

        getData();
        return view;
    }


    private void getData(){
        Query recupDepense = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("expenses");
        recupDepense.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot postSnaphot : snapshot.getChildren()) {

                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("categorie").getValue().toString();
                    String date  = postSnaphot.child("date").getValue().toString();


                    int month = Integer.parseInt(date.substring(3,5));
                    Float year = Float.parseFloat(date.substring(6,10));
                    Integer day = Integer.parseInt(date.substring(0,2));



                    switch(month){

                        case 1:
                            depenseJanvier.add(Float.parseFloat(montant));
                            break;

                        case 2:
                            depenseFévrier.add(Float.parseFloat(montant));
                            break;
                        case 3:
                            depenseMars.add(Float.parseFloat(montant));
                            break;
                        case 4:
                            depenseAvril.add(Float.parseFloat(montant));
                            break;
                        case 5:
                            depenseMai.add(Float.parseFloat(montant));
                            break;
                        case 6:
                            depenseJuin.add(Float.parseFloat(montant));
                            break;
                        case 7:
                            depenseJuillet.add(Float.parseFloat(montant));
                            break;
                        case 8:
                            depenseAout.add(Float.parseFloat(montant));
                            break;
                        case 9:
                            depenseSeptembre.add(Float.parseFloat(montant));
                            break;
                        case 10:
                            depenseOctobre.add(Float.parseFloat(montant));
                            break;
                        case 11:
                            depenseNovembre.add(Float.parseFloat(montant));
                            break;
                        case 12:
                            depenseDecembre.add(Float.parseFloat(montant));
                            break;
                    }

                    System.out.println(depenseJanvier.size());
                    System.out.println(depenseFévrier.size());

                    //input Y data (Months Data - 12 Values)
                    ArrayList<Double> valuesList = new ArrayList<Double>();
                    double sumJanvier2 = depenseJanvier.stream().mapToDouble(i -> i).sum();
                    double sumFevrier2 = depenseFévrier.stream().mapToDouble(i -> i).sum();
                    double sumMars2 = depenseMars.stream().mapToDouble(i -> i).sum();
                    double sumAvril2 = depenseAvril.stream().mapToDouble(i -> i).sum();
                    double sumMai2 = depenseMai.stream().mapToDouble(i -> i).sum();
                    double sumJuin2 = depenseJuin.stream().mapToDouble(i -> i).sum();
                    double sumJuillet2 = depenseJuillet.stream().mapToDouble(i -> i).sum();
                    double sumAout2 = depenseAout.stream().mapToDouble(i -> i).sum();
                    double sumSeptembre2 = depenseSeptembre.stream().mapToDouble(i -> i).sum();
                    double sumOctobre2 = depenseOctobre.stream().mapToDouble(i -> i).sum();
                    double sumNovembre2 = depenseNovembre.stream().mapToDouble(i -> i).sum();
                    double sumDecembre2 = depenseDecembre.stream().mapToDouble(i -> i).sum();




                    System.out.println(sumFevrier2);
                    System.out.println(sumMars2);
                    System.out.println(sumAvril2);



                    valuesList.add(sumJanvier2);
                    valuesList.add(sumFevrier2);
                    valuesList.add(sumMars2);
                    valuesList.add(sumAvril2);
                    valuesList.add(sumMai2);
                    valuesList.add(sumJuin2);
                    valuesList.add(sumJuillet2);
                    valuesList.add(sumAout2);
                    valuesList.add(sumSeptembre2);
                    valuesList.add(sumOctobre2);
                    valuesList.add(sumNovembre2);
                    valuesList.add(sumDecembre2);


                    Double max =  Double.MIN_VALUE;

                    for(Double i : valuesList){
                        if(max<i){
                            max=i;
                        }
                    }



                    //prepare Bar Entries
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    for (int i = 0; i < valuesList.size(); i++) {
                        BarEntry barEntry = new BarEntry(i + 1, valuesList.get(i).floatValue()); //start always from x=1 for the first bar
                        entries.add(barEntry);
                    }

                    //initialize x Axis Labels (labels for 13 vertical grid lines)
                    final ArrayList<String> xAxisLabel = new ArrayList<>();
                    xAxisLabel.add("J"); //this label will be mapped to the 1st index of the valuesList
                    xAxisLabel.add("F");
                    xAxisLabel.add("M");
                    xAxisLabel.add("A");
                    xAxisLabel.add("M");
                    xAxisLabel.add("J");
                    xAxisLabel.add("J");
                    xAxisLabel.add("A");
                    xAxisLabel.add("S");
                    xAxisLabel.add("O");
                    xAxisLabel.add("N");
                    xAxisLabel.add("D");
                    xAxisLabel.add(""); //empty label for the last vertical grid line on Y-Right Axis

                    //initialize xAxis
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.enableGridDashedLine(10f, 10f, 0f);
                    xAxis.setTextColor(Color.BLACK);
                    xAxis.setTextSize(14);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setAxisLineColor(Color.BLACK);
                    xAxis.setDrawGridLines(true);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMinimum(0 + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
                    xAxis.setAxisMaximum(entries.size() + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
                    xAxis.setLabelCount(xAxisLabel.size(), true); //draw x labels for 13 vertical grid lines
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setXOffset(0f); //labels x offset in dps
                    xAxis.setYOffset(0f); //labels y offset in dps
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return xAxisLabel.get((int) value);
                        }
                    });

                    //initialize Y-Right-Axis
                    YAxis rightAxis = barChart.getAxisRight();
                    rightAxis.setTextColor(Color.BLACK);
                    rightAxis.setTextSize(14);
                    rightAxis.setDrawAxisLine(true);
                    rightAxis.setAxisLineColor(Color.BLACK);
                    rightAxis.setDrawGridLines(true);
                    rightAxis.setGranularity(1f);
                    rightAxis.setGranularityEnabled(true);
                    rightAxis.setAxisMinimum(0);
                    rightAxis.setAxisMaximum(Float.parseFloat(max.toString())+5);
                    rightAxis.setLabelCount(5, true); //draw y labels (Y-Values) for 4 horizontal grid lines starting from 0 to 6000f (step=2000)
                    rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

                    //initialize Y-Left-Axis
                    YAxis leftAxis = barChart.getAxisLeft();
                    leftAxis.setAxisMinimum(0);
                    leftAxis.setDrawAxisLine(true);
                    leftAxis.setLabelCount(0, true);
                    leftAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return "";
                        }
                    });

                    //set the BarDataSet
                    BarDataSet barDataSet = new BarDataSet(entries, "Months");
                    barDataSet.setColor(Color.RED);
                    barDataSet.setFormSize(15f);

                    barDataSet.setDrawValues(true);
                    barDataSet.setValueTextSize(12f);

                    //set the BarData to chart
                    BarData data = new BarData(barDataSet);
                    barChart.setData(data);
                    barChart.setScaleEnabled(false);
                    barChart.getLegend().setEnabled(false);
                    barChart.setDrawBarShadow(false);
                    barChart.getDescription().setEnabled(false);
                    barChart.setPinchZoom(false);
                    barChart.setDrawGridBackground(false);
                    barChart.invalidate();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query recupRevenu=mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("revenus");
        recupRevenu.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                ArrayList<Float> listMontant = new ArrayList<>();




                for(DataSnapshot postSnaphot : snapshot.getChildren()) {
                    String description = postSnaphot.child("description").getValue().toString();
                    String montant = postSnaphot.child("montant").getValue().toString();
                    String categorie = postSnaphot.child("catagorie").getValue().toString();
                    String date = postSnaphot.child("date").getValue().toString();


                    int month = Integer.parseInt(date.substring(3, 5));
                    Float year = Float.parseFloat(date.substring(6, 10));
                    Integer day = Integer.parseInt(date.substring(0, 2));


                    switch(month){

                        case 1:
                            revenuJanvier.add(Float.parseFloat(montant));
                            break;

                        case 2:
                            revenuFévrier.add(Float.parseFloat(montant));
                            break;
                        case 3:
                            revenuMars.add(Float.parseFloat(montant));
                            break;
                        case 4:
                            revenuAvril.add(Float.parseFloat(montant));
                            break;
                        case 5:
                            revenuMai.add(Float.parseFloat(montant));
                            break;
                        case 6:
                            revenuJuin.add(Float.parseFloat(montant));
                            break;
                        case 7:
                            revenuJuillet.add(Float.parseFloat(montant));
                            break;
                        case 8:
                            revenuAout.add(Float.parseFloat(montant));
                            break;
                        case 9:
                            revenuSeptembre.add(Float.parseFloat(montant));
                            break;
                        case 10:
                            revenuOctobre.add(Float.parseFloat(montant));
                            break;
                        case 11:
                            revenuNovembre.add(Float.parseFloat(montant));
                            break;
                        case 12:
                            revenuDecembre.add(Float.parseFloat(montant));
                            break;
                    }

                    //input Y data (Months Data - 12 Values)
                    ArrayList<Double> valuesList = new ArrayList<Double>();
                    double sumJanvier = revenuJanvier.stream().mapToDouble(i -> i).sum();
                    double sumFevrier = revenuFévrier.stream().mapToDouble(i -> i).sum();
                    double sumMars = revenuMars.stream().mapToDouble(i -> i).sum();
                    double sumAvril = revenuAvril.stream().mapToDouble(i -> i).sum();
                    double sumMai = revenuMai.stream().mapToDouble(i -> i).sum();
                    double sumJuin = revenuJuin.stream().mapToDouble(i -> i).sum();
                    double sumJuillet = revenuJuillet.stream().mapToDouble(i -> i).sum();
                    double sumAout = revenuAout.stream().mapToDouble(i -> i).sum();
                    double sumSeptembre = revenuSeptembre.stream().mapToDouble(i -> i).sum();
                    double sumOctobre = revenuOctobre.stream().mapToDouble(i -> i).sum();
                    double sumNovembre = revenuNovembre.stream().mapToDouble(i -> i).sum();
                    double sumDecembre = revenuDecembre.stream().mapToDouble(i -> i).sum();


                    valuesList.add((double) sumJanvier);
                    valuesList.add((double) sumFevrier);
                    valuesList.add((double) sumMars);
                    valuesList.add((double) sumAvril);
                    valuesList.add((double) sumMai);
                    valuesList.add((double) sumJuin);
                    valuesList.add((double) sumJuillet);
                    valuesList.add((double) sumAout);
                    valuesList.add((double) sumSeptembre);
                    valuesList.add((double) sumOctobre);
                    valuesList.add((double) sumNovembre);
                    valuesList.add((double) sumDecembre);

                    Double max =  Double.MIN_VALUE;

                    for(Double i : valuesList){
                        if(max<i){
                            max=i;
                        }
                    }

                    //prepare Bar Entries
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    for (int i = 0; i < valuesList.size(); i++) {
                        BarEntry barEntry = new BarEntry(i + 1, valuesList.get(i).floatValue()); //start always from x=1 for the first bar
                        entries.add(barEntry);
                    }

                    //initialize x Axis Labels (labels for 13 vertical grid lines)
                    final ArrayList<String> xAxisLabel = new ArrayList<>();
                    xAxisLabel.add("J"); //this label will be mapped to the 1st index of the valuesList
                    xAxisLabel.add("F");
                    xAxisLabel.add("M");
                    xAxisLabel.add("A");
                    xAxisLabel.add("M");
                    xAxisLabel.add("J");
                    xAxisLabel.add("J");
                    xAxisLabel.add("A");
                    xAxisLabel.add("S");
                    xAxisLabel.add("O");
                    xAxisLabel.add("N");
                    xAxisLabel.add("D");
                    xAxisLabel.add(""); //empty label for the last vertical grid line on Y-Right Axis

                    //initialize xAxis
                    XAxis xAxis = barChartRevenus.getXAxis();
                    xAxis.enableGridDashedLine(10f, 10f, 0f);
                    xAxis.setTextColor(Color.BLACK);
                    xAxis.setTextSize(14);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setAxisLineColor(Color.BLACK);
                    xAxis.setDrawGridLines(true);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMinimum(0 + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
                    xAxis.setAxisMaximum(entries.size() + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
                    xAxis.setLabelCount(xAxisLabel.size(), true); //draw x labels for 13 vertical grid lines
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setXOffset(0f); //labels x offset in dps
                    xAxis.setYOffset(0f); //labels y offset in dps
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return xAxisLabel.get((int) value);
                        }
                    });

                    //initialize Y-Right-Axis
                    YAxis rightAxis = barChartRevenus.getAxisRight();
                    rightAxis.setTextColor(Color.BLACK);
                    rightAxis.setTextSize(14);
                    rightAxis.setDrawAxisLine(true);
                    rightAxis.setAxisLineColor(Color.BLACK);
                    rightAxis.setDrawGridLines(true);
                    rightAxis.setGranularity(1f);
                    rightAxis.setGranularityEnabled(true);
                    rightAxis.setAxisMinimum(0);
                    rightAxis.setAxisMaximum(Float.parseFloat(max.toString())+5);
                    rightAxis.setLabelCount(5, true); //draw y labels (Y-Values) for 4 horizontal grid lines starting from 0 to 6000f (step=2000)
                    rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

                    //initialize Y-Left-Axis
                    YAxis leftAxis = barChartRevenus.getAxisLeft();
                    leftAxis.setAxisMinimum(0);
                    leftAxis.setDrawAxisLine(true);
                    leftAxis.setLabelCount(0, true);
                    leftAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return "";
                        }
                    });

                    //set the BarDataSet
                    BarDataSet barDataSet = new BarDataSet(entries, "Months");
                    barDataSet.setColor(Color.GREEN);
                    barDataSet.setFormSize(15f);
                    barDataSet.setDrawValues(true);
                    barDataSet.setValueTextSize(12f);

                    //set the BarData to chart
                    BarData data = new BarData(barDataSet);
                    barChartRevenus.setData(data);
                    barChartRevenus.setScaleEnabled(false);
                    barChartRevenus.getLegend().setEnabled(false);
                    barChartRevenus.setDrawBarShadow(false);
                    barChartRevenus.getDescription().setEnabled(false);
                    barChartRevenus.setPinchZoom(false);
                    barChartRevenus.setDrawGridBackground(false);
                    barChartRevenus.invalidate();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }



}