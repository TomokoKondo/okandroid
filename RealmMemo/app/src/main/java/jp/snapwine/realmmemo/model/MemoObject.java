package jp.snapwine.realmmemo.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by kondoutomoko on 2016/03/04.
 */
public class MemoObject extends RealmObject {
    @Required
    private String title;
    private String memo;
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
