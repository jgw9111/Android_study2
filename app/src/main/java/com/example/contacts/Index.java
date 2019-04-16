package com.example.contacts;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        final Context _this = Index.this;
        findViewById(R.id.moveLogin).setOnClickListener((v)->{

            Toast.makeText(_this, "인증완료", Toast.LENGTH_LONG).show();
            // Intent i = new Intent(_this,Login.class); // from this to Login
            startActivity(new Intent(_this,Login.class));


        });
    }
    static class Member{int seq; String name,pw,email,phone,addr,photo;}
    static interface IRunnable{public void run();}
    static interface ISupplier{public Object get();}
    static interface IConsumer{public void accept(Object o);}
    static interface IFunction{public Object apply(Object o);}
    static interface IPredicate{public boolean test(Object o);}

    static String DBNAME = "contacts.db";
    static String MEMBERS = "MEMBER";
    static String MNAME = "NAME";
    static String MSEQ = "SEQ";
    static String MPW = "PW";
    static String MEMAIL = "EMAIL";
    static String MPHONE = "PHONE";
    static String MADDR = "ADDR";
    static String MPHOTO = "PHOTO";

    static abstract class QueryFactory{
        Context _this;
        public QueryFactory(Context _this) {
            this._this = _this;

        }
        public abstract SQLiteDatabase getDatabase();
    }
    static class SQLiteHelper extends SQLiteOpenHelper{

        public SQLiteHelper(Context context) {
            super(context, DBNAME, null, 1); //내장 DB가 아닌 내가 만든 DB임
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    "CREATE TABLE IF NOT EXISTS %s" +
                            " ( %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            ")",MEMBERS,MSEQ,MNAME,MPW,MEMAIL,MPHONE,MADDR,MPHOTO
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
