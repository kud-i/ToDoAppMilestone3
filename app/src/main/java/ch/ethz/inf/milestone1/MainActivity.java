package ch.ethz.inf.milestone1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ch.ethz.inf.milestone1.io.IO;


public class MainActivity extends ActionBarActivity {


    private RecyclerView mRecyclerView;
    private ArrayList mDataset;
    private RecyclerView.Adapter mAdapter;
    private IO io;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        io = new IO();
        mDataset = io.LoadData(getBaseContext());

        // Specify an adapter
        mAdapter = new ToDoAdapter(mDataset, this);
        mRecyclerView.setAdapter(mAdapter);


        //FloatingButton Setup
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Result from ToDoActivity

        if (resultCode == RESULT_OK){

            if (requestCode == 0){
                //Create
                mDataset.add(data.getStringExtra("title"));
                mAdapter.notifyDataSetChanged();

            }else if (requestCode == 1){
                //Edit
                int position = data.getIntExtra("position",0);
                mDataset.set(position, data.getStringExtra("title"));
                mAdapter.notifyItemChanged(position);
            }
        }

        io.WriteData(mDataset, getBaseContext());
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
