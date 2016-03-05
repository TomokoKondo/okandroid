package jp.snapwine.realmmemo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import jp.snapwine.realmmemo.ActivityEditMemo;
import jp.snapwine.realmmemo.MainActivity;
import jp.snapwine.realmmemo.R;

/**
 * Created by kondoutomoko on 2016/03/02.
 */
public class ViewMenuMemo extends FrameLayout implements View.OnClickListener {
    private FrameLayout frameLayout;
    private String title;
    private int id;
    private boolean isClicked = false;

    public ViewMenuMemo(Context context, AttributeSet attrs, String memoTitle, int id) {
        super(context);
        frameLayout = (FrameLayout)LayoutInflater.from(context).inflate(R.layout.view_menu_memo, ViewMenuMemo.this);

        title = memoTitle;
        this.id = id;
    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();

        ImageButton button = (ImageButton)frameLayout.findViewById(R.id.button_memo);
        button.setOnClickListener(this);

        ((TextView) frameLayout.findViewById(R.id.text_memo_title)).setText(title);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isClicked = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP && isClicked) {
            if (event.getY() >= 0 && event.getY() <= getHeight())
                ((MainActivity)getContext()).setFocus(id);
            isClicked = false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getContext()).startMemoActivity(id);
    }

    public void setFocus(boolean isFocused) {
        if (isFocused) {
//            frameLayout.setBackground(new ColorDrawable(Color.rgb(225, 225, 225)));
            frameLayout.setBackgroundColor(Color.rgb(225, 225, 225));
        } else {
//            frameLayout.setBackground(new ColorDrawable(Color.rgb(255, 255, 255)));
            frameLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        }
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
}
