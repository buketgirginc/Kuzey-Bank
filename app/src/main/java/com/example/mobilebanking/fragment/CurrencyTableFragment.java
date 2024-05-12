package com.example.mobilebanking.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilebanking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrencyTableFragment extends Fragment {

    private Handler handler = new Handler(Looper.getMainLooper());
    private ScheduledExecutorService executorService;
    private TextView usdAlis;
    private TextView usdSatis;

    private TextView euroAlis;
    private TextView euroSatis;
    private TextView bilgiYazisi;
    private CountDownTimer countDownTimer;
    private long timerMillis = 15000;

    public CurrencyTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // JSON here
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::fetchDataAndUpdateUI, 0, 15, TimeUnit.SECONDS);


        View view = inflater.inflate(R.layout.fragment_currency_table, container, false);
        this.bilgiYazisi = view.findViewById(R.id.bilgiYazisi);
        this.usdAlis = view.findViewById(R.id.dolarAlis);
        this.usdSatis = view.findViewById(R.id.dolarSatis);
        this.euroAlis = view.findViewById(R.id.euroAlis);
        this.euroSatis = view.findViewById(R.id.euroSatis);

        // Her saniye geri sayımı başlat
        countDownTimer = new CountDownTimer(timerMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerMillis = 15000; // Geri sayım bittiğinde başlangıç süresini yeniden ayarla
                startCountdownTimer(); // Geri sayımı başlat
            }
        };
        startCountdownTimer();
        fetchDataAndUpdateUI();
        return view;
    }

    private void startCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer.start();
    }

    private void updateTimerText() {
        long secondsRemaining = timerMillis / 1000;
        long minutes = secondsRemaining / 60;
        long seconds = secondsRemaining % 60;
        String timerText = String.format("%d", seconds);
        bilgiYazisi.setText(timerText + " saniye sonra güncellenecek...");
    }

    private void fetchDataAndUpdateUI() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.genelpara.com/embed/doviz.json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);

            // JSON'dan gerekli verileri al
            JSONObject usdObject = jsonObject.getJSONObject("USD");
            String usdSatis = usdObject.getString("satis");
            String usdAlis = usdObject.getString("alis");

            JSONObject eurObject = jsonObject.getJSONObject("EUR");
            String eurSatis = eurObject.getString("satis");
            String eurAlis = eurObject.getString("alis");

            // UI'yi güncelle
            handler.post(() -> {
                this.usdSatis.setText(usdSatis);
                this.usdAlis.setText(usdAlis);
                this.euroAlis.setText(eurAlis);
                this.euroSatis.setText(eurSatis);
                this.usdSatis.setBackgroundColor(Color.argb(30, 52, 196, 0));
                this.usdAlis.setBackgroundColor(Color.argb(30, 52, 196, 0));
                this.euroAlis.setBackgroundColor(Color.argb(30, 52, 196, 0));
                this.euroSatis.setBackgroundColor(Color.argb(30, 52, 196, 0));
                new Handler().postDelayed(() -> {
                    this.usdSatis.setBackgroundColor(Color.TRANSPARENT);
                    this.usdAlis.setBackgroundColor(Color.TRANSPARENT);
                    this.euroAlis.setBackgroundColor(Color.TRANSPARENT);
                    this.euroSatis.setBackgroundColor(Color.TRANSPARENT);
                }, 2000);
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Uygulama kapanırken zamanlayıcıları durdur
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}