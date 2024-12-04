package Accout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPageAdapter extends FragmentStateAdapter {

    public MyViewPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==1)
        {
            return new placed_orderdeatail_Fragment();
        }
        else   if(position==2)
        {
            return new canceled_orderdetail_Fragment();
        }
        else   if(position==3)
        {
            return new placedsuccessfully_orderdeatil_Fragment();
        }
            return new placed_orderdeatail_Fragment();

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
