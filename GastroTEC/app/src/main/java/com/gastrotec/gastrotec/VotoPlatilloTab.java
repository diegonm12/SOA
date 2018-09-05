package com.gastrotec.gastrotec;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gastrotec.gastrotec.R;

public class VotoPlatilloTab extends Fragment {
    CharSequence restaurantID;
    View myFragmentView;
    TextView showComidaDislike;
    TextView showComidaLike;
    PlatillosDatabaseAdapter adapter;

    String[] listaFavor= new String[3];
    String[] listaContra = new String[3];
    String[] listaComida = new String[3];

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        adapter = new PlatillosDatabaseAdapter(getContext());
        adapter.open();
        Bundle bundlePosition = getArguments();
        restaurantID = bundlePosition.getCharSequence("ID");
        getData(adapter);
        System.out.println("ENTRE ACA");

    }

    private void getData(PlatillosDatabaseAdapter database) {
        Cursor item =  database.getRestaurantsbyID(restaurantID.toString());
        int count =item.getCount();

        for(int i = 0; i < count; i++){

            listaComida[i] = item.getString(1);
            listaFavor[i] = item.getString(4);
            listaContra[i] = item.getString(5);
            item.moveToNext();

        }

    }



    public int getIndexOfLargest( String[] array )
    {
        if ( array == null || array.length == 0 ) return -1; // null or empty
        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( Integer.parseInt(array[i]) > Integer.parseInt(array[largest]) ) largest = i;
        }
        return largest; // position of the first largest found
    }





    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        getData(adapter);
        myFragmentView  = inflater.inflate(R.layout.tab3, container, false);
        showComidaLike = (TextView) myFragmentView.findViewById(R.id.textView2_VotoPlatillo_tab);
        showComidaDislike = (TextView) myFragmentView.findViewById(R.id.textView4_VotoPlatillo_tab);
        showComidaLike.setText(listaComida[getIndexOfLargest(listaFavor)]);
        showComidaDislike.setText(listaComida[getIndexOfLargest(listaContra)]);
        return myFragmentView;


    }

}