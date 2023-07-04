package com.example.sqlite_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editName, editSurname, editMarks,editid;
    Button btn, viewdata, updatedata, delete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB=new DatabaseHelper(this);

        editName=(EditText) findViewById(R.id.name);
        editSurname=(EditText) findViewById(R.id.surname);
        editMarks=(EditText) findViewById(R.id.marks);
        btn=(Button) findViewById(R.id.button);
        viewdata=(Button) findViewById(R.id.viewdata);
        updatedata=(Button) findViewById(R.id.update);
        editid=(EditText) findViewById(R.id.ido);
        delete=(Button) findViewById(R.id.deleteb);
        AddData();
        viewAll();
        updateData();
        Deletedata();
    }

    public void Deletedata()
    {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDB.DeleteData(editid.getText().toString());
                if(deletedRows >0 )
                {
                    Toast.makeText(MainActivity.this, "data is deleted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "data is not deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateData()
    {
        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDB.updateData(editid.getText().toString(),editName.getText().toString()
                        ,editSurname.getText().toString(),editMarks.getText().toString());
                if (isUpdate==true)
                {
                    Toast.makeText(MainActivity.this, "data updated", Toast.LENGTH_SHORT).show();
                    editName.setText(null);
                    editSurname.setText(null);
                    editMarks.setText(null);
                }
                else
                    Toast.makeText(MainActivity.this, "data not updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddData()
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isInserted= myDB.insertData(editName.getText().toString(),editSurname.getText().toString(),
                        editMarks.getText().toString());
               
               if(isInserted == true){
                   Toast.makeText(MainActivity.this, "DAta is inserted", Toast.LENGTH_SHORT).show();
                   editName.setText(null);
                   editSurname.setText(null);
                   editMarks.setText(null);
               }
               else
                   Toast.makeText(MainActivity.this, "Data is nt saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void viewAll()
    {
        viewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res= myDB.getAllData();
                if (res.getCount()==0)
                {
                    Toast.makeText(MainActivity.this, "No data is avalival", Toast.LENGTH_SHORT).show();
                    showmsg("Error","Nothing found");
                    return;
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append("ID :"+res.getString(0)+"\n");
                        buffer.append("Name :"+res.getString(1)+"\n");
                        buffer.append("Surname :"+res.getString(2)+"\n");
                        buffer.append("Marks :"+res.getString(3)+"\n\n\n");
                    }
                    //show all data
                    showmsg("data",buffer.toString());
                }
            }
        });

    }

    public void showmsg(String title, String Message){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}