package com.example.skywalker.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.skywalker.service.ChatHeadService;


public class ChatHeadsClone extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
    }

    public void startService(View view){
        Button button = (Button) view;
        button.setText("Service Started ...");
        startService(new Intent(this, ChatHeadService.class));
        button.setEnabled(false);
    }
}
