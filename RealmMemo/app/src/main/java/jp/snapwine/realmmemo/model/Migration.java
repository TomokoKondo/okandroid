package jp.snapwine.realmmemo.model;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by kondoutomoko on 2016/03/12.
 */
public class Migration implements RealmMigration {
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema personSchema = schema.get("MemoObject");

            // Combine 'firstName' and 'lastName' in a new field called 'fullName'
            personSchema
                    .addField("id", int.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("id", 0);
                        }
                    });
            oldVersion++;
        }
    }
}
