package com.ucb.suficiencia.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {

    private TextView contentTextView;
    private ImageView contentImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_content, container, false);
        contentTextView = view.findViewById(R.id.news_content_text);
        contentImageView = view.findViewById(R.id.news_content_image);

        if (getArguments() != null) {
            String content = getArguments().getString("content");
            int imageResId = getArguments().getInt("imageResId");
            updateContent(content, imageResId);
        }

        return view;
    }

    public void updateContent(String newsContent, int imageResId) {
        contentTextView.setText(newsContent);
        contentImageView.setImageResource(imageResId);
    }
}
