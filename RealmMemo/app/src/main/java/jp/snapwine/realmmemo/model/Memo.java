package jp.snapwine.realmmemo.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by kondoutomoko on 2016/03/04.
 */
public class Memo {
    public static String[] getTitles(Context appContext) {
        String[] titles;

        Realm realm = getRealm(appContext);
        RealmQuery<MemoObject> query = realm.where(MemoObject.class);
        RealmResults<MemoObject> results = query.findAll();

        titles = new String[results.size()];
        Iterator<MemoObject> iterator = results.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            titles[i] = iterator.next().getTitle();
        }

        return titles;
    }

    public static MemoObject getMemoByTitle(Context appContext, String title) {
        MemoObject memo;

        Realm realm = getRealm(appContext);
        RealmQuery<MemoObject> query = realm.where(MemoObject.class);
        query.equalTo("title", title);
        RealmResults<MemoObject> results = query.findAll();
        memo = results.first();

        return memo;
    }

    public static MemoObject getMemoById(Context appContext, int id) {
        MemoObject memo;

        Realm realm = getRealm(appContext);
        RealmQuery<MemoObject> query = realm.where(MemoObject.class);
        RealmResults<MemoObject> results = query.findAll();
        memo = results.get(id);

        return memo;
    }

    public static void deleteMemo(Context appContext, MemoObject memoObject, int id) {
        Realm realm = getRealm(appContext);
        RealmResults<MemoObject> results = realm.where(MemoObject.class).findAll();
        realm.beginTransaction();

        if (memoObject != null) {
            memoObject.removeFromRealm();
        } else if (id >= 0 && id < results.size()) {
            List<MemoObject> memoList = new ArrayList<>();
            Iterator<MemoObject> iterator = results.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                MemoObject tmp = iterator.next();
                if (i != id) {
                    MemoObject replaceMemo = new MemoObject();
                    replaceMemo.setTitle(tmp.getTitle());
                    replaceMemo.setMemo(tmp.getMemo());
                    memoList.add(replaceMemo);
                }
            }
            results.clear();
            realm.copyToRealm(memoList);
        }

        realm.commitTransaction();
    }

    public static void addMemo(Context appContext, String title, String memo) {
        Realm realm = getRealm(appContext);
        realm.beginTransaction();

        MemoObject memoObject = realm.createObject(MemoObject.class);
        memoObject.setTitle(title);
        memoObject.setMemo(memo);

        realm.commitTransaction();
    }

    public static void changeMemo(Context appContext, MemoObject memoObject, String title, String memo) {
        Realm realm = getRealm(appContext);
        realm.beginTransaction();

        memoObject.setTitle(title);
        memoObject.setMemo(memo);

        realm.commitTransaction();
    }

    private static Realm getRealm(Context appContext) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(appContext).build();
        return Realm.getInstance(realmConfig);
    }
}
