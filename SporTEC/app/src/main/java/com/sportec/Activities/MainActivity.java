package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Dependences.MyRecyclerViewAdapter;
import com.sportec.Dependences.News;
import com.sportec.R;
import com.sportec.Service.ApiService;
import com.sportec.Service.ApiServiceNews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String mUserEmail;
    public static List<News> mNewsArray = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // crea un toolbar donde se pone el nombre de la app, la lupa de busqueda y el boton
        // de menu desplegable
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //se encarga manejar la interfaz segun este abierto el menu desplegable o no
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // navigation view que corresponde al menu desplegable al lado izquierdo
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //se usa este intent para obtener el valor del email para identificar el user
        Intent intent = getIntent();
        mUserEmail = intent.getStringExtra("emailUser");

        //se llama el metodo que trae toda las noticias desde la base de datos
        getNewsFromDB();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        setHeader();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.activity_main_cerrar_sesion) {
            final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    cerrarSesion(result.get("name").getAsString(),
                            mUserEmail, result.get("password").getAsString(),
                            result.get("profilePicture").getAsString(), result.get("favSport"));

                }
            };
            ApiService api = new ApiService();
            api.downloadUser(this, mUserEmail, arreglo);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // aqui se deberia agarrar la otra informacion del user por que solo tenemos email
    public void setHeader() {
        //obtengo los elementos del layout
        TextView emailTextView = findViewById(R.id.activity_main_mail);
        final TextView nameTextView = findViewById(R.id.activity_main_name);
        final ImageView imageViewUserPhoto = findViewById(R.id.activity_main_image);
        final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                nameTextView.setText(result.get("name").getAsString());
                Picasso.get().load(result.get("profilePicture").getAsString()).into(imageViewUserPhoto);


            }
        };
        ApiService api = new ApiService();
        api.downloadUser(this, mUserEmail, arreglo);

        //modifico los valores de la barra  del layout con los valores del user
        emailTextView.setText(mUserEmail);


    }

    //metodo encargado de cerrar sesion, lo que hace es cambiarle a user el parametro de
    //sessionInit en cero, que significa que no hay ninguna sesion abierta
    private void cerrarSesion(String name, String mUserEmail, String password, String profilePicture, JsonElement favSport) {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email", mUserEmail);
        json.addProperty("password", password);
        json.addProperty("profilePicture", profilePicture);
        json.addProperty("sessionInit", "0");
        json.add("favSport", favSport);
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                LoginManager.getInstance().logOut();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        };
        ApiService api = new ApiService();
        api.updateUser(MainActivity.this, arreglo, json, mUserEmail);


    }

    // metodo que se encarga de traer todas las noticias desde la base de datos
    private void getNewsFromDB() {
        // obtengo todos las noticas para desplegar la info en el grid
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                final List<News> newsDesorder = new ArrayList<>();

                //obtengo todas las noticias y las meto en una lista de noticias
                for (int i = 0; i < result.size(); i++) {
                    News newNews = new News();
                    newNews.setSportName(result.get(i).getAsJsonObject().get("sport").getAsString());
                    newNews.setTitle(result.get(i).getAsJsonObject().get("title").getAsString());
                    newNews.setContent(result.get(i).getAsJsonObject().get("content").getAsString());
                    newNews.setImportant(result.get(i).getAsJsonObject().get("important").getAsString());
                    newNews.setImage(result.get(i).getAsJsonObject().get("image").getAsString());
                    newsDesorder.add(newNews);
                }

                //en esta linea se llama al metodo que reordena las noticias segun
                // el gusto de deportes del user
                reorderList(newsDesorder);
            }
        };
        ApiServiceNews api = new ApiServiceNews();
        api.downloadNews(this, arreglo);

    }

    // metodo encargado de reordenar la lista segun los gustos del user
    private void reorderList(final List<News> newsDesorder) {
        final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                //obtengo los deporte favoritos desde la base de datos con "favSport"
                JsonArray arraySportsPref = result.get("favSport").getAsJsonArray();
                System.out.println(arraySportsPref);

                //Para meter primeramente la noticia del dia
                for (int i = 0; i < newsDesorder.size(); i++) {
                    if (newsDesorder.get(i).getImportant().matches("1")) {
                        mNewsArray.add(newsDesorder.get(i));
                        newsDesorder.remove(i); // se elimina para que no se vuelva a meter
                    }
                }

                //Para meter las noticias que mas le interesan al usuario
                for (int i = 0; i < newsDesorder.size(); i++) {
                    for (int j = 0; j < arraySportsPref.size(); j++) {
                        if (arraySportsPref.get(j).getAsString().matches(newsDesorder.get(i).getSportName())) {
                            mNewsArray.add(newsDesorder.get(i));
                            newsDesorder.get(i).setImportant("-1");
                        }
                    }
                }

                // se ingresan las otras noticias revisando que no se repitan
                for (int i = 0; i < newsDesorder.size(); i++) {
                    if(!(newsDesorder.get(i).getImportant().matches("-1"))){
                        mNewsArray.add(newsDesorder.get(i));
                    }
                }

                //El siguiente codigo corresponde a la implementacion del recycler
                mRecyclerView = findViewById(R.id.my_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getBaseContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new MyRecyclerViewAdapter(mNewsArray);
                mRecyclerView.setAdapter(mAdapter);


                ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        // se crea el intent que me lleva a cada noticia, se pasa lainformacion
                        // mediante GSON
                        News newsSelected = new News();
                        newsSelected = mNewsArray.get(position);

                        //se inicializa el Gson para la infromacion de la noticia
                        Gson gsonNewsSelected = new Gson();


                        Intent intentSportsNews = new Intent(MainActivity.this, NewsActivity.class);
                        intentSportsNews.putExtra("News", gsonNewsSelected.toJson(newsSelected));
                        startActivity(intentSportsNews);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });


            }
        };
        ApiService api = new ApiService();
        api.downloadUser(this, mUserEmail, arreglo);


    }


}
