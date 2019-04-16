package com.example.contacts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        Context _this = MemberList.this;
        ItemList query = new ItemList(_this);
        ListView memberList = findViewById(R.id.memberList);

        memberList.setAdapter(
                new MemberAdapter(_this,(ArrayList<Index.Member>)new Index.ISupplier() {
                    @Override
                    public Object get() {
                        return query.get();
                    }
                }.get()) //선언과 동시에 실행후 값이 사라짐
        );

    }
    private class MemberListQuery extends Index.QueryFactory{
        SQLiteOpenHelper helper;
        public MemberListQuery(Context _this) {
            super(_this);
            helper = new Index.SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }

    }
    private class ItemList extends MemberListQuery{

        public ItemList(Context _this) {
            super(_this);
        }
        public ArrayList<Index.Member> get(){
            ArrayList<Index.Member> list = new ArrayList<>();
            Cursor c = this.getDatabase().rawQuery(
                    "  SELECT * FROM MEMBER ",null
            );
            Index.Member m = null;
            if(c!=null){
                while (c.moveToNext()){
                    m = new Index.Member();
                    m.seq = Integer.parseInt(c.getString(c.getColumnIndex(Index.MSEQ)));
                    m.name = c.getString(c.getColumnIndex(Index.MNAME));
                    m.pw = c.getString(c.getColumnIndex(Index.MPW));
                    m.email = c.getString(c.getColumnIndex(Index.MEMAIL));
                    m.addr = c.getColumnName(c.getColumnIndex(Index.MADDR));
                    m.phone = c.getColumnName(c.getColumnIndex(Index.MPHONE));
                    m.photo = c.getColumnName(c.getColumnIndex(Index.MPHOTO));
                    Log.d("멤버정보 : \n",m.name);
                    list.add(m);
                }
            }else {
                Toast.makeText(_this,"등록된 회원이 없음",Toast.LENGTH_LONG).show();
            }
            return list;
        }

    }
    private class MemberAdapter extends BaseAdapter{
        ArrayList<Index.Member> list;
        LayoutInflater inflater;
        Context _this;

        public MemberAdapter(Context _this,ArrayList<Index.Member> list) {
            this.list = list;
            this._this = _this;
            this.inflater =LayoutInflater.from(_this);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v = inflater.inflate(R.layout.member_item,null); //member_item 에 infacter
                holder = new ViewHolder();
                holder.photo = v.findViewById(R.id.profile);
                holder.phone = v.findViewById(R.id.phone);
                holder.name = v.findViewById(R.id.name);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
            }
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView photo;
        TextView name,phone;
    }

    /*static abstract class QueryFactory{
        Context _this;
        public QueryFactory(Context _this){
            this._this = _this;
        }
        public abstract SQLiteDatabase getDatabase();
    }
    static class SQLiteHelper extends SQLiteOpenHelper{

        public SQLiteHelper(Context context) {
            super(context, Index.DBNAME, null, 1);
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.rawQuery(String.format(
                    " SELECT * FROM %s ",Index.MEMBERS
            ),null);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
     }
    */


}
