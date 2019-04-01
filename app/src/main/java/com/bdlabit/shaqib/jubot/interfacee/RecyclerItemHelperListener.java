package com.bdlabit.shaqib.jubot.interfacee;

import android.support.v7.widget.RecyclerView;

public interface RecyclerItemHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
