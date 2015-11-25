package ch.ethz.inf.milestone1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ToDoActivity extends AppCompatActivity {

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        Intent intent = getIntent();

        int i = intent.getIntExtra("position",-1);

        if (i >= 0) {
            //EDIT Mode
            setTitle(getString(R.string.ChangeToDo));
            mPosition = i;
            TextView title = (TextView) findViewById(R.id.editTitleText);

            title.setText(intent.getStringExtra("title"));
        }else{
            //Create mode
            setTitle(getString(R.string.NewToDo));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            TextView titleTextView = (TextView) findViewById(R.id.editTitleText);

            String title = titleTextView.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("position", mPosition);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

            super.onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
