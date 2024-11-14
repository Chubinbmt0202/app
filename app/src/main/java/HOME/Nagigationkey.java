package HOME;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.apprestaurant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import fragment_ngv.AccountFragment;
import fragment_ngv.BookFragment;
import fragment_ngv.FavouriteFragment;
import fragment_ngv.HomeFragment;
import fragment_ngv.OrderFragment;

public class Nagigationkey extends AppCompatActivity {
    private BottomNavigationView ngv_bottomngvview;
    private FrameLayout flt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagigationkey);

        ngv_bottomngvview = findViewById(R.id.ngv_bottomNavigation);
        flt = findViewById(R.id.ngv_viewPager);

        // Đặt Fragment mặc định khi Activity được tạo
        replaceFragment(new HomeFragment());

        ngv_bottomngvview.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Sử dụng if-else để xác định fragment được chọn
            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.order) {
                selectedFragment = new OrderFragment();
            } else if (item.getItemId() == R.id.book) {
                selectedFragment = new BookFragment();
            } else if (item.getItemId() == R.id.favourite) {
                selectedFragment = new FavouriteFragment();
            } else if (item.getItemId() == R.id.account) {
                selectedFragment = new AccountFragment();
            }

            // Thay thế fragment nếu selectedFragment không null
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        // Lắng nghe thay đổi bố cục của FrameLayout
        flt.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // Cập nhật nút dưới cùng khi FrameLayout thay đổi

                ngv_bottomngvview.setVisibility(View.VISIBLE); // Hoặc bất kỳ hành động nào bạn muốn thực hiện
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Phương thức này sẽ thay thế fragment hiện tại bằng fragment mới
    private void replaceFragment(Fragment fragment) {
        // Thực hiện transaction để thay thế fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ngv_viewPager, fragment) // Đảm bảo fragment_container tồn tại trong layout
                    .commit();
            ngv_bottomngvview.setVisibility(View.VISIBLE);
        }
    }
}
