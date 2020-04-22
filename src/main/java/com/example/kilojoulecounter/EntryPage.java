package com.example.kilojoulecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.print.PrinterId;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EntryPage extends AppCompatActivity {

    private TextView data;
    private DatabaseHelper database;
    private Cursor cursor;
    private Button buttonNext,buttonPrev;
    private static final String Tag="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_page);
        Intent intent =getIntent();
        String date = intent.getStringExtra("ChosenDate");
        String FCT = intent.getStringExtra("FCT");
        String ECT =intent.getStringExtra("ECT");
        String NETT =intent.getStringExtra("NET");
        String noAdd=intent.getStringExtra("NoAdd");
        //Log.d(Tag,"date:"+date+"fct:"+FCT+"ECT"+ECT+"NETT"+NETT);

        data = findViewById(R.id.show_entry);
        String x ="\n"+"Date: "+date +"\n"+"Food Category Total :"+FCT+" kJ"+"\n"+"Exercise Category Total:  "+ECT+" kJ"+"\n"+"Nett Total: "+NETT;
        data.setText(x);
        database = new DatabaseHelper(this);
        //String NET = ((Integer.valueOf("4"))-(Integer.valueOf("45")))+"";
        addItems(date,FCT,ECT,NETT,noAdd);
        Runnable r =new Runnable() {
            @Override
            public void run() {
                cursor =database.getListContents();
            }
        };
        Thread thread2 =new Thread(r);
        thread2.start();
        buttonNext= findViewById(R.id.nxtButton);
        buttonPrev= findViewById(R.id.prevButton);
    }

    public void onHomeClick(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onCalculator(View view)
    {
        Intent intent = new Intent(this,Calculator.class);
        startActivity(intent);
    }

    public void addItems(String date,String fct,String ect,String net,String noAdd)
    {

        boolean insertData= false;
        if(noAdd==null)
        {
            insertData =database.addData(date,fct,ect,net);
        }

        if(insertData==true){
            Toast toast =Toast.makeText(this,"Saved",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void onPrevButton(View view)
    {//Log.d(Tag,"mike");
        if(cursor.moveToPrevious())
        {
            data = findViewById(R.id.show_entry);
            String x ="\n"+"Date: "+cursor.getString(1)+"\n"+"Food Category Total: "+ cursor.getString(2)+" kJ"+"\n"+"Exercise Category Total: "+cursor.getString(3)+" kJ"+"\n"+"Nett Total(NKI): "+cursor.getString(4);
            data.setText(x);
            if(!buttonNext.isClickable()){buttonNext.setClickable(true);}
        }
        else
        {

            buttonPrev.setClickable(false);
        }
    }

    public void onNextButton(View view)
    {
        Log.d(Tag,"next");
        if(cursor.moveToNext())
        {
            data = findViewById(R.id.show_entry);
            String x ="\n"+"Date: "+cursor.getString(1)+"\n"+"Food Category Total: "+ cursor.getString(2)+" kJ"+"\n"+"Exercise Category Total: "+cursor.getString(3)+" kJ"+"\n"+"Nett Total(NKI): "+cursor.getString(4);
            data.setText(x);
            if(!buttonPrev.isClickable()){buttonPrev.setClickable(true);}
        }
        else
        {

            buttonNext.setClickable(false);
        }
    }

}
