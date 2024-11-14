package Intro_register_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apprestaurant.R;

import User_Restaurant.DetailUser_Activity;

public class RegisterActivity extends AppCompatActivity {
    private TextView txtdangnhap ;
    private EditText edtname , edtphone , edtemail , pass1 , pass2 ;
    private Button btndangki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        edtname =  findViewById(R.id.edt_dki_name);
        edtphone =  findViewById(R.id.edt_dki_phone);
        edtemail = findViewById(R.id.editTextTextEmailAddress);
        pass1 = findViewById(R.id.Pass_dki_mk);
        pass2 = findViewById(R.id.Pass_dki_mk2);

        ToLogin();
        ToDangKy();

    }
    private void ToLogin()
    {
        txtdangnhap = (TextView) findViewById(R.id.txt_login);

        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
    }



    private void ToDangKy()
    {
        btndangki = (Button) findViewById(R.id.btn_dangki);

        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtname == null || edtphone == null || edtemail == null || pass1 == null || pass2 == null) {
                    Toast.makeText(RegisterActivity.this, "Một trong các trường không được khởi tạo.", Toast.LENGTH_LONG).show();
                    return;
                }
                // Kiểm tra thông tin nhập vào
                if (edtname.getText().toString().trim().isEmpty() ||
                        edtphone.getText().toString().trim().isEmpty() ||
                        edtemail.getText().toString().trim().isEmpty() ||
                        pass1.getText().toString().trim().isEmpty() ||
                        pass2.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG).show();
                }
                else if (!pass1.getText().toString().trim().equals(pass2.getText().toString().trim()))
                {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng nhau.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent it = new Intent(RegisterActivity.this, DetailUser_Activity.class);
                    it.putExtra("name", edtname.getText().toString().trim());
                    it.putExtra("phone", edtphone.getText().toString().trim());
                    it.putExtra("email", edtemail.getText().toString().trim());
                    startActivity(it);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công.", Toast.LENGTH_LONG).show();

                    Intent it1 = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(it1);
                }
            }



        });

    }

}