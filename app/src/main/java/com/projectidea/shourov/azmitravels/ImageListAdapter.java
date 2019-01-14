package com.projectidea.shourov.azmitravels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shourov on 14,January,2019
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    private OnItemClickListener mClickListener;

    public ImageListAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list, viewGroup, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder imageHolder, int i) {
        Upload upload = mUploads.get(i);
        Glide.with(mContext)
                .load(upload.getImageUrl())
                .into(imageHolder.listItemImageview);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_item_imageview)
        ImageView listItemImageview;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mClickListener.onItemCLick(position);
                }
            }
        }
    }

    //click interface
    public interface OnItemClickListener {
        void onItemCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }
}
