package com.gzeinnumer.oneiday7example.base;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.oneiday7example.DashboardActivity;
import com.gzeinnumer.oneiday7example.data.SessionManagerUtil;

import java.util.Date;

public class BaseActivity extends AppCompatActivity {

     public void onShowToast(String msg){
         Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
     }

    protected void checkSession() {
        if (SessionManagerUtil.getInstance().isSessionActive(getApplicationContext(), new Date())) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }
    }
}
