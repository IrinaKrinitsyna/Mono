package com.example.i78k.mono;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i78k.mono.model.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    protected ArrayAdapter<Room> adapter; //ClipData???

    protected ListView listview;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new ArrayAdapter<>(MainActivity.this,R.layout.main);
                listview = (ListView) findViewById(R.id.listview);
                listview.setAdapter(adapter);
                adapter.clear();

                /*EditText Text = (EditText) findViewById(R.id.edittext);
                String User = Text.getText().toString();
                getreport(User);*/

                shourooms(createDemodata());
            }
        });
    }


    public List <Room> createDemodata () {
        List <Room> Rooms = new ArrayList<>();

        for ( int i=0; i<5; i++)
        {
            Rooms.add(new Room("Комната "+ i,""));
        }
        return Rooms;
    }


/*    private void getreport(String User) {
        *//* некоторое шаманство, которое мы не пониммаем >>>>> *//*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        *//* некоторое шаманство закончилось, мы получили объект, позволяющий делать http-запросы *//*

        Call<List<Repo>> repos = service.listRepos(User);
        Toast.makeText(this, "Запрос начался", Toast.LENGTH_SHORT);
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful()) {

                    adapter = new ArrayAdapter<>(MainActivity.this, R.layout.post_item);
                    listview = (ListView) findViewById(R.id.listview);
                    listview.setAdapter(adapter);

                    for (int i = 0; i < response.body().size(); i++) {
                        adapter.add(response.body().get(i).full_name + "\n" +
                                response.body().get(i).created_at + " / " + response.body().get(i).updated_at);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Не найдено", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void shourooms(List <Room> rooms) {

        adapter = new MonoAdapter(MainActivity.this);
        listview = (ListView) findViewById(R.id.listview);


        for (int i = 0; i < rooms.size(); i++) {
            adapter.add(rooms.get(i)); //TODO вывести список времен
        }
    listview.setAdapter(adapter);
    }
    public class MonoAdapter extends ArrayAdapter<Room>  {

        public MonoAdapter(Context context) {

            super(context, R.layout.post_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Room room = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.post_item, null);
            }
            ((TextView) convertView.findViewById(R.id.textV1))
                    .setText(room.name);
            ((TextView) convertView.findViewById(R.id.textV2))
                    .setText(room.sTiming);
            return convertView;
        }
    }

}
