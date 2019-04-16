package com.example.contacts;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Context _this = Login.this;
        findViewById(R.id.loginBtn).setOnClickListener((v)->{
            Toast.makeText(_this, "로그인버튼 클릭", Toast.LENGTH_LONG).show();
            EditText userID = findViewById(R.id.userID);
            EditText password = findViewById(R.id.password);

            ItemExist ie = new ItemExist(_this);
            ie.id = userID.getText().toString();
            ie.pw = password.getText().toString();
            new Index.IConsumer() {
                @Override
                public void accept(Object o) {
                    if(ie.test()){
                        startActivity(new Intent(_this,MemberList.class));
                        Toast.makeText(_this, "로그인 성공", Toast.LENGTH_LONG).show();

                    }else{
                        startActivity(new Intent(_this,Login.class));
                        Toast.makeText(_this, "로그인 실패", Toast.LENGTH_LONG).show();
                    }
                }
            }.accept(null);


        });
    }
    private class LoginQuery extends Index.QueryFactory{
        SQLiteOpenHelper helper;
        public LoginQuery(Context _this) {
            super(_this); //context
            helper = new Index.SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase(); //읽기전용
        }
    }
    private class ItemExist extends LoginQuery{
        String id,pw;
        public ItemExist(Context _this) {
            super(_this);
        }
        public boolean test(){
            return super.getDatabase()
                    .rawQuery(String.format(
                            " SELECT * FROM %s "+
                            " WHERE %s LIKE '%s' ",
                            Index.MEMBERS,
                            Index.MSEQ,
                            id,
                            Index.MPW,
                            pw
                    ),null).moveToNext();
        }
    }
}
