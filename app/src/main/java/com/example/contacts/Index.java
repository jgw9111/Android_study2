package com.example.contacts;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        Context _this = Index.this;
        findViewById(R.id.moveLogin).setOnClickListener((v)->{
            SQLiteHelper helper = new SQLiteHelper(_this);
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
                    " CREATE TABLE IF NOT EXISTS %s " +
                            " ( %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT " +
                            ")",MEMBERS,MSEQ,MNAME,MPW,MEMAIL,MPHONE,MADDR,MPHOTO
            );
            Log.d("실행할 쿼리",sql);
            db.execSQL(sql);
            Log.d("================","create 쿼리 실행완료");
            /* String[] names = {"강동원","윤아","김도연","박보검","이지은"};
            String[] emails = {"kangdw","yun-a","doyeon","bogum","leeji"};
            for (int i=0;i<names.length;i++){
                Log.d("입력될 이름",names[i]);
                Log.d("입력될 이메일",emails[i]);

                db.execSQL(String.format(" INSERT INTO %s " +
                    "(%s ," +
                    " %s ," +
                    " %s ," +
                    " %s ," +
                    " %s ," +
                    " %s " +
                    ")VALUES(" +
                    " '%s', " +
                    " '%s', " +
                    " '%s', " +
                    " '%s', " +
                    " '%s', " +
                    " '%s' )",MEMBERS,MNAME,MPW,MEMAIL,MPHONE,MADDR,MPHOTO,
                    names[i],
                    "1",
                    emails[i]+"@test.com",
                    "010-1234-445"+(i+1),
                    "신촌"+(i+1)+"길",
                    "default_photo"+(i+1)));
            }
            */
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
