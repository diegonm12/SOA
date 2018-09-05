package com.gastrotec.gastrotec;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HorarioComidasTab extends Fragment {
    CharSequence restaurantID;

    View myFragmentView;
    TextView showComida;
    String[] listaComida= new String[3];
    String[] listaHorario= new String[3];
    String[] listaFavor= new String[3];
    String[] listaContra = new String[3];
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        PlatillosDatabaseAdapter adapter = new PlatillosDatabaseAdapter(getContext());
        adapter.open();
        Bundle bundlePosition = getArguments();
        restaurantID = bundlePosition.getCharSequence("ID");
        Cursor item =  adapter.getRestaurantsbyID(restaurantID.toString());
        int count =item.getCount();

        for(int i = 0; i < count; i++){
            listaComida[i] = item.getString(1);
            listaHorario[i] = item.getString(2);
            listaFavor[i] = item.getString(4);
            listaContra[i] = item.getString(5);
            item.moveToNext();

        }



    }

    private void getData(PlatillosDatabaseAdapter database) {
        Cursor item =  database.getRestaurantsbyID(restaurantID.toString());
        int count =item.getCount();

        for(int i = 0; i < count; i++){
            listaComida[i] = item.getString(1);
            listaHorario[i] = item.getString(2);
            listaFavor[i] = item.getString(4);
            listaContra[i] = item.getString(5);
            item.moveToNext();

        }

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        myFragmentView  = inflater.inflate(R.layout.tab2, container, false);
        showComida = (TextView) myFragmentView.findViewById(R.id.comida_horario_comidas);
        TextView textHorariosComida1 = (TextView) myFragmentView.findViewById(R.id.textView1_horario_comidas);
        textHorariosComida1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                showComida.setText(listaComida[0]);

            }
        });
        TextView textHorariosComida2 = (TextView) myFragmentView.findViewById(R.id.textView2_horario_comidas);
        textHorariosComida2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showComida.setText(listaComida[1]);

            }
        });
        TextView textHorariosComida3 = (TextView) myFragmentView.findViewById(R.id.textView3_horario_comidas);
        textHorariosComida3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showComida.setText(listaComida[2]);

            }
        });

        return myFragmentView;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewHorario1 = myFragmentView.findViewById(R.id.textView1_horario_comidas);
        TextView textViewHorario2 = myFragmentView.findViewById(R.id.textView2_horario_comidas);
        TextView textViewHorario3 = myFragmentView.findViewById(R.id.textView3_horario_comidas);
        textViewHorario1.setText(listaHorario[0]);
        textViewHorario2.setText(listaHorario[1]);
        textViewHorario3.setText(listaHorario[2]);
    }



}