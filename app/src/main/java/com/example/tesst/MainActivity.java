package com.example.tesst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edthoten, edttenlop, edtquequan, edtmaso;
    Button btninsert, btndelete, btnupdate, btnquery,btnlogout;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edthoten = findViewById(R.id.edthoten);
        edttenlop = findViewById(R.id.edttenlop);
        edtquequan = findViewById(R.id.edtquequan);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
        btnlogout = findViewById(R.id.btnlogout);
        edtmaso = findViewById(R.id.edtmaso);
        //Tạo listview
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylist);
        lv.setAdapter(myadapter);
        //Tao va mo co so du lieu sqlite
        mydatabase = openOrCreateDatabase("qlsinhvien.db",MODE_PRIVATE,null);
        //Tao table chua du lieu
        try {
            String sql = " CREATE TABLE tbllop(maso TEXT primary key ,hoten TEXT, tenlop TEXT, quequan TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception e){
            Log.e("Error","Table da ton tai");
        }
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maso = edtmaso.getText().toString();
                String hoten = edthoten.getText().toString();
                String tenlop = edttenlop.getText().toString();
                String quequan = edtquequan.getText().toString();
                //tao ban ghi truoc khi chen vao table
                ContentValues myvalue = new ContentValues();
                myvalue.put("maso",maso);
                myvalue.put("hoten",hoten);
                myvalue.put("tenlop",tenlop);
                myvalue.put("quequan",quequan);
                String msg ="";
                if(maso == null){
                    msg = "insert khong thanh cong";
                }
                else{
                    if(mydatabase.insert("tbllop",null,myvalue)== -1)
                    {
                        msg = "insert khong thanh cong";
                    }
                    else{
                        msg ="insert thanh cong ";
                    }
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maso =edtmaso.getText().toString();
                int n = mydatabase.delete("tbllop","maso = ?",new String[]{maso});
                String msg = "";
                if(n == 0)
                {
                    msg =" Xoa khong thanh cong";
                }
                else {
                    msg = n + "ho so da xoa";
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maso = edtmaso.getText().toString();
                String hoten = edthoten.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("maso",maso);
                int n = mydatabase.update("tbllop",myvalue,"maso = ?", new String[]{maso});
                String msg = "";
                if(n ==0 ){
                    msg = " khong the update";
                }
                else{
                    msg = n + " sv duoc update";
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor c = mydatabase.query("tbllop",null,null,null,null,null,null);
                c.moveToNext();
                String data ="";
                while (c.isAfterLast() == false){
                    data =  c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2);
                    c.moveToNext();
                    mylist.add(data);
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });
    }
}