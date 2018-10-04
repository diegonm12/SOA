package com.sportec.Dependences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sportec.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;    //infla  el layout para la busqueda de contenidos
    private List<SearchResult> mListResultsAdapter = new ArrayList<>(); //lista de la busqueda

    //corresponde al constructor de la  clase recibiendo los resultados de la busque
    // y ademas el contexto que maneja la activity
    public ListViewAdapter(Context context, List<SearchResult> ListElements) {
        inflater = LayoutInflater.from(context);
        mListResultsAdapter.clear();
        this.mListResultsAdapter.addAll(ListElements);
    }

    public class ViewHolder {
        TextView name;
    }

    // retorna la cantidad de resultados que se pueden presentar en el contendor de bsuqueda
    @Override
    public int getCount() {
        return mListResultsAdapter.size();
    }

    //retorna el item que se esta seleccionando segun sea la busqueda del user
    @Override
    public SearchResult getItem(int position) {
        return mListResultsAdapter.get(position);
    }

    //retorna el id del item
    @Override
    public long getItemId(int position) {
        return position;
    }

    //obtiene la view para que se pueda desplegar todos los resultados de la busqueda
    // mediante un view holder pueden observarse los resultados
    @SuppressLint("InflateParams")
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            holder.name = view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(mListResultsAdapter.get(position).getNameResult());
        return view;
    }

}