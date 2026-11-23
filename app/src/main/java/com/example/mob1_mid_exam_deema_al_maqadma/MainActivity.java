package com.example.mob1_mid_exam_deema_al_maqadma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvCounter, tvArtworkTitle, tvArtistInfo;
    private ImageView ivArtwork;
    private Button btnPrevious, btnNext;

    private ArrayList<Artwork> artworkList;
    private int currentIndex = 0;

    private static final String KEY_CURRENT_INDEX = "KEY_CURRENT_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ربط العناصر
        tvCounter = findViewById(R.id.tvCounter);
        ivArtwork = findViewById(R.id.ivArtwork);
        tvArtworkTitle = findViewById(R.id.tvArtworkTitle);
        tvArtistInfo = findViewById(R.id.tvArtistInfo);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        // تعبئة البيانات
        loadArtworksData();

        // استعادة الحالة عند دوران الشاشة
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        updateArtworkUI();

        // زر التالي
        btnNext.setOnClickListener(v -> {
            if (currentIndex < artworkList.size() - 1) {
                currentIndex++;
                updateArtworkUI();
            }
        });

        // زر السابق
        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateArtworkUI();
            }
        });

        // النقر على الصورة للانتقال للشاشة الثانية (Explicit Intent)
        ivArtwork.setOnClickListener(v -> {
            Artwork current = artworkList.get(currentIndex);
            Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
            intent.putExtra("ART_TITLE", current.getTitle());
            intent.putExtra("ART_ARTIST", current.getArtist());
            intent.putExtra("ART_YEAR", current.getYear());
            intent.putExtra("ART_IMAGE_RES", current.getImageResId());
            intent.putExtra("ART_URL", current.getWebUrl());
            startActivity(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void loadArtworksData() {
        artworkList = new ArrayList<>();
        artworkList.add(new Artwork(R.drawable.art1, "Deep Field Stellar Deaths", "Smacs Carin", "2021", "https://webbtelescope.org/"));
        artworkList.add(new Artwork(R.drawable.art2, "Cosmic Cliffs in Carina", "NASA Hubble Team", "2022", "https://www.nasa.gov/"));
        artworkList.add(new Artwork(R.drawable.art3, "Stephan's Quintet Galaxies", "Webb Space Observatory", "2022", "https://esawebb.org/"));
        artworkList.add(new Artwork(R.drawable.art4, "Pillars of Creation", "Hubble Space Telescope", "2015", "https://hubblesite.org/"));
        artworkList.add(new Artwork(R.drawable.art5, "Phantom Galaxy Spiral", "Judy Schmidt & ESA", "2023", "https://www.esa.int/"));
        artworkList.add(new Artwork(R.drawable.art6, "Hubble Ngc Flat Final", "Sonay Billan", "2019", "https://www.spacetelescope.org/"));
    }

    private void updateArtworkUI() {
        Artwork current = artworkList.get(currentIndex);

        tvCounter.setText(getString(R.string.counter_format, currentIndex + 1, artworkList.size()));
        ivArtwork.setImageResource(current.getImageResId());
        tvArtworkTitle.setText(current.getTitle());
        tvArtistInfo.setText(getString(R.string.artist_info_format, current.getArtist(), current.getYear()));

        // زر Previous معطل عند 1/6
        boolean hasPrev = (currentIndex > 0);
        btnPrevious.setEnabled(hasPrev);
        setButtonStyle(btnPrevious, hasPrev);

        // زر Next معطل عند 6/6
        boolean hasNext = (currentIndex < artworkList.size() - 1);
        btnNext.setEnabled(hasNext);
        setButtonStyle(btnNext, hasNext);
    }

    private void setButtonStyle(Button button, boolean isEnabled) {
        if (isEnabled) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8D4E24")));
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E0D7D0")));
            button.setTextColor(Color.parseColor("#9E9E9E"));
        }
    }
}