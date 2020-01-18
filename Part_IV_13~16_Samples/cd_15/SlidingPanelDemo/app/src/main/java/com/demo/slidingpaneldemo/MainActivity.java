package com.demo.slidingpaneldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    SlidingPanel panel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panel=(SlidingPanel)findViewById(R.id.panel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplication())
                .inflate(R.menu.option, menu);

        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.toggle) {
            panel.toggle();

            return(true);
        }

        return(super.onOptionsItemSelected(item));
    }
}
