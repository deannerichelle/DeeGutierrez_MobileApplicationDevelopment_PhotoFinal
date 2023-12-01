package com.zybooks.deegutierrez_photofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    DatabaseHelper databaseHelper;

    EditText editTextTag;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTextTag = findViewById(R.id.editTextTag);
        btnSearch = findViewById(R.id.btnSearch);

        databaseHelper = new DatabaseHelper(this);

        // Get data from the database
        List<PhotoModel> photoList = databaseHelper.getAllPhotos();

        if (photoList.isEmpty()) {
            Toast.makeText(this, "No photos found", Toast.LENGTH_SHORT).show();
        } else {
            // Set up RecyclerView Adapter
            photoAdapter = new PhotoAdapter(this, photoList);
            recyclerView.setAdapter(photoAdapter);
        }

        // Set up the click listener for the search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByTag();
            }
        });
    }

    // This is the searchbytag function
    // so the user can search for a specific
    // picture by typing a tag
    private void searchByTag() {
        String tag = editTextTag.getText().toString().trim();

        if (!tag.isEmpty()) {
            List<PhotoModel> searchResults = databaseHelper.getPhotosByTag(tag);

            if (searchResults.isEmpty()) {
                Toast.makeText(this, "No matching photos found", Toast.LENGTH_SHORT).show();
            } else {
                // Update RecyclerView with search results
                photoAdapter.setData(searchResults);
                photoAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Please enter a tag", Toast.LENGTH_SHORT).show();
        }
    }
}
