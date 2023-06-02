package com.example.sqlitesessionsecond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitesessionsecond.databinding.ActivitySelectedListingBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectedListingActivity extends AppCompatActivity implements ItemClick {
    ActivitySelectedListingBinding binding;
    ArrayAdapter<String> adapterItems;
    private PropertyAdapter propertyAdapter;
    private List<Property> itemList;
    String dateFrom = "";
    private DBHelper databaseHelper;
    Bitmap bitmap;
    AutoCompleteTextView drpWeekend, drp_holiday, drp_other;
    String[] dropDownItems = {"Easy Rules", "Hard Rules", "Something Else", "Adjara Gudju", "Costa Riko"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectedListingBinding.inflate(getLayoutInflater());
        databaseHelper = new DBHelper(this);
        setContentView(binding.getRoot());
        binding.recylerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        propertyAdapter = new PropertyAdapter(this, getApplicationContext(), itemList);
        drpWeekend = findViewById(R.id.drp_weekend);
        drp_holiday = findViewById(R.id.drp_holidays);
        drp_other = findViewById(R.id.drp_other);
        adapterItems = new ArrayAdapter<>(this, R.layout.rule_item, dropDownItems);
        drpWeekend.setAdapter(adapterItems);
        drp_holiday.setAdapter(adapterItems);
        drp_other.setAdapter(adapterItems);
        binding.recylerView.setAdapter(propertyAdapter);


        drpWeekend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        drp_holiday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        drp_other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recylerView);

        binding.btnUpWeekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up(binding.txtOnTheWeekendCounter);
            }
        });
        binding.btnUpHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up(binding.txtOnTheHolidaysCounter);
            }
        });
        binding.btnUpOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up(binding.txtOnTheOtherCounter);
            }
        });

        binding.btnDownWeekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(binding.txtOnTheWeekendCounter);
            }
        });
        binding.btnDownHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(binding.txtOnTheHolidaysCounter);
            }
        });
        binding.btnDownOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(binding.txtOnTheOtherCounter);
            }
        });
        binding.btnSetPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.txtOnTheWeekendCounter.getText().equals("0") || binding.txtOnTheHolidaysCounter.getText().equals("0") || binding.txtOnTheOtherCounter.getText().equals("0") || drpWeekend.getText().toString().equals("") || drp_other.getText().toString().equals("") || drp_holiday.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Change Counters Value and Select Rules", Toast.LENGTH_SHORT).show();

                } else {
                    String count = binding.txtOnTheHolidaysCounter.getText().toString();
                    String count2 = binding.txtOnTheWeekendCounter.getText().toString();
                    String count3 = binding.txtOnTheOtherCounter.getText().toString();
                    int counter = Integer.parseInt(count);
                    counter+= Integer.parseInt(count2);
                    counter+= Integer.parseInt(count3);
                    // Get the new values for updating the data
                    byte[] newImageBytes = databaseHelper.getByteArrayFromBitmap(bitmap);
                    String newDate = binding.edtFrom.getText().toString();
                    String newPrice = "$ " + counter;
                    String newRule = drpWeekend.getText().toString();

                    // Call the updatePropertyData method from DBHelper to update the data in the table
                    databaseHelper.updatePropertyData(newImageBytes, newDate, newPrice, newRule);

                    Intent intent = new Intent(SelectedListingActivity.this, ListingActivity.class);
                    startActivity(intent);
                }
            }
        });

        retrieveData();
    }

    private void retrieveData() {
        // Get a readable database instance
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Perform a query to retrieve all rows from the table
        String selectQuery = "SELECT * FROM " + DBHelper.SELECTED_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Check if there are any rows in the cursor
        if (cursor.getCount() > 0) {
            // Iterate over the cursor and add items to the list
            while (cursor.moveToNext()) {
                @SuppressLint("Range") byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_IMAGE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRICE));
                @SuppressLint("Range") String rule = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_RULE));

                // Convert the byte array back to a Bitmap
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                // Create an Item object and add it to the list
                Property item = new Property(imageBitmap, date, price, rule);
                itemList.add(item);
            }

            // Notify the adapter that the data has changed
            propertyAdapter.notifyDataSetChanged();
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();
    }


    @Override
    public void itemClick(int position) {

    }

    private void up(TextView txt) {
        int num = Integer.parseInt((String) txt.getText());
        num += 1;
        txt.setText(String.valueOf(num));
    }

    private void down(TextView txt) {
        int num = Integer.parseInt((String) txt.getText());
        num -= 1;
        txt.setText(String.valueOf(num));
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    itemList.remove(position);

                    propertyAdapter.notifyDataSetChanged();
                    break;
                case ItemTouchHelper.RIGHT:
                    dateFrom = itemList.get(position).getDate();
                    bitmap = itemList.get(position).getImage();
                    binding.edtFrom.setText(dateFrom);
                    binding.edtTo.setText(dateFrom);
                    propertyAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}