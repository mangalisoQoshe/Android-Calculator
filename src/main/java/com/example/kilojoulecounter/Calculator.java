package com.example.kilojoulecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Calculator extends AppCompatActivity {

    private static final String Tag ="MainActivity";
    private TextView showdate;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private EditText editBreakfast;
    private EditText editLunch;
    private EditText editGym;
    private EditText editSport;
    private TextView foodTotal;
    private TextView exerciseTotal;
    private TextView netshit;
    private String Sum,foodSum,exerciseSum,net,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        DateThing();

        editBreakfast= findViewById(R.id.editBreakfast);
        editLunch=findViewById(R.id.editLunch);
        editGym=findViewById(R.id.gymId);
        editSport= findViewById(R.id.sportId);


        editLunch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editLunch = findViewById(R.id.editLunch);
                editBreakfast = findViewById(R.id.editBreakfast);
                String y = editBreakfast.getText().toString();
                String x = editLunch.getText().toString();
                if(x.length()<1){x="0";}
                foodSum=sum(x,y);
                String a = foodSum+" kJ";
                foodTotal =findViewById(R.id.Food_total);
                foodTotal.setText(a);



            }
        });
        editSport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editSport =  findViewById(R.id.sportId);
                editGym =  findViewById(R.id.gymId);
                String y = editSport.getText().toString();
                String x = editGym.getText().toString();
                if(y.length()<1){y="0";}
                exerciseSum=sum(x,y);
                exerciseTotal =findViewById(R.id.Exercise_total);
                String a = exerciseSum+" kJ";
                exerciseTotal.setText(a);
                net();
               netshit =findViewById(R.id.net_total);
                netshit.setText(net);

            }
        });


    }
    private  void DateThing()
    {
        showdate =(TextView) findViewById(R.id.Select_date);
        showdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year =cal.get(Calendar.YEAR);
                int month =cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Calculator.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,DateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        DateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                Log.d(Tag, "onDateSet : mm/dd/yyy:"+month+"/"+day+"/"+year);
                date =month+ "/"+day+"/"+year;
                showdate.setText(date);


            }
        };
    }

    public void onSaveEntry(View view)
    {
        Intent intent = new Intent(this,EntryPage.class);
        intent.putExtra("ChosenDate",date);
        intent.putExtra("FCT",foodSum);
        intent.putExtra("ECT",exerciseSum);
        intent.putExtra("NET",net);
        if(!(date==null || foodSum== null ||exerciseSum==null||net==null)) {
            startActivity(intent);
        }
        else {
            Toast toast =Toast.makeText(this,"Not Saved",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,400);
            toast.show();
        }

    }

    public void onBackClick(View view)
    {
        finish();
    }

    private String sum(String num1,String num2)
    {
        int value1 = Integer.valueOf(num1).intValue();
        int value2 = Integer.valueOf(num2).intValue();
        Sum =(value1+value2)+"";
        Log.d(Tag, Sum);
        return Sum;

    }
    private String net()
    {
        net = ((Integer.valueOf(foodSum))-(Integer.valueOf(exerciseSum)))+"";
        return net;
    }

}
