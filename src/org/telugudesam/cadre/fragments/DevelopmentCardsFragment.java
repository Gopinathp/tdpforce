package org.telugudesam.cadre.fragments;

import org.telugudesam.cadre.R;
import org.telugudesam.cadre.adapters.DevelopmentCardsAdapter;
import org.telugudesam.cadre.objects.Section;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A fragment representing a section of the app that simply
 * displays the development cards.
 */
public class DevelopmentCardsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public DevelopmentCardsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tdp_main, container, false);
        ListView gridView = (ListView) rootView.findViewById(R.id.staggeredGridView1);
        gridView.setAdapter(new DevelopmentCardsAdapter(getActivity(), Section.values()[getArguments().getInt(ARG_SECTION_NUMBER)]));
        return rootView;
    }
}
