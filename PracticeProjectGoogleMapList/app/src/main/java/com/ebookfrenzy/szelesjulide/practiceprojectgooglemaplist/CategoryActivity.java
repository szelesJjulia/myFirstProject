package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private static CostumAdapter myAdapter;
    static ArrayList<String> listWithCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        listWithCategories = new ArrayList<String>();
        final ListView listViewCategory = (ListView)findViewById(R.id.listViewCategory);

        String categoryArray[] = {"Shopping","People","Medicine","Paying"};

        for (int i = 0; i < categoryArray.length; i++){
            listWithCategories.add(categoryArray[i]);
        }
        myAdapter = new CostumAdapter(listWithCategories,getApplicationContext());
        listViewCategory.setAdapter(myAdapter);

        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String)listViewCategory.getItemAtPosition(position);
                //TODO open a new Activity with the new ListItems
                Intent intentForItemList = new Intent(CategoryActivity.this,ItemActivity.class);
                intentForItemList.putExtra("selectedCategory",selectedItem);
                startActivityForResult(intentForItemList,222);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}
