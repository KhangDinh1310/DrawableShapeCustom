package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    TextView regisText;
    EditText textRegisUser, textRegisEmail, textRegisPass, textRegisCfPass;
    Button regisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regisText = findViewById(R.id.register);
        regisText.setPaintFlags(regisText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        regisText.setText(R.string.haveAccount);

        textRegisUser = findViewById(R.id.text_register_user);
        textRegisEmail = findViewById(R.id.text_email);
        textRegisPass = findViewById(R.id.text_register_password);
        regisButton = findViewById(R.id.regis_button);
        textRegisCfPass = findViewById(R.id.text_confirm);

        regisText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                Toast.makeText(RegisterActivity.this, "Please log in here!", Toast.LENGTH_SHORT).show();
            }
        });
        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = textRegisUser.getText().toString();
                String email = textRegisEmail.getText().toString();
                String password = textRegisPass.getText().toString();
                String cf_password = textRegisCfPass.getText().toString();
                Database db = new Database(RegisterActivity.this, "Healthcare", null, 1);

                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || cf_password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill all information!", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(cf_password) == 0) {
                        if (isValid(password)) {
                            db.register(username, email, password);
                            Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Password must contain at least 8 characters, at least 1 capital or 1 special symbol and 1 digit ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public static boolean isValid(String checkPass) {
        int F1 = 0, F2 = 0, F3 = 0;
        if (checkPass.length() < 8) {
            return false;
        } else {
            for (int i = 0; i <checkPass.length(); i++) {
                if(Character.isLetter(checkPass.charAt(i))) {
                    F1 = 1;
                }
            }
            for(int k = 0; k <checkPass.length(); k++) {
                if(Character.isDigit(checkPass.charAt(k))) {
                    F2 = 1;
                }
            }
            for (int h = 0; h < checkPass.length(); h++) {
                char c = checkPass.charAt(h);
                if ((c >= 33 && c <= 46) || c == 64) {
                    F3 = 1;
                }
            }
            if (F1 == 1 && F2 == 1 && F3 == 1)
                return true;
            return false;
        }
    }
}