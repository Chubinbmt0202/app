package HOME;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.apprestaurant.R;

import java.util.List;

public class PhotoApdater extends PagerAdapter {

    private Context context;
    private List<ImageQuangCao> list;

    public PhotoApdater(Context context, List<ImageQuangCao> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.photoimg_layout,container,false);

        ImageView imgphoto = view.findViewById(R.id.photoimg_home);

        ImageQuangCao qc = list.get(position);

        if(qc != null)
        {
            imgphoto.setImageResource(qc.getImganh());
        }

        // Them View vao Viewgroup
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(list != null)
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
