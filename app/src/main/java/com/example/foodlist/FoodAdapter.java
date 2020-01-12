package com.example.foodlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<FoodList> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public FoodAdapter(Context context, ArrayList<FoodList> exampleList){
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( mContext ).inflate( R.layout.food_list,parent,false );
        return new ExampleViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        FoodList currentItem = mExampleList.get( position );

        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        String likeCount = currentItem.getLikeCount();

        holder.mTextViewCreator.setText( creatorName );
        holder.mTextViewLikes.setText( "Likes: "+ likeCount );
        Picasso.with( mContext ).load( imageUrl ).fit().centerInside().into( holder.mImageView );
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public ExampleViewHolder(@NonNull View itemView) {
            super( itemView );
            mImageView = itemView.findViewById( R.id.image_view );
            mTextViewCreator = itemView.findViewById( R.id.text_view_creator );
            mTextViewLikes = itemView.findViewById( R.id.text_view_likes );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick( position );
                        }
                    }
                }
            } );
        }
    }
}
