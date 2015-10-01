package com.dirinc.randomnumbergenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GetButtonActivity extends AppCompatActivity {
    public Button apply1;
    public Boolean isApply1;

    public GetButtonActivity() {
        // Initialize the button(s)
        apply1 = (Button) findViewById(R.id.button1);
        isApply1 = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_button);
    }

    public void apply1() {
        apply1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isApply1 = true;
                setButton();
            }
        });
    }

    public void setButton() {
        if(isApply1) {
            Toast.makeText(this, "Better Button 1 Applied!", Toast.LENGTH_SHORT).show();
        }
    }
}
