package com.sportec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sportec.Dependences.News;
import com.sportec.R;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //obtengo la informacion de la noticia pasandolo por GSON
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("News");
        News newsToShow = gson.fromJson(strObj, News.class);

        //se define los elementos de la pantalla utilizar
        TextView textTitle = findViewById(R.id.activity_news_title);
        TextView textContent = findViewById(R.id.activity_news_content);
        ImageView imageNews = findViewById(R.id.activity_news_image);

        //se le asigna a cada elemento la informacion correspondiente
        textTitle.setText(newsToShow.getTitle());
        textContent.setText(newsToShow.getContent());
        Picasso.get().load(newsToShow.getImage()).fit().centerInside().into(imageNews);

    }

    public void goBack(View view) {
        finish();
    }
}
