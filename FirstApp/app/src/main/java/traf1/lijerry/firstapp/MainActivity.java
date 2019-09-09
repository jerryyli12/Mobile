package traf1.lijerry.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void clicked(View view){
        System.out.println("Hi mom");
        //TextView textView = (TextView) findViewById(R.id.my_textview);
        //textView.setText("Button pressed");
    }
}
