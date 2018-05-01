package com.app.sample.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Activity;
import java.util.Calendar;

public class book_out_time extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        //Get a new instance of Calendar
        final Calendar c= Calendar.getInstance();
        int hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
        int minute = c.get(c.MINUTE); //Current Minute
        int second = c.get(c.SECOND); //Current Second

        //Get the widgets reference from XML layout
        final TextView tv = (TextView) findViewById(R.id.tv);
        TimePicker tp = (TimePicker) findViewById(R.id.tp);

        //Display the TimePicker initial time
        tv.setText("out Time\nH:M:S | " + hourOfDay + ":" + minute + ":" + second);

        //Set a TimeChangedListener for TimePicker widget
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                tv.setText("Time changed\nH:M | "+hourOfDay + ":" + minute);
            }
        });


        Button btnnext = (Button) findViewById(R.id.next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to register screen");
                Intent intent = new Intent(book_out_time.this,payment.class);
                startActivity(intent);







            }
        });

    }
}
