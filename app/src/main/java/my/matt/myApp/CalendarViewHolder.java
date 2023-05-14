package my.matt.myApp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class CalendarViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    public final TextView dayOfMonth;
    public final TextView montantOfDay;
    private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth= itemView.findViewById(R.id.cellDayText);
        montantOfDay = itemView.findViewById(R.id.cellMontantImage);

        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAbsoluteAdapterPosition(), (String) dayOfMonth.getText() ) ;
    }
}
