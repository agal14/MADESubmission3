package com.dicoding.associate.madesubmission3.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dicoding.associate.madesubmission3.R;
import com.dicoding.associate.madesubmission3.adapter.KamusAdapter;
import com.dicoding.associate.madesubmission3.database.KamusHelper;
import com.dicoding.associate.madesubmission3.model.KamusModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.dicoding.associate.madesubmission3.MainActivity.MyPREFERENCES;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    View rootView;
    List<KamusModel> listWords = new ArrayList<>();

    private KamusAdapter kamusAdapter;
    private KamusHelper kamusHelper;
    @BindView(R.id.rv_words)
    RecyclerView rvWords;
    SharedPreferences prefs;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        rvWords = rootView.findViewById(R.id.rv_words);
        kamusAdapter = new KamusAdapter(listWords, this.getActivity());
        String strtext = getArguments().getString("queryget");
        kamusHelper = new KamusHelper(this.getActivity());
        kamusHelper.open();

        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // then you use
        String tablequery = prefs.getString("kamus", null);
        String table = "";
        if(tablequery==null){
            table = "table_englishindo";
        }
        else {
            if(tablequery.equals("engindo")){
                table = "table_englishindo";
            }
            else if(tablequery.equals("indoeng")){
                table = "table_indoenglish";
            }
        }
//        // Ambil semua data kamus di database
        ArrayList<KamusModel> kamusModels = kamusHelper.getDataByKata(strtext,table);
        if(kamusModels!=null) {
            Log.d("tes", "Data ada");
            listWords = kamusModels;
            rvWords.setHasFixedSize(true);
            rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
            rvWords.setAdapter(new KamusAdapter(listWords, getContext()));
            kamusAdapter.notifyDataSetChanged();
//            kamusAdapter.addItem(mahasiswaModels);
        }
        kamusHelper.close();
        return rootView;
    }

}
