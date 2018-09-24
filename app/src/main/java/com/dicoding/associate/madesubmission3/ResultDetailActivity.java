package com.dicoding.associate.madesubmission3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

public class ResultDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.tv_translate)
    TextView tvTranslate;
    @BindView(R.id.tv_keterangan)
    TextView tvKet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        tvWord = findViewById(R.id.tv_word);
        tvTranslate = findViewById(R.id.tv_translate);

        tvWord.setText(getIntent().getStringExtra("word"));
        tvTranslate.setText(getIntent().getStringExtra("translate"));

    }
}
