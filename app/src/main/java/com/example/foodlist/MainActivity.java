package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FoodAdapter.OnItemClickListener{
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";
    private RecyclerView mRecyclerView;
    private FoodAdapter mFoodAdapter;
    private ArrayList<FoodList> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mRecyclerView = findViewById( R.id.recycler_view );
        mRecyclerView.setHasFixedSize( true );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        mExampleList = new ArrayList<>(  );

        mRequestQueue = Volley.newRequestQueue( this );
        parseJSON();
    }
    private void parseJSON(){
        //API
        String url ="https://www.themealdb.com/api/json/v1/1/categories.php";

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray( "categories" );

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject( i );
                                //Looping Json
                                String creatorName = hit.getString( "strCategory" );
                                String imageUrl = hit.getString( "strCategoryThumb" );
                                String likeCount = hit.getString( "strCategoryDescription" );

                                mExampleList.add( new FoodList( imageUrl,creatorName,likeCount ) );
                            }

                            mFoodAdapter = new FoodAdapter( MainActivity.this,mExampleList );
                            mRecyclerView.setAdapter( mFoodAdapter );
                            mFoodAdapter.setOnClickListener( MainActivity.this );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        } );

        mRequestQueue.add( request );

    }
    //Move To Detail
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent( this,Detail.class );
        FoodList clickedItem = mExampleList.get( position );

        detailIntent.putExtra( EXTRA_URL,clickedItem.getImageUrl() );
        detailIntent.putExtra( EXTRA_CREATOR,clickedItem.getCreator() );
        detailIntent.putExtra( EXTRA_LIKES, clickedItem.getLikeCount() );
        startActivity( detailIntent );
    }

    // Profile
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {

            case R.id.profile:
                Intent i = new Intent( MainActivity.this, Profile.class );
                startActivity( i );
                break;

        }
    }
}
