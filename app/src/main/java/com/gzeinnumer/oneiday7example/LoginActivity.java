package com.gzeinnumer.oneiday7example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gzeinnumer.oneiday7example.base.BaseActivity;
import com.gzeinnumer.oneiday7example.data.RetroServer;
import com.gzeinnumer.oneiday7example.data.SessionManagerUtil;
import com.gzeinnumer.oneiday7example.databinding.ActivityLoginBinding;
import com.gzeinnumer.oneiday7example.model.LoginResponse;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    private String token;
    private String username;
    private String password;
    private Executor backgroundThread = Executors.newSingleThreadExecutor();
    private Executor mainThread = new Executor() {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edUsername.setText("demo@demo.com");
        binding.edPass.setText("demo123");

        initView();
        initOnclick();
    }

    private static final String TAG = "testsasa";

    private void initView() {
        checkSession();
    }

    private void initOnclick() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        username = binding.edUsername.getText().toString();
        password = binding.edPass.getText().toString();

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            onShowToast("Username dan password tidak boleh kosong!!");
            return;
        }

        RetroServer.getInstance().login(username, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                Log.d(TAG, "onResponse1: " + response.code());
                LoginResponse loginResponse = response.body();
                if (loginResponse.isStatus()) {
                    token = loginResponse.getToken();
                    login();
                } else {
                    Toast.makeText(getApplicationContext(), "Username dan password tidak cocok!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onResponse2: " + t.getMessage());
            }
        });
    }

    private void login() {
        binding.progressBar.setVisibility(View.VISIBLE);
        backgroundThread.execute(new Runnable() {
            @Override
            public void run() {
                // connect server
                SystemClock.sleep(3000);
                mainThread.execute(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        startAndStoreSession();
                        startMainActivity();
                    }
                });
            }
        });
    }

    private void startAndStoreSession() {
        SessionManagerUtil.getInstance()
                .storeUserToken(getApplicationContext(), generateToken(username, password));
        SessionManagerUtil.getInstance()
                .storeToken(getApplicationContext(), token);
        SessionManagerUtil.getInstance()
                .startUserSession(getApplicationContext(), 10);
    }

    private String generateToken(String username, String password) {
        String feeds = username + ":" + password;
        String token = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            token = Base64.getEncoder().encodeToString(feeds.getBytes());
        } else {
            token = feeds;
        }
        return token;
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}