package com.example.yapily_app.utilities;

import android.view.View;

public interface ItemClickListener {
    void onItemClick(View view, int position);
    void onItemHeld(View view, int position);
}
