package com.sportec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Dependences.CustomListAdapter;
import com.sportec.Dependences.Team;
import com.sportec.Dependences.User;
import com.sportec.R;
import com.sportec.Service.ApiService;
import com.sportec.Service.ApiServiceTeams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowTeamActivity extends AppCompatActivity {
    public static List<String> mListNameTeam = new ArrayList<>();
    public static List<String> mListImageTeam = new ArrayList<>();
    public static List<String> mListUserEmail = new ArrayList<>();
    public static List<String> mListUserName = new ArrayList<>();
    public static List<String> mListUserImage = new ArrayList<>();
    public static Team teamToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_team);

        //obtengo la informacion del pasandolo por GSON
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("Team");
        teamToShow = gson.fromJson(strObj, Team.class);

        //coloco el del equipo en el textview corresponiente al nombre
        TextView nameTeamTextView = findViewById(R.id.activity_show_team_name);
        nameTeamTextView.setText(teamToShow.getmName());

        //coloco la imagen del equipo al image view correspondiente
        ImageView imageTeam = findViewById(R.id.activity_show_team_image);
        Picasso.get()
                .load(teamToShow.getmImage())
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(imageTeam);

        //se llena la lista con los mmiebros del equipo
        fillListNames(teamToShow.getMember());

        // List view que corresponde a la lista de los miembros del equipo
        ListView list;
        CustomListAdapter adapter = new CustomListAdapter(ShowTeamActivity.this, mListNameTeam, mListImageTeam);
        list = findViewById(R.id.activity_show_team_list_member);
        list.setAdapter(adapter);

    }

    private void fillListNames(JsonElement member) {
        for(int i = 0; i < member.getAsJsonArray().size(); i++){
            mListNameTeam.add(member.getAsJsonArray().get(i).getAsString());
            System.out.println(member.getAsJsonArray().get(i).getAsString());
        }
    }

    public void goBack(View view) {
        mListNameTeam.clear();
        finish();
    }

    public void VisibleLayout(View view) {
        final RelativeLayout layoutToShow = findViewById(R.id.activity_show_team_add_member);
        layoutToShow.setVisibility(View.VISIBLE);
        final FutureCallback<JsonArray > arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                for (int i = 0; i < result.size(); i++) {
                    mListUserEmail.add(result.get(i).getAsJsonObject().get("email").getAsString());
                    mListUserImage.add(result.get(i).getAsJsonObject().get("profilePicture").getAsString());
                    mListUserName.add(result.get(i).getAsJsonObject().get("name").getAsString());

                }
                ListView list;
                CustomListAdapter adapter = new CustomListAdapter(ShowTeamActivity.this, mListUserName, mListUserImage);
                list = findViewById(R.id.activity_show_team_listview_add_member);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        layoutToShow.setVisibility(View.INVISIBLE);
                        JsonObject name = new JsonObject();
                        name.addProperty("name", mListUserName.get(position));
                        addRequestTeam(name.get("name"));


                    }
                });
            }
        };

        ApiService api = new ApiService();
        api.downloadUsers(ShowTeamActivity.this, arreglo);

    }

    private void addRequestTeam(JsonElement jasonArray) {
        JsonObject json = new JsonObject();
        json.addProperty("name", teamToShow.getmName());
        json.addProperty("image", teamToShow.getmImage());
        json.addProperty("type", teamToShow.getmType());
        json.addProperty("sport", teamToShow.getmSport());
        json.add("member", teamToShow.getMember());
        json.add("request", jasonArray);

        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                Toast.makeText(getApplicationContext(), "Se le ha enviado un notificacion a usuario", Toast.LENGTH_SHORT).show();

            }
        };
        ApiServiceTeams api = new ApiServiceTeams();
        api.updateTeam(ShowTeamActivity.this, arreglo, json, teamToShow.getId());
    }
}
