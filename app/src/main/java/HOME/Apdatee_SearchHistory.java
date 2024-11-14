package HOME;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class Apdatee_SearchHistory extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> list;
    private int resource;

    public Apdatee_SearchHistory(@NonNull Context context, int resource1, ArrayList<String> list) {
        super(context, resource1);
        this.context = context;
        this.list = list;
        this.resource = resource1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        TextView tvlstimkiem = convertView.findViewById(R.id.textllichsutimkiem);
        tvlstimkiem.setText(list.get(position));

        return convertView;
    }
}
