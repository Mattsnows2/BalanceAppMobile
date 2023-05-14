package my.matt.myApp;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

public class MyViewHolderRevenu extends RecyclerView.ViewHolder {

    ImageView imageRevenuView;
    TextView textDescription, textMontant, textDate, textCategorie;
    public SwipeRevealLayout swipeRevealLayout;
    public LinearLayout layoutDelete;
    public ImageView buttonEdit, buttonDelete;
    public MyViewHolderRevenu(@NonNull View itemView) {
        super(itemView);


        textDescription=itemView.findViewById(R.id.textDescriptionRevenuView);
        textMontant=itemView.findViewById(R.id.textMontantRevenuView);
        textDate=itemView.findViewById(R.id.textDate);
        imageRevenuView=itemView.findViewById(R.id.imageMontantView);
        layoutDelete=itemView.findViewById(R.id.layout_delete);
        swipeRevealLayout=itemView.findViewById(R.id.swipeRevealLayout);
        buttonDelete=itemView.findViewById(R.id.buttonDelete);
        buttonEdit=itemView.findViewById(R.id.buttonEdit);

        textCategorie=itemView.findViewById(R.id.textCategorieRevenu);







    }
}
