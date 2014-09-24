package com.philpicinic.easybillsplit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philpicinic.easybillsplit.R;

/**
 * Created by Phil on 9/24/2014.
 */
public class SharedItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.shared_item_layout, null);
    }
}
