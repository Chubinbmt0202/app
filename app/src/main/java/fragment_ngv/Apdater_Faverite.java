package fragment_ngv;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class Apdater_Faverite extends RecyclerView.Adapter<Apdater_Faverite.ViewHolder>{

    private Context context;
    private ArrayList<Yeuthich> list;

    public Apdater_Faverite(Context context, ArrayList<Yeuthich> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Apdater_Faverite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favourite, parent, false);
        return new Apdater_Faverite.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Apdater_Faverite.ViewHolder holder, int position) {
         Yeuthich yt = list.get(position);

         holder.imganh.setImageResource(yt.getImganh());
         holder.tvyeuthich.setText(yt.getTenmonan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imganh , iconyeuthich;
        private TextView tvyeuthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.imganhyeuthich);
            tvyeuthich = itemView.findViewById(R.id.tvtenyeuthich);
            iconyeuthich = itemView.findViewById(R.id.iconyeuthich);

            iconyeuthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iconyeuthich.setImageResource(R.drawable.btn_2);
                }
            });
        }
    }
}
