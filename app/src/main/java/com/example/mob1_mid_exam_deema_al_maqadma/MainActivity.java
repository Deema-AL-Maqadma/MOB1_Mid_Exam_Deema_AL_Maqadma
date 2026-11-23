package com.example.artspace;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // عناصر الواجهة
    private TextView tvCounter, tvArtworkTitle, tvArtistInfo;
    private ImageView ivArtwork;
    private Button btnPrevious, btnNext;

    // قائمة اللوحات الفنية ومؤشر اللوحة الحالية
    private ArrayList<Artwork> artworkList;
    private int currentIndex = 0;

    // مفتاح الحفظ عند تدوير الشاشة
    private static final String KEY_CURRENT_INDEX = "KEY_CURRENT_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. ربط عناصر الواجهة
        tvCounter = findViewById(R.id.tvCounter);
        ivArtwork = findViewById(R.id.ivArtwork);
        tvArtworkTitle = findViewById(R.id.tvArtworkTitle);
        tvArtistInfo = findViewById(R.id.tvArtistInfo);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        // 2. تهيئة بيانات اللوحات الست (بيانات مطابقة للصور المرجعية)
        loadArtworksData();

        // 3. استرجاع الحالة عند تدوير الشاشة (Lifecycle & State Management)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        // عرض اللوحة الحالية وتحديث الأزرار
        updateArtworkUI();

        // 4. برمجة زر التالي (Next)
        btnNext.setOnClickListener(v -> {
            if (currentIndex < artworkList.size() - 1) {
                currentIndex++;
                updateArtworkUI();
            }
        });

        // 5. برمجة زر السابق (Previous)
        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateArtworkUI();
            }
        });

        // 6. برمجة النقر على الصورة للانتقال للشاشة الثانية (Explicit Intent)
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

    // حفظ مؤشر اللوحة الحالية قبل تدمير الشاشة عند التدوير
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    // تعبئة بيانات اللوحات الست
    private void loadArtworksData() {
        artworkList = new ArrayList<>();
        artworkList.add(new Artwork(R.drawable.art1, "Deep Field Stellar Deaths", "Smacs Carin", "2021", "https://webbtelescope.org/"));
        artworkList.add(new Artwork(R.drawable.art2, "Cosmic Cliffs in Carina", "NASA Hubble Team", "2022", "https://www.nasa.gov/"));
        artworkList.add(new Artwork(R.drawable.art3, "Stephan's Quintet Galaxies", "Webb Space Observatory", "2022", "https://esawebb.org/"));
        artworkList.add(new Artwork(R.drawable.art4, "Pillars of Creation", "Hubble Space Telescope", "2015", "https://hubblesite.org/"));
        artworkList.add(new Artwork(R.drawable.art5, "Phantom Galaxy Spiral", "Judy Schmidt & ESA", "2023", "https://www.esa.int/"));
        artworkList.add(new Artwork(R.drawable.art6, "Hubble Ngc Flat Final", "Sonay Billan", "2019", "https://www.spacetelescope.org/"));
    }

    // تحديث بيانات اللوحة وأزرار التحكم ديناميكياً
    @SuppressLint("SetTextI18n")
    private void updateArtworkUI() {
        Artwork current = artworkList.get(currentIndex);

        // تحديث النصوص والصورة
        tvCounter.setText((currentIndex + 1) + " / " + artworkList.size());
        ivArtwork.setImageResource(current.getImageResId());
        tvArtworkTitle.setText(current.getTitle());
        tvArtistInfo.setText(current.getArtist() + " (" + current.getYear() + ")");

        // التحكم في تمكين/تعطيل زر Previous (معطل في اللوحة الأولى 1/6)
        boolean hasPrev = (currentIndex > 0);
        btnPrevious.setEnabled(hasPrev);
        setButtonState(btnPrevious, hasPrev);

        // التحكم في تمكين/تعطيل زر Next (معطل في اللوحة الأخيرة 6/6)
        boolean hasNext = (currentIndex < artworkList.size() - 1);
        btnNext.setEnabled(hasNext);
        setButtonState(btnNext, hasNext);
    }

    // دالة مساعدة لضبط لون الزر عند التفعيل والتعطيل
    private void setButtonState(Button button, boolean isEnabled) {
        if (isEnabled) {
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.btn_active_color)));
            button.setTextColor(ContextCompat.getColor(this, R.color.btn_text_active));
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.btn_disabled_color)));
            button.setTextColor(ContextCompat.getColor(this, R.color.btn_text_disabled));
        }
    }
}