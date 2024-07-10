package com.example.HW2_frogy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.HW2_frogy.R;
import com.example.HW2_frogy.callbacks.CallBack_List;
import com.example.HW2_frogy.extras.RecordsList;
import com.example.HW2_frogy.extras.Scores;

public class Fragment_List extends Fragment {

    private ListView listView;
    private CallBack_List callBackUserInfo;

    public void setCallBackUserInfo(CallBack_List callBackUserInfo) {
        this.callBackUserInfo = callBackUserInfo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        listView = view.findViewById(R.id.fragmentList_top10);
    }

    private void initViews() {
        RecordsList data = callBackUserInfo.getResults();
        if (data != null) {
            ArrayAdapter<Scores> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data.getResults());
            listView.setAdapter(adapter);
        }
    }
}
