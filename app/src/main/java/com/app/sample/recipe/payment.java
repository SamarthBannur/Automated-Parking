package com.app.sample.recipe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Toast[] toast = new Toast[1];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        Button btnconfirm = (Button) findViewById(R.id.confirm);
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to register screen");
            System.out.println("confirm button clicked!");



                Context context = getApplicationContext();
                CharSequence text = "Booked Succesfully!!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                Toast.makeText(context, text, duration).show();




            }
        });

    }
}
