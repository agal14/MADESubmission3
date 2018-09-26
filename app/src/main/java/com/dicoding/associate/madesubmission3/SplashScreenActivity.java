package com.dicoding.associate.madesubmission3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.dicoding.associate.madesubmission3.database.KamusHelper;
import com.dicoding.associate.madesubmission3.model.KamusModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @BindView(R.id.progress_bar)
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        pb = findViewById(R.id.progress_bar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;


        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashScreenActivity.this);
            appPreference = new AppPreference(SplashScreenActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {

                ArrayList<KamusModel> kamusModelsEngIndo = preLoadRaw(R.raw.english_indonesia);
                ArrayList<KamusModel> kamusModelsIndoEng = preLoadRaw(R.raw.indonesia_english);

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusModelsEngIndo.size();

                for (KamusModel model1 : kamusModelsEngIndo) {
                    kamusHelper.insertEnglishIndoTransaction(model1);
                    progress += progressDiff;
                    publishProgress((int) progress);
                }

                for (KamusModel model2 : kamusModelsIndoEng) {
                    kamusHelper.insertIndoEnglishTransaction(model2);
                    progress += progressDiff;
                    publishProgress((int) progress);
                }

                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(int valRaw) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(valRaw);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(count, splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
        }
        return kamusModels;
    }
}
