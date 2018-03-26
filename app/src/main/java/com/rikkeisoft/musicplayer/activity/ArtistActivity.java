package com.rikkeisoft.musicplayer.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import com.rikkeisoft.musicplayer.R;
import com.rikkeisoft.musicplayer.activity.base.AppbarActivity;
import com.rikkeisoft.musicplayer.custom.adapter.pager.ArtistPagerAdapter;
import com.rikkeisoft.musicplayer.model.AlbumsModel;
import com.rikkeisoft.musicplayer.model.SongsModel;
import com.rikkeisoft.musicplayer.model.item.AlbumItem;
import com.rikkeisoft.musicplayer.model.item.ArtistItem;
import com.rikkeisoft.musicplayer.model.item.SongItem;
import com.rikkeisoft.musicplayer.utils.Loader;

import java.util.List;

public class ArtistActivity extends AppbarActivity {

    public static final String ID = "id";

    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    public static Intent createIntent(Context context, String id) {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra(ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        init();

        findArtist();
    }

    @Override
    protected void init() {
        super.init();

        pagerAdapter = new ArtistPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

        setTitleTap(0, R.string.songs);
        setTitleTap(1, R.string.albums);
    }

    @Override
    protected void findView() {
        super.findView();

        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        showHomeButton();
    }

    private void findArtist() {
        ArtistItem artist = Loader.findArtist(this, getIntent().getStringExtra(ID));
        setSongs(artist.getSongs(this));

        if(artist.getNumberOfAlbums() > 0)
            if(artist.getAlbums(this).size() > 0)
                setAlbums(artist.getAlbums(this));
        else appbarImage.setImageBitmap(null);

        setTitle(artist.getName()); Log.d("debug", artist.getName());
    }

    private void setSongs(List<SongItem> songs) {
        SongsModel songsModel = ViewModelProviders.of(this).get(SongsModel.class);
        songsModel.getItems().setValue(songs);
    }

    private void setAlbums(List<AlbumItem> albums) {
        AlbumsModel albumsModel = ViewModelProviders.of(this).get(AlbumsModel.class);
        albumsModel.getItems().setValue(albums);

//        if(albums.get(0).getBmAlbumArt() != null)
            appbarImage.setImageBitmap(albums.get(0).getBmAlbumArt());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}