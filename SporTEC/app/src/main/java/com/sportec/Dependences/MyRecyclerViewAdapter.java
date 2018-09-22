package com.sportec.Dependences;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportec.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    //variable donde se guardaran las noticias ya que es una list new
    private List<News> mDataset;
    private static MyClickListener myClickListener;
    private static final int TYPE_I = 1;
    private static final int TYPE_N = 0;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;     //corresponde al textview donde se podra el titulo de la noticia
        ImageView imageView;    // corresponde al image view donde se podra  la image de la noticia en el car

        DataObjectHolder(View itemView) {       //se crea el holder que mantiene el textview y el imageview
            super(itemView);
            label = itemView.findViewById(R.id.card_view_title);
            imageView = itemView.findViewById(R.id.card_view_image);
            itemView.setOnClickListener(this);
        }

        //Deteccion de selecciones del usuario
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    //funcion obtener el elemento que el usuario esta seleccionando
    public void setOnItemClickListener(MyClickListener myClickListener) {
        MyRecyclerViewAdapter.myClickListener = myClickListener;
    }

    //corresponde al constructor del adapter el cual recibe la lista sobre la que se va a trabajar
    public MyRecyclerViewAdapter(List<News> myDataset) {
        mDataset = myDataset;
    }

    // este metodo se encarga de crear la vista del view de noticias, el if que se muestra
    // en el metodo diferencia las view de las noticas normales de la del dia
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        //se el viewType es igual, esa es la noticia del dia
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_day_news, parent, false);
        }
        return new DataObjectHolder(view);
    }

    // obtiene los datos de las noticias,  para este caso seria la imagen de la noticia
    // y el texto de la noticia
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getTitle());
        Picasso.get().load(mDataset.get(position).getImage()).fit().centerInside().into(holder.imageView);


    }

    //obtiene la cantidad de elementos que se encuentran en la lista
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    // funcion encarga de obtener el la noticia del dia, encontrando la diferencia
    // en el identificador "importancia"
    public int getItemViewType(int position) {
        News item = mDataset.get(position);
        if (item.getImportant().matches("1")) {
            return TYPE_I;
        } else if (item.getImportant().matches("0")) {
            return TYPE_N;
        } else {
            return -1;
        }
    }
}