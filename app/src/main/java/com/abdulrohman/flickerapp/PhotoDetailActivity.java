package com.abdulrohman.flickerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

interface IShowDetail {
    void showDetailClickItem(Photo p);
}

public class PhotoDetailActivity extends BaseActivity {
    TextView phothoTitel;
    TextView phothoAuthor;
    TextView phothoTag;
    ImageView photoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        appBar(true);
        Photo intent = (Photo) getIntent().getSerializableExtra(PHOTO_TRANSFER);
        phothoAuthor = findViewById(R.id.photoAuthorDetail);
        phothoTag = findViewById(R.id.photoTagDetail);
        phothoTitel = findViewById(R.id.photoTitelDetail);
        photoImg = findViewById(R.id.imgDetail);
        if (intent != null) {
            phothoTitel.setText(intent.getTitle());
            phothoAuthor.setText(intent.getAuthor());
            phothoTag.setText(intent.getTag());
            Picasso.get().load(intent.getLink())
                    .error(R.drawable.place)
                    .into(photoImg);
        }
    }


}
