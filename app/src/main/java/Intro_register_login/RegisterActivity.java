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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView txtdangnhap ;
    private EditText edtname , edtphone , edtemail , pass1 , pass2 ;
    private Button btndangki;
    private DatabaseReference mDatabase;
    private  int thamso = 0;
    int ktbt ;
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
        ktbt = 0;
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



    private void ToDangKy() {
        btndangki = findViewById(R.id.btn_dangki);

        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra null cho các trường nhập liệu
                if (edtname == null || edtphone == null || edtemail == null || pass1 == null || pass2 == null) {
                    Toast.makeText(RegisterActivity.this, "Một trong các trường không được khởi tạo.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Kiểm tra dữ liệu nhập
                if (edtname.getText().toString().trim().isEmpty() ||
                        edtphone.getText().toString().trim().isEmpty() ||
                        edtemail.getText().toString().trim().isEmpty() ||
                        pass1.getText().toString().trim().isEmpty() ||
                        pass2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!pass1.getText().toString().trim().equals(pass2.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng nhau.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Khởi tạo tham chiếu Firebase Realtime Database
                mDatabase = FirebaseDatabase.getInstance().getReference("User");
                // Lấy số lượng user hiện tại từ Realtime Database
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                        thamso = snapshot.exists() ? (int) snapshot.getChildrenCount() +1 : 0;
                        // Lưu dữ liệu vào Firestore
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        // Tạo dữ liệu người dùng mới
                        Map<String, Object> newUser = new HashMap<>();
                        newUser.put("name", edtname.getText().toString().trim());
                        newUser.put("SDT", edtphone.getText().toString().trim());
                        newUser.put("Email", edtemail.getText().toString().trim());
                        newUser.put("GioiTinh", 1); // Mặc định giới tính
                        newUser.put("Mkhau", pass1.getText().toString().trim());

                        database.child("User").child(thamso + "").setValue(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Đăng ký không thành công.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công.", Toast.LENGTH_LONG).show();
                        Intent it1 = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(it1);
                        finish();
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                        Log.e("FirebaseError", "Lỗi truy cập Firebase Database", error.toException());
                        Toast.makeText(RegisterActivity.this, "Lỗi khi truy cập dữ liệu.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


}