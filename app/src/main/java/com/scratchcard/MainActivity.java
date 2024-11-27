package com.scratchcard;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codingvisions.scratchcard.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ScratchCardView scratchCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        scratchCardView  = findViewById(R.id.scratch_card);
        String randomText = getRandomText();
        scratchCardView.setRandomText(randomText);

    }

    private String getRandomText() {
        // Your list of random texts
        String[] texts = {"Congratulations!", "You Won!", "Try Again!", "Good Luck!"};
        Random random = new Random();
        int randomIndex = random.nextInt(texts.length);
        return texts[randomIndex];
    }
}