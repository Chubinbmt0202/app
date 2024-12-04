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

    private final Context context;
    private final ArrayList<String> list;
    private final int resource;

    public Apdatee_SearchHistory(@NonNull Context context, int resource1, ArrayList<String> list) {
        super(context, resource1, list);
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.resource = resource1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            // Tạo ViewHolder và lưu các view con
            holder = new ViewHolder();
            holder.tvSearchItem = convertView.findViewById(R.id.textllichsutimkiem);

            convertView.setTag(holder);
        } else {
            // Lấy ViewHolder từ tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Gán dữ liệu vào view
        String item = list.get(position);
        holder.tvSearchItem.setText(item);

        // Xử lý sự kiện khi click vào mục lịch sử
        convertView.setOnClickListener(view ->
                Toast.makeText(context, "Clicked: " + item, Toast.LENGTH_SHORT).show()
        );

        return convertView;
    }

    // Lớp ViewHolder để tối ưu tìm view
    static class ViewHolder {
        TextView tvSearchItem;
    }
}
