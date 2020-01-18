package com.demo.myrecyclerview_1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        recylerViewLayoutManager =
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(this);

        recyclerView.setAdapter(recyclerViewAdapter);
    }

}