package com.ucb.suficiencia.news;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

public class HeadlineListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Example headlines
        String[] headlines = {
                "Breaking News: Market Hits All-Time High",
                "Local Man Gets Hit by Truck, Wakes Up in Fantasy World",
                "Teen Accidentally Summons Dragon While Doing Homework"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.headline_list_item, R.id.headline_text, headlines);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String selectedHeadline = (String) l.getItemAtPosition(position);
        ((MainActivity) getActivity()).showNewsContent(selectedHeadline);
    }
}
