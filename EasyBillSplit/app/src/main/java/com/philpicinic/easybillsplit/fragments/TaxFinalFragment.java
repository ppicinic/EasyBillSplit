package com.philpicinic.easybillsplit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philpicinic.easybillsplit.R;

/**
 * Created by Phil on 10/9/2014.
 */
public class TaxFinalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.tax_final_layout, null);
        return view;
    }
}
