package com.bdlabit.shaqib.jubot.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.bdlabit.shaqib.jubot.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "Food.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts(){
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ID","foodName","quantity","price"};
        String sqlTable = "OrderDetail";
        queryBuilder.setTables(sqlTable);
        Cursor cursor = queryBuilder.query(database,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(new Order(
                        cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("foodName")),
                        cursor.getString(cursor.getColumnIndex("quantity")),
                        cursor.getString(cursor.getColumnIndex("price"))
                ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("INSERT INTO orderDetail(foodName,quantity,price) VALUES('%s','%s','%s');",
                order.getFoodName(),
                order.getFoodQuantity(),
                order.getFoodPrice());
        database.execSQL(query);
    }

    public void removeFromCart(String name){
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM orderDetail WHERE foodName='%s'",name);
        database.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM orderDetail");
        database.execSQL(query);
    }

    public int getCountCart() {
        int count = 0;

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                count = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return count;
    }

    public void updateCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET Quantity= %s WHERE ID = %d",order.getFoodQuantity(),order.getID());
        db.execSQL(query);
    }

    public boolean checkCart(String name){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT EXISTS (SELECT * FROM OrderDetail WHERE foodName='"+name+"'LIMIT 1)";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getInt(0) == 1){
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }
}
