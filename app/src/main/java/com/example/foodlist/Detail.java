package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import static com.example.foodlist.MainActivity.EXTRA_CREATOR;
import static com.example.foodlist.MainActivity.EXTRA_LIKES;
import static com.example.foodlist.MainActivity.EXTRA_URL;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra( EXTRA_URL );
        String creatorName = intent.getStringExtra( EXTRA_CREATOR );
        String likeCount = intent.getStringExtra( EXTRA_LIKES );

        ImageView imageView = findViewById( R.id.image_view_detail );
        TextView textViewCreator = findViewById( R.id.text_view_creator_detail );
        TextView textViewLikes = findViewById( R.id.text_view_like_detail );

        Picasso.with( this ).load( imageUrl ).fit().centerInside().into( imageView );
        textViewCreator.setText( creatorName );
        textViewLikes.setText( likeCount );

    }
}
