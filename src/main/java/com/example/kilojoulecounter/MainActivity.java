package com.example.kilojoulecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper database;
    private ListView listView;
    private static final String Tag ="MainActivity";
    private TextView textView;
    private Cursor data;
    private ArrayList<String> theList;
    private  ListAdapter listAdapter;

    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            listView.setAdapter(listAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(this);
        //database.getRid();
        data =database.getListContents();
        listView= findViewById(R.id.listOfItems);
        textView =findViewById(R.id.showAvg);
        displayList();
        //Log.d(Tag,"aaaaa"+computeAvg());
        textView.setText(computeAvg());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String x = String.valueOf(i);
                // Log.d(Tag,"sjsj"+x);
                int a=0;
                Cursor dat =database.getListContents();
                while(a<=i)
                {
                    dat.moveToNext();
                    //Log.d(Tag,"check"+a);
                    a++;
                }
                Log.d(Tag,dat.getString(1));
                String aa= dat.getString(1);
                String b =dat.getString(2);
                String c=dat.getString(3);
                String d=dat.getString(4);
                doShit(aa,b,c,d);

            }
        });
    }

    public void onCalculator(View view)
    {
        Intent intent = new Intent(this,Calculator.class);
        startActivity(intent);
    }

    private void displayList()
    {

        Runnable r =new Runnable() {
            @Override
            public void run() {
                theList =new ArrayList<>();
                while (data.moveToNext())
                {
                    String x= data.getString(1)+"           "+data.getString(4)+" kJ";
                    Log.d(Tag,x);
                    theList.add(x);
                    listAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,theList);
                    handler.sendEmptyMessage(0);
                }
            }
        };
        Thread thread1 =new Thread(r);
        thread1.start();


    }
    private String computeAvg()
    {
        Cursor cursor =database.getListContents();
        int sum=0;
        while (cursor.moveToNext())
        {
            sum+=cursor.getInt(4);
        }
        double div =cursor.getCount();
        if(div==0){return 0+" kJ";}else{
            double m=sum/div;
        return (Math.round(m*100.0)/100.0)+" kJ";}
    }

    private void doShit(String a,String b,String c,String d)
    {
        //Log.d(Tag,"naaaaa");
        String e="no";
        Intent inten = new Intent(MainActivity.this,EntryPage.class);
        //Log.d(Tag,"swswswsws");
        inten.putExtra("ChosenDate",a);
        inten.putExtra("FCT",b);
        inten.putExtra("ECT",c);
        inten.putExtra("NET",d);
        inten.putExtra("NoAdd",e);
        //Log.d(Tag,"2");
        startActivity(inten);
        //Log.d(Tag,"2");
    }

}