package com.myapplicationdev.android.p13_taskmanagerwear;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

public class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        DBHelper dbh = new DBHelper(ReplyActivity.this);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");
        }

        if(reply != null && reply.toString().equalsIgnoreCase("Completed")){
            Intent i = getIntent();
            Task task = (Task) i .getSerializableExtra("task");
            if(task != null){
                Toast.makeText(ReplyActivity.this, "You have indicated: " + reply, Toast.LENGTH_SHORT).show();
                dbh.deleteTask(task.getId());
            }


            //if(reply == "Done"){
           //     DBHelper db = new DBHelper(ReplyActivity.this);
             //   db.deleteTask(remoteInput.getBundle());
            //    Intent i = new Intent();
           //     setResult(RESULT_OK, i);
           //     finish();
          //  }

        }

    }
}
