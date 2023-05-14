package my.matt.myApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.matt.myApp.models.Compte;

public class MyAdapterCompte extends RecyclerView.Adapter<MyViewHolderCompte> {

    Context context;
    List<Compte> compte;

    public MyAdapterCompte(Context context, List<Compte> compte) {
        this.context = context;
        this.compte = compte;
    }

    @NonNull
    @Override
    public MyViewHolderCompte onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderCompte((LayoutInflater.from(context).inflate(R.layout.compte_view, parent, false)));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCompte holder, int position) {
        holder.textCompte.setText(compte.get(position).getCapital());
    }

    @Override
    public int getItemCount() {
        return compte.size();
    }
}
