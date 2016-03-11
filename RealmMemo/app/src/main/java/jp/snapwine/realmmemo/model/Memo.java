package jp.snapwine.realmmemo.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by kondoutomoko on 2016/03/04.
 */
public class Memo {
    public static String[] getTitles(Context appContext) {
        String[] titles;

        RealmResults<MemoObject> results = sortMemo(appContext);

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
        RealmQuery<MemoObject> query = realm.where(MemoObject.class).equalTo("id", id);
        RealmResults<MemoObject> results = query.findAll();
        memo = results.first();

        return memo;
    }

    public static void deleteMemo(Context appContext, int id) {
        Realm realm = getRealm(appContext);
        RealmResults<MemoObject> results = realm.where(MemoObject.class).equalTo("id", id).findAll();
        realm.beginTransaction();

        MemoObject memoObject = results.first();
        if (memoObject == null)
            return;
        memoObject.removeFromRealm();

        realm.commitTransaction();

        sortMemo(appContext);
    }

    public static void addMemo(Context appContext, String title, String memo) {
        Realm realm = getRealm(appContext);
        realm.beginTransaction();

        MemoObject memoObject = realm.createObject(MemoObject.class);
        memoObject.setTitle(title);
        memoObject.setMemo(memo);
        memoObject.setId(-1);

        realm.commitTransaction();
        sortMemo(appContext);
    }

    public static void changeMemo(Context appContext, MemoObject memoObject, String title, String memo) {
        Realm realm = getRealm(appContext);
        realm.beginTransaction();

        memoObject.setTitle(title);
        memoObject.setMemo(memo);
        memoObject.setId(-1);

        realm.commitTransaction();

        sortMemo(appContext);
    }

    private static RealmResults<MemoObject> sortMemo(Context appContext) {
        Realm realm = getRealm(appContext);

        RealmResults<MemoObject> results = realm.where(MemoObject.class).findAll();

        results.sort("id");
        setIds(realm, results);

        return results;
    }

    private static void setIds(Realm realm, RealmResults<MemoObject> results) {
        MemoObject[] memoObjects = new MemoObject[results.size()];
        Iterator<MemoObject> iterator = results.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            memoObjects[i] = iterator.next();
        }

        realm.beginTransaction();
        for (int i = 0; i < memoObjects.length; i++) {
            memoObjects[i].setId(i);
        }
        realm.commitTransaction();
    }

    private static Realm getRealm(Context appContext) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(appContext).schemaVersion(1).build();
        return Realm.getInstance(realmConfig);
    }
}
