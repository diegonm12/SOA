package com.sportec.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.sportec.Dependences.CustomListAdapter;
import com.sportec.Dependences.News;
import com.sportec.Dependences.Team;
import com.sportec.R;
import com.sportec.Service.ApiServiceNews;
import com.sportec.Service.ApiServiceTeams;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    public static String mSportSelected;
    public static List<Team> mListTeams = new ArrayList<>();
    public static List<String> mListNameTeam = new ArrayList<>();
    public static List<String> mListImageTeam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        //traigo el nombre del deporte del cual se esta hablando
        mSportSelected = getIntent().getStringExtra("sportSelected");

        //se trae los equipos de la base de datos
        getTeamsFromDB();
    }

    //en este metodo se agarran todos teams de la base de datos segun sea el deporte
    // que se eligio.
    private void getTeamsFromDB() {
        final FutureCallback<JsonArray> arreglo = new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                for (int i = 0; i < result.size(); i++) {
                    Team teamResult = new Team();
                    teamResult.setmName(result.get(i).getAsJsonObject().get("name").getAsString());
                    teamResult.setmImage(result.get(i).getAsJsonObject().get("image").getAsString());
                    teamResult.setmType(result.get(i).getAsJsonObject().get("type").getAsString());
                    teamResult.setmSport(result.get(i).getAsJsonObject().get("sport").getAsString());
                    teamResult.setMemeber(result.get(i).getAsJsonObject().get("member").getAsJsonArray());
                    teamResult.setId(result.get(i).getAsJsonObject().get("_id").getAsString());
                    mListTeams.add(teamResult);
                    mListImageTeam.add(teamResult.getmImage());
                    mListNameTeam.add(teamResult.getmName());
                }
                ListView list;
                CustomListAdapter adapter = new CustomListAdapter(TeamActivity.this, mListNameTeam, mListImageTeam);
                list = findViewById(R.id.activity_team_list);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String Slecteditem = mListNameTeam.get(+position);
                        Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                        //se inicializa el Gson para la infromacion del equipo
                        Gson gsonTeamSelected = new Gson();

                        Team teamToSend = mListTeams.get(position);

                        //se hace el intent a la vista del team.
                        Intent intentShowTeam = new Intent(TeamActivity.this, ShowTeamActivity.class);
                        intentShowTeam.putExtra("Team", gsonTeamSelected.toJson(teamToSend));
                        startActivity(intentShowTeam);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    }
                });
            }
        };
        if(mSportSelected.matches("Futbol Americano")){
            ApiServiceTeams api = new ApiServiceTeams();
            api.downloadTeamsBySport(this, arreglo, "Americano");

        }
        else{
            ApiServiceTeams api = new ApiServiceTeams();
            api.downloadTeamsBySport(this, arreglo, mSportSelected);
        }

    }

    // me salgo de la pantalla se limpian las listas
    public void goBack(View view) {
        mListNameTeam.clear();
        mListTeams.clear();
        mListImageTeam.clear();
        finish();
    }
}

