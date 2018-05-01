package com.app.sample.recipe.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.sample.recipe.R;

/**
 * Created by shashank on 17/4/18.
 */

public class SignOut extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, null);
        // activate fragment menu
        setHasOptionsMenu(true);
            System.out.println("dsl,dfs,");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);
        System.out.println("dsl,dfs,");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar.make(view, item.getTitle() + " clicked", Snackbar.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);

    }
}