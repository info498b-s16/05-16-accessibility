package edu.uw.accessdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "**Second**";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //action bar "back"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String message = extras.getString(MainActivity.EXTRA_MESSAGE);
            TextView subtitle = (TextView) findViewById(R.id.txtSecond);
            subtitle.setText("Received: " + message);
        }
    }
}
