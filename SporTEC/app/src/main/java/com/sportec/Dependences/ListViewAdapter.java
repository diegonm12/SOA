package com.sportec.Dependences;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sportec.Activities.MainActivity;
import com.sportec.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<SearchResult> mListResultsAdapter = new ArrayList<>() ;

    public ListViewAdapter(Context context ) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);

        this.mListResultsAdapter.addAll(MainActivity.mListResults);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return MainActivity.mListResults.size();
    }

    @Override
    public SearchResult getItem(int position) {
        return MainActivity.mListResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(MainActivity.mListResults.get(position).getNameResult());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        MainActivity.mListResults.clear();
        if (charText.length() == 0) {
            MainActivity.mListResults.addAll(mListResultsAdapter);
        } else {
            for (SearchResult wp : mListResultsAdapter) {
                if (wp.getNameResult().toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainActivity.mListResults.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}