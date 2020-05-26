package co.edu.unab.hernandez.yeison.your_health;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 8000; // 3 segundos
    private VideoView videoSplash;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String url = "https://proyectosyjpro.000webhostapp.com/animation.mp4"; // your URL here
        videoSplash = (VideoView)findViewById(R.id.videoView);
        videoSplash.setVideoPath(url);
        videoSplash.start();


        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
}
