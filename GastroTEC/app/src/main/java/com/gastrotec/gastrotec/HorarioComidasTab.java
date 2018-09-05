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
                platillo = 1;
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
                platillo = 2;
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
                platillo = 3;
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

                    // Aqui se hace el proceso para darle like a un platillo, se actualiza
                    // en la base de datos y luego se vuelve a agarrar el nuevo puntaje a
                    // favor para mostrarlo nuevamente.

                    PlatillosDatabaseAdapter adapter = new PlatillosDatabaseAdapter(getContext());
                    adapter.open();


                    // Se hace el manejo de los los strings para ser tratados como integer
                    // para contar los likes
                    int newNumberFavor = Integer.parseInt(listaFavor[platillo-1]);
                    newNumberFavor = newNumberFavor+1;
                    String newFavor = Integer.toString(newNumberFavor);
                    int restID = Integer.parseInt(restaurantID.toString());
                    int platilloID = ((restID-1)*3)+(platillo);

                    // aqui se actualiza el valor de likes y se agarra el valor nuevo
                    adapter.updateEntryLike(Integer.toString(platilloID),newFavor);
                    Cursor cursorUpdateLike =  adapter.getRestaurantsbyID(restaurantID.toString());

                    //Se busca el cursor que contiene la informacion del platillo que se quiere
                    int countAdapter =cursorUpdateLike.getCount();

                    for(int i = 0; i < countAdapter; i++){
                        if (i+1 == platillo){
                            break;
                        }
                        cursorUpdateLike.moveToNext();

                    }
                    String newLikeShow = cursorUpdateLike.getString(4);
                    showFavor.setText(newLikeShow);
                    getData(adapter);



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
                else {
                    // Aqui se hace el proceso para darle no me gusta a un platillo, se actualiza
                    // en la base de datos y luego se vuelve a agarrar el nuevo puntaje a
                    // favor para mostrarlo nuevamente.

                    PlatillosDatabaseAdapter adapter = new PlatillosDatabaseAdapter(getContext());
                    adapter.open();


                    // Se hace el manejo de los los strings para ser tratados como integer
                    // para contar los likes
                    int newNumberDislike = Integer.parseInt(listaContra[platillo-1]);
                    newNumberDislike = newNumberDislike+1;
                    String newDislike = Integer.toString(newNumberDislike);
                    int restID = Integer.parseInt(restaurantID.toString());
                    int platilloID = ((restID-1)*3)+(platillo);

                    // aqui se actualiza el valor de likes y se agarra el valor nuevo
                    adapter.updateEntryDislike(Integer.toString(platilloID),newDislike);
                    Cursor cursorUpdateDislike =  adapter.getRestaurantsbyID(restaurantID.toString());

                    //Se busca el cursor que contiene la informacion del platillo que se quiere
                    int countAdapter =cursorUpdateDislike.getCount();

                    for(int i = 0; i < countAdapter; i++){
                        if (i+1 == platillo){
                            break;
                        }
                        cursorUpdateDislike.moveToNext();

                    }
                    String newDislikeShow = cursorUpdateDislike.getString(5);
                    showContra.setText(newDislikeShow);
                    getData(adapter);

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