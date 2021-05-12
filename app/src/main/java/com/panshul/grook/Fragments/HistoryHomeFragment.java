package com.panshul.grook.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panshul.grook.R;

public class HistoryHomeFragment extends Fragment {

    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_history_home, container, false);
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.grook.history", Context.MODE_PRIVATE);
        String json = pref.getString("history","");
        Log.i("json",json);
        return view;
    }
}