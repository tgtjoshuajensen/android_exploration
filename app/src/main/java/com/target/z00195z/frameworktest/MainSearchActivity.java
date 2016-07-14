package com.target.z00195z.frameworktest;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;


public class MainSearchActivity extends AppCompatActivity {
    EditText mEdit;
    Button mButton;
    ListView listItem;
    CustomItemAdapter itemAdapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        mEdit = (EditText) findViewById(R.id.SearchField);
        mButton = (Button) findViewById(R.id.SearchButton);

        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    submitSearch(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listItem = (ListView) findViewById(R.id.listItem);

//        set up adapter
        ArrayList<Item> arrayOfItems = new ArrayList<>();
        itemAdapter = new CustomItemAdapter(MainSearchActivity.this, arrayOfItems);
        listItem.setAdapter(itemAdapter);

        updateSearchResults(new PreviousSearch(MainSearchActivity.this).getSearchTerm());
    }

    public MainSearchActivity(){
        handler = new Handler();
    }
    public void submitSearch(View view) {
        new PreviousSearch(MainSearchActivity.this).setSearchTerm(mEdit.getText().toString());
        updateSearchResults(mEdit.getText().toString());
    }
    private void populateItemsList(JSONObject jObj) throws JSONException {

        JSONArray products = jObj.getJSONArray("products");
        for (int i=0; i<products.length(); i++) {
            JSONObject item = products.getJSONObject(i);
            Log.e("title", item.getString("title"));
            Item newItem = new Item(item.getString("title"), item.getString("tcin"));
            itemAdapter.add(newItem);
        }
    }

    private void updateSearchResults(final String searchTerm){
        itemAdapter.clear();
        new Thread(){
            public void run(){
                final JSONObject json = RedskyRestClient.getJSON(searchTerm);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Log.e("no results", "the response was no good.");

                            Toast.makeText(MainSearchActivity.this,
                                    MainSearchActivity.this.getString(R.string.no_results),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            try {
                                populateItemsList(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.start();
    }
}
