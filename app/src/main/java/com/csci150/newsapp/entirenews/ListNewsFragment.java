package com.csci150.newsapp.entirenews;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csci150.newsapp.entirenews.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListNewsFragment extends Fragment {
    private final String TAG = "ListNewsFragment";

    public ListNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListNewsFragment.
     */
    public static ListNewsFragment newInstance() {
        return new ListNewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Utils.print(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_list_news, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.print(TAG, "onPause");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Utils.print(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Utils.print(TAG, "onDetach");
    }
}
