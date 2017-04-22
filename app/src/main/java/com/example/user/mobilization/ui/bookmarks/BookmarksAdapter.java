package com.example.user.mobilization.ui.bookmarks;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mobilization.R;
import com.example.user.mobilization.model.BookmarkModel;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by User on 17.04.17.
 */

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {
    private ArrayList<BookmarkModel> data = new ArrayList<>();
    private ArrayList<BookmarkModel> cleanData;
    private BookmarkClick callback;

    public void setBookmarkClickListener(BookmarkClick listener) {
        this.callback = listener;
    }

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
        holder.bookmarkView.setOnClickListener(v -> {
            callback.bookmarkButtonOnClick(v, position);
            if (data.get(position).isState()) {
                holder.bookmarkView.setImageResource(R.drawable.ic_bookmark_grey);
                data.get(position).setState(false);

            } else {
                holder.bookmarkView.setImageResource(R.drawable.ic_bookmark_yellow);
                data.get(position).setState(true);
            }
        });
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
        this.data.clear();
        this.data = data;
        cleanData = data;
    }

    public void searchFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data = new ArrayList<>();
        Log.e("TEXT", charText);
        if (charText.length() < 1) {
            // cleanData содержит неизмененную и неотфильтрованную копию данных списка
            data.addAll(cleanData);
        } else {
            for (int i = 0; i < cleanData.size(); i++) {
                try {
                    if ((cleanData.get(i).getTranslation().toLowerCase(Locale.getDefault()).contains(charText)) ||
                            cleanData.get(i).getTranslated().toLowerCase(Locale.getDefault()).contains(charText)) {
                        data.add(cleanData.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface BookmarkClick {
        void bookmarkButtonOnClick(View v, int position);
    }
}
