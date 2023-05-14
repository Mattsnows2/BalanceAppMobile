package my.matt.myApp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageMontantView2;
    TextView textDescription, textMontant, textDate, textCategorie;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textDescription=itemView.findViewById(R.id.textDescriptionDepenseView);
        textMontant=itemView.findViewById(R.id.textMontantDepenseView);
        textDate=itemView.findViewById(R.id.textDateDepense);
        imageMontantView2 = itemView.findViewById(R.id.imageMontantView2);

        textCategorie = itemView.findViewById(R.id.textCategorieDepense);


    }
}
