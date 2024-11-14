package User_Restaurant;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.apprestaurant.Accout_detail_userFragment;
import com.example.apprestaurant.R;

import fragment_ngv.HomeFragment;

public class ViewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maincontain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Đặt Fragment mặc định khi Activity được tạo
        replaceFragment(new Accout_detail_userFragment());

    }
    // Phương thức này sẽ thay thế fragment hiện tại bằng fragment mới
    private void replaceFragment(Fragment fragment) {
        // Thực hiện transaction để thay thế fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ngv_viewPager, fragment) // Đảm bảo fragment_container tồn tại trong layout
                    .commit();
        }
    }
}