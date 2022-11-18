package com.example.agenda_10b.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutocompleteEditText extends androidx.appcompat.widget.AppCompatEditText {
    public AutocompleteEditText(Context context) {
        super(context);
    }

    public AutocompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}