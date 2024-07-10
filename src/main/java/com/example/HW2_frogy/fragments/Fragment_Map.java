package com.example.HW2_frogy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.HW2_frogy.R;
import com.example.HW2_frogy.callbacks.CallBack_Map;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Fragment_Map extends Fragment {

    private SupportMapFragment supportMapFragment;
    private CallBack_Map callBackMap;

    public void setCallBackMap(CallBack_Map callBackMap) {
        this.callBackMap = callBackMap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initializeMapFragment();
        return view;
    }

    private void initializeMapFragment() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(onMapReadyCallback);
        }
    }

    private final OnMapReadyCallback onMapReadyCallback = googleMap -> callBackMap.setMarkers(googleMap);
}
