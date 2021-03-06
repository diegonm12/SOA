package com.sportec.Activities;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Dependences.ListViewAdapter;
import com.sportec.Dependences.MyRecyclerViewAdapter;
import com.sportec.Dependences.News;
import com.sportec.Dependences.SearchResult;
import com.sportec.Dependences.Sport;
import com.sportec.Dependences.Team;
import com.sportec.Dependences.User;
import com.sportec.R;
import com.sportec.Service.ApiService;
import com.sportec.Service.ApiServiceContent;
import com.sportec.Service.ApiServiceNews;
import com.sportec.Service.ApiServiceSports;
import com.sportec.Service.ApiServiceTeams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String mUserEmail; //corresponde al email del usuario actual en el activity
    public static List<News> mNewsArray = new ArrayList<>(); //tenemos las listas de noticias que se van a presentar
    @SuppressLint("StaticFieldLeak")
    public static RecyclerView mRecyclerView; //se define un recycler para mostrar las noticias
    public static RecyclerView.Adapter mAdapter; //se define un adapter para la listview de noticias
    public static RecyclerView.LayoutManager mLayoutManager; //corresponde al layout del recycler
    @SuppressLint("StaticFieldLeak")
    public static ListView mListViewResults; //listview de los resultados de la noticia
    @SuppressLint("StaticFieldLeak")
    public static ListViewAdapter mAdapterList; //correspondiente adaptador para ver la  noticia
    @SuppressLint("StaticFieldLeak")
    public static SearchView mEditsearch; // se crea un searchview para el  buscador de contenido
    public static List<SearchResult> mListResults = new ArrayList<>(); //un listado de dicho buscador de contenido

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
        mNewsArray.clear();

        //se llama el metodo que trae toda las noticias desde la base de datos
        getNewsFromDB();

    }

    // corresponde a la opcion para poder abrir el menu lateral
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // metodo que se encarga de crear las opciones de la barra de la aplicacion
    //en donde esta definido el buscador de contenido ademas del menu lateral
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        setHeader();

        //se define un search edit para lograr buscar el contenido
        mEditsearch = (SearchView) menu.findItem(R.id.search).getActionView();

        mEditsearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout finderLayout = findViewById(R.id.finderLayout);
                finderLayout.setVisibility(View.VISIBLE);

                // se crea la listview para ver los resultados del search segun sea lo que
                // el user escriba
                mListViewResults = findViewById(R.id.listview);

                mEditsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    // como se necesita buscar informacion en la base de datos, el siguiente
                    //metodo permite ir buscando por cada letra que el usuario ingrese
                    @Override
                    public boolean onQueryTextChange(String newText) {

                        final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                mListResults.clear();

                                // este corresponde alcaso en que la busqueda de contenido coincide con las noticias o sea de tipo noticias
                                if (!(result.get("news").isJsonNull())) {
                                    JsonArray arrayResult = result.get("news").getAsJsonArray();
                                    for (int i = 0; i < arrayResult.size(); i++) {
                                        SearchResult newResultSearch = new SearchResult();
                                        newResultSearch.setNameResult(arrayResult.get(i).getAsJsonObject().get("title").getAsString());
                                        newResultSearch.setNameClass(arrayResult.get(i).getAsJsonObject().get("type").getAsString());
                                        newResultSearch.setIdentifier(arrayResult.get(i).getAsJsonObject().get("_id").getAsString());
                                        mListResults.add(newResultSearch);
                                    }
                                }

                                // este corresponde alcaso en que la busqueda de contenido coincide con los usuarios o sea de tipo los usuarios
                                if (!(result.get("user").isJsonNull())) {
                                    JsonArray arrayResult = result.get("user").getAsJsonArray();
                                    for (int i = 0; i < arrayResult.size(); i++) {
                                        SearchResult newResultSearch = new SearchResult();
                                        newResultSearch.setNameResult(arrayResult.get(i).getAsJsonObject().get("name").getAsString());
                                        newResultSearch.setNameClass(arrayResult.get(i).getAsJsonObject().get("type").getAsString());
                                        newResultSearch.setIdentifier(arrayResult.get(i).getAsJsonObject().get("email").getAsString());
                                        mListResults.add(newResultSearch);
                                    }
                                }

                                // este corresponde alcaso en que la busqueda de contenido coincide con los deportes o sea de tipo los deporte
                                if (!(result.get("sport").isJsonNull())) {
                                    JsonArray arrayResult = result.get("sport").getAsJsonArray();
                                    for (int i = 0; i < arrayResult.size(); i++) {
                                        SearchResult newResultSearch = new SearchResult();
                                        newResultSearch.setNameResult(arrayResult.get(i).getAsJsonObject().get("name").getAsString());
                                        newResultSearch.setNameClass(arrayResult.get(i).getAsJsonObject().get("type").getAsString());
                                        newResultSearch.setIdentifier(arrayResult.get(i).getAsJsonObject().get("_id").getAsString());
                                        mListResults.add(newResultSearch);
                                    }
                                }

                                // este corresponde alcaso en que la busqueda de contenido coincide con los equipos o sea de tipo equipos
                                if (!(result.get("team").isJsonNull())) {
                                    JsonArray arrayResult = result.get("team").getAsJsonArray();
                                    for (int i = 0; i < arrayResult.size(); i++) {
                                        SearchResult newResultSearch = new SearchResult();
                                        newResultSearch.setNameResult(arrayResult.get(i).getAsJsonObject().get("name").getAsString());
                                        newResultSearch.setNameClass(arrayResult.get(i).getAsJsonObject().get("type").getAsString());
                                        newResultSearch.setIdentifier(arrayResult.get(i).getAsJsonObject().get("_id").getAsString());
                                        mListResults.add(newResultSearch);
                                    }
                                }


                                // Pasa el resultado a la clase ListViewAdapter para que se muestre los resultados
                                mAdapterList = new ListViewAdapter(getApplicationContext(), mListResults);

                                // Se hace el bind del adapter a la clase viewer
                                mListViewResults.setAdapter(mAdapterList);
                            }
                        };
                        ApiServiceContent api = new ApiServiceContent();
                        api.downloadSearch(getApplicationContext(), arreglo, newText);
                        return false;
                    }
                });

                //se necesita que cuando el usuario clickea en la x
                //se cierre el layout de las busquedas, por esto se implementa el onClose
                mEditsearch.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        RelativeLayout finderLayout = findViewById(R.id.finderLayout);
                        finderLayout.setVisibility(View.INVISIBLE);
                        return false;
                    }
                });

                // de todas las opciones que aparecen en la lista de busqueda cuando
                // el usuario la escoge, se selecciona mendiante este metodo
                mListViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, MainActivity.mListResults.get(position).getNameResult(), Toast.LENGTH_SHORT).show();

                        //Para el caso en que la seleccion del  buscador sea una noticia
                        if (MainActivity.mListResults.get(position).getNameClass().matches("news")) {
                            final FutureCallback<JsonObject> arregloNews = new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    // se define la noticia que escogio  el user
                                    News newNews = new News();
                                    newNews.setSportName(result.getAsJsonObject().get("sport").getAsString());
                                    newNews.setTitle(result.getAsJsonObject().get("title").getAsString());
                                    newNews.setContent(result.getAsJsonObject().get("content").getAsString());
                                    newNews.setImportant(result.getAsJsonObject().get("important").getAsString());
                                    newNews.setImage(result.getAsJsonObject().get("image").getAsString());
                                    newNews.setType(result.getAsJsonObject().get("type").getAsString());

                                    //se inicializa el Gson para la infromacion de la noticia
                                    Gson gsonNewsSelected = new Gson();

                                    //se hace el intent a la vista de noticias.
                                    Intent intentSportsNews = new Intent(MainActivity.this, NewsActivity.class);
                                    intentSportsNews.putExtra("News", gsonNewsSelected.toJson(newNews));
                                    startActivity(intentSportsNews);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            };
                            ApiServiceNews api = new ApiServiceNews();
                            api.downloadNewById(MainActivity.this, arregloNews, mListResults.get(position).getIdentifier());
                        }

                        //Para este caso seria que el usuario clickeo sobre algunos de los perfiles
                        // de los usuarios
                        if (MainActivity.mListResults.get(position).getNameClass().matches("user")) {
                            final FutureCallback<JsonObject> arregloUser = new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    // se define el usuario que escogio  el user
                                    User userSelected = new User();
                                    userSelected.setName(result.getAsJsonObject().get("name").getAsString());
                                    userSelected.setmEmail(result.getAsJsonObject().get("email").getAsString());
                                    userSelected.setmPassword(result.getAsJsonObject().get("password").getAsString());
                                    userSelected.setmProfilePicture(result.getAsJsonObject().get("profilePicture").getAsString());
                                    userSelected.setmType(result.getAsJsonObject().get("type").getAsString());
                                    userSelected.setFavSports(result.getAsJsonObject().get("favSport").getAsJsonArray());

                                    //se inicializa el Gson para la infromacion de la noticia
                                    Gson gsonNewsSelected = new Gson();

                                    //se hace el intent a la vista de los user.
                                    //En este caso aparte del objeto user, adjunto el email que quiere buscar al
                                    //usuario corresponiente, esto para darle permisos de cambios o no
                                    Intent intentUserProfile = new Intent(MainActivity.this, ProfileUserActivity.class);
                                    intentUserProfile.putExtra("User", gsonNewsSelected.toJson(userSelected));
                                    intentUserProfile.putExtra("currentUser", mUserEmail);
                                    startActivity(intentUserProfile);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            };
                            ApiService apiUser = new ApiService();
                            apiUser.downloadUser(MainActivity.this, mListResults.get(position).getIdentifier(), arregloUser);
                        }

                        //Para este caso seria que el usuario clickeo sobre algunos de los perfiles
                        // de los nombres de los equipos
                        if (MainActivity.mListResults.get(position).getNameClass().matches("team")) {
                            final FutureCallback<JsonObject> arregloTeam = new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    // se define el equipo que escogio  el user
                                    Team teamSelected = new Team();
                                    teamSelected.setmName(result.getAsJsonObject().get("name").getAsString());
                                    teamSelected.setmImage(result.getAsJsonObject().get("image").getAsString());
                                    teamSelected.setMemeber(result.getAsJsonObject().get("member").getAsJsonArray());
                                    teamSelected.setmType(result.getAsJsonObject().get("type").getAsString());
                                    teamSelected.setmSport(result.getAsJsonObject().get("sport").getAsString());
                                    teamSelected.setId(result.getAsJsonObject().get("_id").getAsString());


                                    //se inicializa el Gson para la infromacion de los equipos
                                    Gson gsonTeamSelected = new Gson();

                                    //se hace el intent a la vista de los equipos.
                                    Intent intentTeamToShow = new Intent(MainActivity.this, ShowTeamActivity.class);
                                    intentTeamToShow.putExtra("Team", gsonTeamSelected.toJson(teamSelected));
                                    startActivity(intentTeamToShow);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            };
                            ApiServiceTeams apiTeam = new ApiServiceTeams();
                            apiTeam.downloadTeamById(MainActivity.this, arregloTeam, mListResults.get(position).getIdentifier());
                        }

                        if (MainActivity.mListResults.get(position).getNameClass().matches("sport")) {
                            final FutureCallback<JsonObject> arregloSport = new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    // se define el deporte que escogio  el user
                                    Sport sportSelected = new Sport();
                                    sportSelected.setName(result.getAsJsonObject().get("name").getAsString());

                                    //se hace el intent a la vista de los deportes
                                    Intent intentTeamToShow = new Intent(MainActivity.this, SportSelectedActivity.class);
                                    intentTeamToShow.putExtra("sportSelected", sportSelected.getName());
                                    startActivity(intentTeamToShow);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            };
                            ApiServiceSports apiTeam = new ApiServiceSports();
                            apiTeam.downloadSportById(MainActivity.this, arregloSport, mListResults.get(position).getIdentifier());
                        }
                    }
                });
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
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
    private void cerrarSesion(String name, String mUserEmail, String password, String profilePicture, JsonElement favSport, String type) {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email", mUserEmail);
        json.addProperty("password", password);
        json.addProperty("profilePicture", profilePicture);
        json.addProperty("sessionInit", "0");
        json.add("favSport", favSport);
        json.addProperty("type", type);
        mNewsArray.clear();
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                LoginManager.getInstance().logOut();
                Intent intentCloseSesion = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intentCloseSesion);
            }
        };
        ApiService api = new ApiService();
        api.updateUser(MainActivity.this, arreglo, json, mUserEmail);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Aqui se manejan las opciones que corresponden a la barra lateral
        int id = item.getItemId();

        if (id == R.id.activity_profile) {
            final FutureCallback<JsonObject> arregloUser = new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                    // se define el usuario que escogio  el user
                    User userSelected = new User();
                    userSelected.setName(result.getAsJsonObject().get("name").getAsString());
                    userSelected.setmEmail(result.getAsJsonObject().get("email").getAsString());
                    userSelected.setmPassword(result.getAsJsonObject().get("password").getAsString());
                    userSelected.setmProfilePicture(result.getAsJsonObject().get("profilePicture").getAsString());
                    userSelected.setmType(result.getAsJsonObject().get("type").getAsString());
                    userSelected.setFavSports(result.getAsJsonObject().get("favSport").getAsJsonArray());

                    //se inicializa el Gson para la infromacion de la noticia
                    Gson gsonNewsSelected = new Gson();

                    //se hace el intent a la vista de los user.
                    //En este caso aparte del objeto user, adjunto el email que quiere buscar al
                    //usuario corresponiente, esto para darle permisos de cambios o no
                    Intent intentUserProfile = new Intent(MainActivity.this, ProfileUserActivity.class);
                    intentUserProfile.putExtra("User", gsonNewsSelected.toJson(userSelected));
                    intentUserProfile.putExtra("currentUser", mUserEmail);
                    startActivity(intentUserProfile);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            };
            ApiService apiUser = new ApiService();
            apiUser.downloadUser(MainActivity.this, mUserEmail, arregloUser);

        } else if (id == R.id.activity_main_cerrar_sesion) {
            final FutureCallback<JsonObject> arreglo = new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    cerrarSesion(result.get("name").getAsString(),
                            mUserEmail, result.get("password").getAsString(),
                            result.get("profilePicture").getAsString(), result.get("favSport"), result.get("type").getAsString());

                }
            };
            ApiService api = new ApiService();
            api.downloadUser(this, mUserEmail, arreglo);
        } else if (id == R.id.activity_sports) {
            //pasa a la pantalla de la escogencia de deporte favoritos
            Intent intentCheckSports = new Intent(MainActivity.this, CheckSportsActivity.class);
            intentCheckSports.putExtra("emailUser", mUserEmail);
            intentCheckSports.putExtra("permission", "0");
            startActivity(intentCheckSports);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    newNews.setType(result.get(i).getAsJsonObject().get("type").getAsString());
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
                    if (!(newsDesorder.get(i).getImportant().matches("-1"))) {
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
                        News newsSelected;
                        newsSelected = mNewsArray.get(position);

                        //se inicializa el Gson para la infromacion de la noticia
                        Gson gsonNewsSelected = new Gson();

                        //Se hace el cambio del intent
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
