package com.example.profile.nieyuhang;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    SharedPreferences prefs;
    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        prefs = getSharedPreferences("admin_prefs", MODE_PRIVATE);
        if (!prefs.contains("admin_password")) prefs.edit().putString("admin_password", "2406033346").apply();
        EditText etUser = findViewById(R.id.etUser);
        EditText etPass = findViewById(R.id.etPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnReg = findViewById(R.id.btnReg);
        btnReg.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        btnLogin.setOnClickListener(v -> {
            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();
            if (u.isEmpty()||p.isEmpty()) { Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show(); return; }
            String adminPass = prefs.getString("admin_password","2406033346");
            if ("admin".equals(u) && adminPass.equals(p)) {
                startActivity(new Intent(this, AdminActivity.class));
                return;
            }
            if (db.checkUser(u,p)) {
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra("username", u);
                startActivity(i);
            } else Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        });
    }
}
