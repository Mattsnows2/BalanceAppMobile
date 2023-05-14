package my.matt.myApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {


    private final ArrayList<String> dayOfMonth;
    private final OnItemListener  onItemListener;
    private final ArrayList<String > montantOfDay;
    public CalendarAdapter(ArrayList<String> dayOfMonth, ArrayList<String> montantOfDay, OnItemListener onItemListeneier) {
        this.dayOfMonth = dayOfMonth;
        this.onItemListener = onItemListeneier;
        this.montantOfDay=montantOfDay;

    }

    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1666666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(dayOfMonth.get(position));
        holder.montantOfDay.setText(montantOfDay.get(position));


    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }

    public interface  OnItemListener{
        void onItemClick(int position, String dayText);
    }
}
