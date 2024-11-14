package Intro_register_login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apprestaurant.R;

import HOME.Nagigationkey;

public class Intro_Activity extends AppCompatActivity {
   private TextView txtintro , txtlogin ;
    private Handler handler = new Handler();
    private Button btbhome;
    private VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        txtintro = (TextView) findViewById(R.id.txt_welcome);

        btbhome = (Button) findViewById(R.id.btn_home);

        showTextWithDelay(txtintro.getText().toString(), 200);

        txtlogin = (TextView) findViewById(R.id.txt_login);

        // Khai bao video view
        vv = (VideoView) findViewById(R.id.videoView);
        vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.quangcaonhahang));
        vv.setOnPreparedListener(mp -> mp.setLooping(true));  // Lặp lại video
        vv.start();

        txtlogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent it1 = new Intent(Intro_Activity.this,LoginActivity.class);
                 startActivity(it1);
                 finish();
         }});

         btbhome.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent it = new Intent(Intro_Activity.this, Nagigationkey.class);
                 startActivity(it);
                 finish();
             }
         });
    }
    private void showTextWithDelay(final String text, final long delay) {
        final int[] index = {0}; // Sử dụng mảng để giữ giá trị index có thể thay đổi
        txtintro.setText("");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (index[0] < text.length()) {
                    txtintro.append(String.valueOf(text.charAt(index[0])));
                    index[0]++;
                    handler.postDelayed(this, delay);
                }
            }
        };

        handler.post(runnable);
    }
}