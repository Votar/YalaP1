package com.bertalt.yalap1;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Bertalt on 13.03.2016.
 */
public class ComonClickListener implements View.OnClickListener {

    private Context mCtx;
    public ComonClickListener(Context context){
        mCtx = context;
    } //[Comment] Maybe Common?
    @Override
    public void onClick(View v) {

        if (mCtx == null)
            return;

        StringBuilder message = new StringBuilder().append(mCtx.getResources().getText(R.string.tmp_selected))
                .append(" ")
                .append(v.getClass().getSimpleName());

        Toast.makeText(mCtx, message.toString(), Toast.LENGTH_SHORT).show();
    }
}
