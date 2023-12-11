package com.example.library_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    MaterialToolbar mtbMain;
    RecyclerView rvMain;
    ArrayList<LibEvent> eventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtbMain = findViewById(R.id.mtbMain);
        rvMain = findViewById(R.id.rvMain);

        findViewById(R.id.fabCreateEvent).setOnClickListener(v -> {

            Intent create_event_intent = new Intent(MainActivity.this, CreateEvent.class);
            MainActivity.this.startActivity(create_event_intent);

        });

        try (DB_Helper getEventList = new DB_Helper(MainActivity.this)) {
            eventList = getEventList.get_events();
        }

        MainAdapter mainAdapter = new MainAdapter(eventList, this);
        rvMain.setAdapter(mainAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                RecyclerView.VERTICAL, false);
        rvMain.setLayoutManager(linearLayoutManager);

        setSupportActionBar(mtbMain);
        mtbMain.showOverflowMenu();

    }


    // Create Toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    // Check what item in Toolbar menu was selected (but there is only one in our case)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.remove_events) {
            //call method to delete all contacts

            try (DB_Helper remove_all = new DB_Helper(MainActivity.this)) {
                remove_all.remove_all_events();
            }

            //reload the activity
            recreate();

        } else{
            return false;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onItemClick(int position) {

    }

}
