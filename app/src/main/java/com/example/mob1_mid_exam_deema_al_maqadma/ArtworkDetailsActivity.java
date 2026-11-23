package com.example.mob1_mid_exam_deema_al_maqadma;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtworkDetailsActivity extends AppCompatActivity {

    private String targetUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);

        ImageView ivDetail = findViewById(R.id.ivDetailImage);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvArtist = findViewById(R.id.tvDetailArtist);
        Button btnVisit = findViewById(R.id.btnVisitWebsite);
        Button btnBack = findViewById(R.id.btnBack);

        // 1. استقبال البيانات القادمة من الشاشة الأولى
        Intent incoming = getIntent();
        String title = incoming.getStringExtra("ART_TITLE");
        String artist = incoming.getStringExtra("ART_ARTIST");
        String year = incoming.getStringExtra("ART_YEAR");
        int imageRes = incoming.getIntExtra("ART_IMAGE_RES", 0);
        targetUrl = incoming.getStringExtra("ART_URL");

        // 2. عرض البيانات
        tvTitle.setText(title);
        tvArtist.setText(getString(R.string.artist_year_format, artist, year));
        if (imageRes != 0) {
            ivDetail.setImageResource(imageRes);
        }

        // 3. النية الضمنية (Implicit Intent): فتح المتصفح
        btnVisit.setOnClickListener(v -> {
            if (targetUrl != null && !targetUrl.isEmpty()) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl));
                startActivity(webIntent);
            }
        });

        // 4. زر الرجوع
        btnBack.setOnClickListener(v -> finish());
    }
}