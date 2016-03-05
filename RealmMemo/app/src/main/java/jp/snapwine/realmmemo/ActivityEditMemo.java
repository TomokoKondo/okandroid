package jp.snapwine.realmmemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jp.snapwine.realmmemo.model.Memo;
import jp.snapwine.realmmemo.model.MemoObject;

/**
 * Created by kondoutomoko on 2016/03/05.
 */
public class ActivityEditMemo extends Activity {
    int memoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        getMemoId();
        if (memoId != -1)
            readMemo();

        ((Button)findViewById(R.id.button_memo_finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText) findViewById(R.id.edit_title)).getText().toString();
                String memo = ((EditText) findViewById(R.id.edit_memo)).getText().toString();
                if (title == null || title.equals("")) {
                    title = "memo";
                }
                saveMemo(title, memo);
                startMainActivity();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startMainActivity();
        }
        return false;
    }

    private void getMemoId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            memoId = extras.getInt("memo_id");
        }
    }

    private void readMemo() {
        MemoObject memo = Memo.getMemoById(getApplicationContext(), memoId);
        ((EditText)findViewById(R.id.edit_title)).setText(memo.getTitle());
        ((EditText)findViewById(R.id.edit_memo)).setText(memo.getMemo());
    }

    private void saveMemo(String title, String memo) {
        if (memoId == -1) {
            Memo.addMemo(getApplicationContext(), title, memo);
        } else {
            Memo.changeMemo(getApplicationContext(), Memo.getMemoById(getApplicationContext(), memoId), title, memo);
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
