package Intro_register_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apprestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import HOME.Nagigationkey;

public class LoginActivity extends AppCompatActivity {
    private TextView txtdki;
    private Button btn_login;
    private CheckBox cbghinho;
    private SharedPreferences share;
    private int check;
    private DatabaseReference mDatabase;
    private EditText edtsdt, edtpass;
    int ss = 0;
    String  kk ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        cbghinho = findViewById(R.id.cbghinho);
        txtdki = findViewById(R.id.txt_dangky);
        edtsdt = findViewById(R.id.edtsearch1);
        edtpass = findViewById(R.id.pass);
        btn_login = findViewById(R.id.btn_dangnhap); // Khởi tạo btn_login


        share = getSharedPreferences("mysave", MODE_PRIVATE);
        check = share.getInt("cb", 0);
        if (check == 1) {
            cbghinho.setChecked(true);
            edtsdt.setText(share.getString("tk", ""));
            edtpass.setText(share.getString("mk", ""));
        }


        txtdki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
                finish();
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtsdt.getText().toString().isEmpty() || edtpass.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG).show();
                }
                else
                {

                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean userFound = false;

                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String phoneNumber = userSnapshot.child("SDT").getValue(String.class);
                                String password = userSnapshot.child("Mkhau").getValue(String.class);

                                // Check if phone number matches
                                if (phoneNumber != null && phoneNumber.equals(edtsdt.getText().toString())) {
                                    userFound = true;

                                    // Check if password matches
                                    if (password != null && password.equals(edtpass.getText().toString())) {
                                        SharedPreferences.Editor editor = share.edit();
                                        editor.putString("tk", edtsdt.getText().toString());
                                        editor.putString("mk", edtpass.getText().toString());
                                        editor.putInt("cb", 1);
                                        editor.apply();

                                        Intent it = new Intent(LoginActivity.this, Nagigationkey.class);
                                        it.putExtra("idus", userSnapshot.getKey());
                                        startActivity(it);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                    break; // Stop after finding the user
                                }
                            }

                            if (!userFound) {
                                Toast.makeText(LoginActivity.this, "Sai số điện thoại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Lỗi kết nối dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
        });
    }
}
