package com.johnwebi.webiimageloaderexample.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnwebi.webiimageloaderexample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridLayout extends Fragment {

    View v;
    RecyclerView photosRecyclerView;

    public GridLayout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_grid_layout,container, false);
        BindView();
        return v;
    }

    private void BindView() {
        photosRecyclerView = v.findViewById(R.id.photos_recyclerview);
    }

}
