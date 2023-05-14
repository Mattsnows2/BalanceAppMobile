package my.matt.myApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.matt.myApp.models.Depense;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Depense> depenses;

    public MyAdapter(Context context, List<Depense> depenses){
        this.context= context;
        this.depenses = depenses;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.depense_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textDescription.setText(depenses.get(position).getDescription());
        holder.textMontant.setText(depenses.get(position).getMontant());
        holder.textCategorie.setText(depenses.get(position).getCategorie());
        holder.textDate.setText(depenses.get(position).getDate());

        if(depenses.get(position).getCategorie().equals(("Nourriture"))) {
            holder.imageMontantView2.setImageResource(R.drawable.food);
        }else if(depenses.get(position).getCategorie().equals("Education")){
            holder.imageMontantView2.setImageResource(R.drawable.education);
        }else if(depenses.get(position).getCategorie().equals("transport")){
            holder.imageMontantView2.setImageResource(R.drawable.transport);
        }else if(depenses.get(position).getCategorie().equals("Charges")){
            holder.imageMontantView2.setImageResource(R.drawable.charges);
        }else if(depenses.get(position).getCategorie().equals("Factures")){
            holder.imageMontantView2.setImageResource(R.drawable.bill);
        }else if(depenses.get(position).getCategorie().equals("autres")){
            holder.imageMontantView2.setImageResource(R.drawable.other);
        }


    }

    @Override
    public int getItemCount() {
        return depenses.size();
    }
}
