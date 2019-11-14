package com.example.mylayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SSJK_fragment extends Fragment {
    private View view;
    private GridLayout gl_ssjk;

    private void initViews(View view) {
        gl_ssjk = view.findViewById(R.id.gl_ssjk);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ssjk_fragment, container, false);
        initViews(view);
        return view;
    }

}
