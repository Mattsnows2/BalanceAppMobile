package my.matt.myApp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import my.matt.myApp.models.Revenu;

import java.util.List;

public class MyAdapterRevenu extends RecyclerView.Adapter<MyViewHolderRevenu> {

    Context context;
    List<Revenu> revenus;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private DatabaseReference mDatabase;


    public MyAdapterRevenu(Context context, List<Revenu> revenus) {
        this.context = context;
        this.revenus = revenus;
    }

    @NonNull
    @Override
    public MyViewHolderRevenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderRevenu(LayoutInflater.from(context).inflate(R.layout.revenu_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderRevenu holder, int position) {

        holder.textDescription.setText(revenus.get(position).getDescription());
        holder.textMontant.setText(revenus.get(position).getMontant());
        holder.textDate.setText(revenus.get(position).getDate());
        holder.textCategorie.setText(revenus.get(position).getCatagorie());
        String montant = revenus.get(position).getMontant();
     //   Long id = revenus.get(position).getId();

        if(revenus.get(position).getCatagorie().equals("Salaire")){
            holder.imageRevenuView.setImageResource(R.drawable.salary);
        }else if(revenus.get(position).getCatagorie().equals("Interet")){
            holder.imageRevenuView.setImageResource(R.drawable.rate);
        }else if (revenus.get(position).getCatagorie().equals("autres")){
            holder.imageRevenuView.setImageResource(R.drawable.other);
        }

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(holder.imageRevenuView));
        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

                String id = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("revenus").push().getKey();
                Log.d("qf",id+"");
                mDatabase = FirebaseDatabase.getInstance("https://balance-6e66a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
                mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("compte").child("0").child("revenus").child(id).removeValue();


            }
        });



    }

    @Override
    public int getItemCount() {
        return revenus.size();
    }
}
