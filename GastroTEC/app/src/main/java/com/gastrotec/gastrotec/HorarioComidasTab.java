package com.gastrotec.gastrotec;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HorarioComidasTab extends Fragment {
    CharSequence restaurantID;

    View myFragmentView;
    TextView showComida;
    TextView showFavor;
    TextView showContra;
    int platillo = 12;
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
        getData(adapter);



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
        showFavor = (TextView) myFragmentView.findViewById(R.id.textView8_horario_comidas);
        showContra = (TextView) myFragmentView.findViewById(R.id.textView9_horario_comidas);
        TextView textHorariosComida1 = (TextView) myFragmentView.findViewById(R.id.textView1_horario_comidas);
        textHorariosComida1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                platillo = 0;
                showComida.setText(listaComida[0]);
                showFavor.setText(listaFavor[0]);
                showContra.setText(listaContra[0]);

            }
        });
        TextView textHorariosComida2 = (TextView) myFragmentView.findViewById(R.id.textView2_horario_comidas);
        textHorariosComida2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                platillo = 1;
                showComida.setText(listaComida[1]);
                showFavor.setText(listaFavor[1]);
                showContra.setText(listaContra[1]);

            }
        });
        TextView textHorariosComida3 = (TextView) myFragmentView.findViewById(R.id.textView3_horario_comidas);
        textHorariosComida3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                platillo = 2;
                showComida.setText(listaComida[2]);
                showFavor.setText(listaFavor[2]);
                showContra.setText(listaContra[2]);

            }
        });

        ImageView imagenLike = (ImageView) myFragmentView.findViewById(R.id.imageView_horario_comidas);
        imagenLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (platillo == 12){

                }
                else{
                    PlatillosDatabaseAdapter adapter = new PlatillosDatabaseAdapter(getContext());
                    adapter.open();
                    int newNumberFavor = Integer.parseInt(listaFavor[platillo]);
                    newNumberFavor = newNumberFavor+1;
                    String newFavor = Integer.toString(newNumberFavor);
                    int restID = Integer.parseInt(restaurantID.toString());
                    //int restaurantToChange = restID*(platillo+restID);
                    //adapter.updateEntryLike();


                }

            }
        });

        ImageView imagenDisLike = (ImageView) myFragmentView.findViewById(R.id.imageView2_horario_comidas);
        imagenDisLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Dislike");
                if (platillo == 12){

                }


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