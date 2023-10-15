package com.example.vxutjlpt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.vxutjlpt.MainActivity;
import com.example.vxutjlpt.R;
import com.example.vxutjlpt.db.DbQuery;
import com.example.vxutjlpt.model.CategoryAdapter;
import com.example.vxutjlpt.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private GridView catView;
//    public static List<CategoryModel> catList = new ArrayList<>(); chuyen qua DbQuery

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Home");

        catView = view.findViewById(R.id.cat_Grid);
        
//        loadCategories();

        CategoryAdapter adapter = new CategoryAdapter(DbQuery.g_catList);
        catView.setAdapter(adapter);

        return view;
    }

}// End HomeFragment
