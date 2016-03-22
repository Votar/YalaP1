/**
 *
 * Created by Bertalt 12.03.16
 * Simple Activity in accordance with the assignment Task#1
 */


package com.bertalt.yalap1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//[Comment] Big text size
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.tmp_Fragment_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initReciclerView();

       setCommonClickListener(findViewById(R.id.content_main)); //[Comment] Wrong formatting

    }

    /**
     *
     * @param view - Top level view
     * Setup common ClickListener which show toast with view name
     *
     */
    private void setCommonClickListener(View view){

        ArrayList<View> allChildView = Util.getAllChildren(view);

        for(int i=0; i<allChildView.size(); i++)
        {

            View v = allChildView.get(i);
            if(v instanceof TextView)
                v.setOnClickListener(new ComonClickListener(this));

        } //[Comment] Wrong formatting Use ctrl + shift + L
    }

    /**
     * Make shorter onCreate()
     */
    private void initReciclerView(){ //[Comment] RecyclerView

        RecyclerView rvImages = (RecyclerView) findViewById(R.id.rvImages);
        rvImages.setHasFixedSize(true);

        rvImages.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvImages, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                StringBuilder message = new StringBuilder().append(getApplicationContext().getResources().getText(R.string.tmp_selected))
                        .append(" ")
                        .append(position+1)
                        .append(" ")
                        .append(getApplicationContext().getResources().getText(R.string.tmp_image));

                Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        ImagesAdapter adapter = new ImagesAdapter(generateLinkList());
        rvImages.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(layoutManager);
    }


    /**
     *Generate list of links on images for Picasso and recyclerView
     */
    private ArrayList<String> generateLinkList(){
        ArrayList list = new ArrayList<>();//[Comment] Use abstraction instead of realization
        list.add("http://www.jpost.com/HttpHandlers/ShowImage.ashx?id=277701&h=530&w=758"); //[Comment] Hardcode, put strings into <string-array />
        list.add("http://www.nasa.gov/sites/default/files/bwhi1apicaaamlo.jpg_large.jpg");
        list.add("http://media4.s-nbcnews.com/j/MSNBC/Components/Photo/_new/earth-space-540x380.grid-6x2.jpg");

        return list;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish(); //[Comment] Without this
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Custom interface for recyclerView
     */
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    /**
     * Initialization ClickListener for RecyclerView
     */
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener { //[Comment] You don't need it. Just set onClick for recyclerView item inside view holder

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child)); //[Comment] Deprecated
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child)); //[Comment] Deprecated
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}
