package com.example.randomquotes;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.example.randomquotes.databinding.ActivityMainBinding;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ApiViewModel apiViewModel;
    private String quote = "";
    private String author = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);

        showQuote();
        binding.nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuote();
            }
        });
        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote();
            }
        });
    }

    private void shareQuote() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this quote!\n " + quote + " \n said by " + author);
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent, "Share this Quote");
        startActivity(shareIntent);
    }

    private void showQuote() {
        apiViewModel.loadQuote(new ApiViewModel.OnSuccessListener() {
            @Override
            public void onSuccess(JSONObject response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    quote = response.getString("content");
                    author = response.getString("author");
                    binding.quoteText.setText(quote);
                    binding.quoteAuthor.setText(author);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
