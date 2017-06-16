package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data.CostumAdapterForItem;
import com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data.ItemTop;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ItemActivity extends AppCompatActivity {


    static ArrayList<ItemTop> foodList;
    static ArrayList<ItemTop> medicineList;
    static ArrayList<ItemTop> peopleList;
    static ArrayList<ItemTop> payingList;

    private static CostumAdapterForItem myAdapter;
    public static int choosenCategory;

    static ArrayList<Integer> checkedCheckBoxes;
    static  ArrayList<CheckBox> checkBoxList;

    //Shared preferences part
    private String MY_PREFS_NAME = "PassDataPref";
    SharedPreferences sharedPreferences;
    static String titleMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //Set up shared preferences
        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);


        foodList = new ArrayList<ItemTop>();
        peopleList = new ArrayList<ItemTop>();
        medicineList = new ArrayList<ItemTop>();
        payingList = new ArrayList<ItemTop>();

        checkedCheckBoxes = new ArrayList<Integer>();
        checkBoxList = new ArrayList<CheckBox>();

        //Add elements to the lists
        createListItems();

        ListView itemListView = (ListView)findViewById(R.id.listViewRealItems);

        //Get the message from the prev. activity
        Bundle bundle = getIntent().getExtras();
        String gottenMessage = bundle.getString("selectedCategory");

        switch (gottenMessage){
            case "Shopping":
                choosenCategory = 0;
                break;
            case "People":
                choosenCategory = 1;
                break;
            case "Medicine":
                choosenCategory = 2;
                break;
            case "Paying":
                choosenCategory = 3;
                break;
            default:
                break;
        }

        titleMessage = gottenMessage;

        //Means that the food part was choosen
        if (choosenCategory == 0){
            myAdapter = new CostumAdapterForItem(foodList,getApplicationContext());
            itemListView.setAdapter(myAdapter);
        }else if(choosenCategory == 1){
            myAdapter = new CostumAdapterForItem(peopleList,getApplicationContext());
        }else if(choosenCategory == 2){
            myAdapter = new CostumAdapterForItem(medicineList,getApplicationContext());
        }else if(choosenCategory == 3){
            myAdapter = new CostumAdapterForItem(payingList,getApplicationContext());
        }

        itemListView.setAdapter(myAdapter);

        //Since the checking is not working if we have a ListView which takes away the focus, I used this way
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox actualCheckBox = (CheckBox)view.findViewById(R.id.checkBoxAdding);
                //String tag =  actualCheckBox.getTag().toString();

                if (!actualCheckBox.isChecked()){
                    actualCheckBox.setChecked(true);
                    //int v = actualCheckBox.getId();
                   // checkedCheckBoxes.add(actualCheckBox.getId());
                    checkBoxList.add(actualCheckBox);
                }else{
                    actualCheckBox.setChecked(false);
                    //Check if we have it
                    if (checkBoxList.contains(actualCheckBox)){
                        int index = checkBoxList.indexOf(actualCheckBox);
                        checkBoxList.remove(index);
                    }
                }
            }
        });


    }

    public void createListItems(){

        //Food list part
        ItemTop itemFood1 = new ItemTop("Cheese");
        ItemTop itemFood2 = new ItemTop("Bread");
        ItemTop itemFood3 = new ItemTop("Ham");
        ItemTop itemFood4 = new ItemTop("Watermelon");
        ItemTop itemFood5 = new ItemTop("Apple");
        ItemTop itemFood6 = new ItemTop("Sugar");
        ItemTop itemFood7 = new ItemTop("Flour");
        ItemTop itemFood8 = new ItemTop("Milk");
        ItemTop itemFood9 = new ItemTop("Rice");
        ItemTop itemFood10 = new ItemTop("Sweets");

        //People part
        //TODO access real phone contacts
        ItemTop person1 = new ItemTop("Mother");
        ItemTop person2 = new ItemTop("Father");
        ItemTop person3 = new ItemTop("Sister");
        ItemTop person4 = new ItemTop("Brother");
        ItemTop person5 = new ItemTop("Friend1");
        ItemTop person6 = new ItemTop("Friend2");

        //Medicine part
        ItemTop medicine1 = new ItemTop("Medicine1");
        ItemTop medicine2 = new ItemTop("Medicine1");
        ItemTop medicine3 = new ItemTop("Medicine1");
        ItemTop medicine4 = new ItemTop("Medicine1");
        ItemTop medicine5 = new ItemTop("Medicine1");

        //Paying category
        ItemTop payWaterBill = new ItemTop("Waterbill");
        ItemTop payElectricityBill = new ItemTop("Electricitybill");
        ItemTop payGasBill = new ItemTop("Gasbill");

        //Add the elements to the appropiate list
        foodList.add(itemFood1);
        foodList.add(itemFood2);
        foodList.add(itemFood3);
        foodList.add(itemFood4);
        foodList.add(itemFood5);
        foodList.add(itemFood6);
        foodList.add(itemFood7);
        foodList.add(itemFood8);
        foodList.add(itemFood9);
        foodList.add(itemFood10);

        peopleList.add(person1);
        peopleList.add(person2);
        peopleList.add(person3);
        peopleList.add(person4);
        peopleList.add(person5);
        peopleList.add(person6);

        medicineList.add(medicine1);
        medicineList.add(medicine2);
        medicineList.add(medicine3);
        medicineList.add(medicine4);
        medicineList.add(medicine5);

        payingList.add(payWaterBill);
        payingList.add(payElectricityBill);
        payingList.add(payGasBill);

    }

    public void addElement(View view){

        if (checkBoxList.size() > 0){
            //TODO insert value into shared preferences
            String stringToPass = "";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (int i = 0; i < checkBoxList.size(); i++){
                CheckBox checkBox = checkBoxList.get(i);
                stringToPass = String.format("%s %s",stringToPass,checkBox.getTag().toString());
            }

            editor.putString("title",titleMessage);
            editor.putString("comment",stringToPass);
            editor.commit();

            finish();

        }


    }
}
