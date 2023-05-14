package my.matt.myApp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderCompte extends RecyclerView.ViewHolder {

    TextView textCompte;


    public MyViewHolderCompte(@NonNull View itemView) {
        super(itemView);

        textCompte=itemView.findViewById(R.id.textCompte);
    }
}
