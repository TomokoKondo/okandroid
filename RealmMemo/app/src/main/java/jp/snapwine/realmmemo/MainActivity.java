package jp.snapwine.realmmemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import jp.snapwine.realmmemo.model.Memo;
import jp.snapwine.realmmemo.view.ViewMenuMemo;

public class MainActivity extends AppCompatActivity {
    private List<ViewMenuMemo> memoList;
    private int focusId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoList = new ArrayList<ViewMenuMemo>();

        String[] memoTitles = Memo.getTitles(getApplicationContext());
        for (String title : memoTitles) {
            addMemo(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            if (focusId > -1 && focusId < memoList.size()) {
                ((LinearLayout) findViewById(R.id.root_memo)).removeView(memoList.get(focusId));
                memoList.remove(focusId);
                Memo.deleteMemo(getApplicationContext(), focusId);
                focusId = -1;
                setFocus(focusId);
                for (int i = 0; i < memoList.size(); i++) {
                    memoList.get(i).setId(i);
                }
                return true;
            }
        } else if (id == R.id.action_add) {
            startMemoActivity(-1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        memoList.clear();
    }

    private void addMemo(String memoTitle) {
        int id = memoList.size();
        memoList.add(id, new ViewMenuMemo(this, null, memoTitle, id));

        ((LinearLayout) findViewById(R.id.root_memo)).addView(memoList.get(id));
    }

    public void setFocus(int id) {
        focusId = id;
        ViewMenuMemo memo;
        for (int i = 0; i < memoList.size(); i++) {
            memo = memoList.get(i);
            memo.setFocus(memo.getId() == id);
        }
    }

    public void startMemoActivity(int id) {
        Intent intent = new Intent(getApplicationContext(), ActivityEditMemo.class);
        intent.putExtra("memo_id", id);
        startActivity(intent);
        finish();
    }
}
