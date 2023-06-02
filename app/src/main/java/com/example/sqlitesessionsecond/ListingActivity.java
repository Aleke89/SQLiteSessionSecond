package com.example.sqlitesessionsecond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListingActivity extends AppCompatActivity implements ItemClick {
    private ItemClick itemClick;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;

    private DBHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        databaseHelper = new DBHelper(this);

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, this, itemList);
        recyclerView.setAdapter(itemAdapter);
//        databaseHelper.insertData(R.drawable.event,"First Listing Title","10/10/2022");
//        databaseHelper.insertData(R.drawable.icon,"Second Listing Title","18/08/2022");
//        databaseHelper.insertData(R.drawable.postoffice,"Third Listing Title","10/01/2022");
//        databaseHelper.insertData(R.drawable.sitting,"Fourth Listing Title","25/02/2022");
//        databaseHelper.insertData(R.drawable.event,"Fifth Listing Title","11/01/2022");
        retrieveData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Close the database connection
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
    private void retrieveData()
    {
        // Get a readable database instance
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Perform a query to retrieve all rows from the table
        String selectQuery = "SELECT * FROM " + DBHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Check if there are any rows in the cursor
        if (cursor.getCount() > 0) {
            // Iterate over the cursor and add items to the list
            while (cursor.moveToNext()) {
                @SuppressLint("Range") byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_IMAGE));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
                @SuppressLint("Range")  String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));

                // Convert the byte array back to a Bitmap
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                // Create an Item object and add it to the list
                Item item = new Item(imageBitmap, title, date);
                itemList.add(item);
            }

            // Notify the adapter that the data has changed
            itemAdapter.notifyDataSetChanged();
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();
    }

    @Override
    public void itemClick(int position) {
        Intent intent = new Intent(ListingActivity.this, SelectedListingActivity.class);
        intent.putExtra("title", itemList.get(position).getTitle());
        intent.putExtra("date", itemList.get(position).getDate());
        startActivity(intent);
    }
}