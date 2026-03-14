package com.example.employeecontactmanagerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name,id,phone;
    Spinner dept;
    Button save;
    ListView list;

    ArrayList<String> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.etName);
        id=findViewById(R.id.etId);
        phone=findViewById(R.id.etPhone);
        dept=findViewById(R.id.spDept);
        save=findViewById(R.id.btnSave);
        list=findViewById(R.id.listEmployees);

        employees=new ArrayList<>();

        ArrayAdapter<String> deptAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.departments));

        dept.setAdapter(deptAdapter);

        ArrayAdapter<String> listAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        employees);

        list.setAdapter(listAdapter);

        save.setOnClickListener(v -> {

            String data=name.getText().toString()+"\nID: "+id.getText().toString()+
                    "\nDept: "+dept.getSelectedItem().toString();

            employees.add(data);
            listAdapter.notifyDataSetChanged();

        });

        list.setOnItemClickListener((parent, view, position, id1) -> {

            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Select Action")
                    .setItems(new String[]{"Call","SMS","Email"},(dialog,which)->{

                        if(which==0){

                            AlertDialog.Builder confirm=new AlertDialog.Builder(MainActivity.this);

                            confirm.setTitle("Confirm Call")
                                    .setMessage("Do you want to call?")
                                    .setPositiveButton("Call",(d,w)->{

                                        Intent intent=new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:"+phone.getText().toString()));
                                        startActivity(intent);

                                    })
                                    .setNegativeButton("Cancel",null)
                                    .show();
                        }

                        if(which==1){

                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("sms:"+phone.getText().toString()));
                            startActivity(intent);

                        }

                        if(which==2){

                            Intent intent=new Intent(Intent.ACTION_SEND);
                            intent.setType("message/rfc822");
                            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"test@gmail.com"});
                            intent.putExtra(Intent.EXTRA_SUBJECT,"Employee Contact");
                            startActivity(intent);

                        }

                    });

            builder.show();

        });

    }
}
