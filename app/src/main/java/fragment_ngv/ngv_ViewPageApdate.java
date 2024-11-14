package fragment_ngv;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import Order.Order_CategoryBanhCuonFragment;

public class ngv_ViewPageApdate extends FragmentStatePagerAdapter {
    public ngv_ViewPageApdate(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new HomeFragment();

            case 1 : return new OrderFragment();

            case 2 : return new BookFragment();

            case 3 : return new FavouriteFragment();

            case 4 : return new AccountFragment();

            default:   return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
