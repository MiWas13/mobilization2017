package com.example.user.mobilization.ui.bookmarks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mobilization.R;
import com.example.user.mobilization.model.BookmarkModel;

import java.util.ArrayList;


/**
 * Created by User on 17.04.17.
 */

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {
    private ArrayList<BookmarkModel> data = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmarks, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data.get(position).isState()) {
            holder.bookmarkView.setImageResource(R.drawable.ic_bookmark_yellow);
        } else {
            holder.bookmarkView.setImageResource(R.drawable.ic_bookmark_grey);
        }
        holder.firstLanguage.setText(data.get(position).getTranslated());
        holder.secondLanguage.setText(data.get(position).getTranslation());
        holder.languagesView.setText(data.get(position).getLanguages());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookmarkView;
        private TextView firstLanguage, secondLanguage, languagesView;

        ViewHolder(View itemView) {
            super(itemView);
            bookmarkView = (ImageView) itemView.findViewById(R.id.bookmark_view);
            firstLanguage = (TextView) itemView.findViewById(R.id.first_language);
            secondLanguage = (TextView) itemView.findViewById(R.id.second_language);
            languagesView = (TextView) itemView.findViewById(R.id.languages);
        }
    }

    public void setData(ArrayList<BookmarkModel> data) {
        this.data = data;
    }

}
