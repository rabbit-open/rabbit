package com.hualala.librealm.dao.core;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        // Migrate from version 0 to version 1
        if (oldVersion == 0) {
            //from0to1(schema);
            oldVersion++;
        }
        // Migrate from version 1 to version 2
        if (oldVersion == 1) {
            //from1to2(realm, schema);
            oldVersion++;
        }
        //依次递归升级
    }

    private void from1to2(DynamicRealm realm, RealmSchema schema) {
        // Create a new class
        RealmObjectSchema petSchema = schema.create("Pet")
                .addField("name", String.class, FieldAttribute.REQUIRED)
                .addField("type", String.class, FieldAttribute.REQUIRED);

        // Add a new field to an old class and populate it with initial data
        schema.get("Person")
                .addRealmListField("pets", petSchema)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        if (obj.getString("fullName").equals("JP McDonald")) {
                            DynamicRealmObject pet = realm.createObject("Pet");
                            pet.setString("name", "Jimbo");
                            pet.setString("type", "dog");
                            obj.getList("pets").add(pet);
                        }
                    }
                });
    }

    private void from0to1(RealmSchema schema) {
        RealmObjectSchema personSchema = schema.get("Person");
        personSchema
                .addField("fullName", String.class, FieldAttribute.REQUIRED)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        obj.set("fullName", obj.getString("firstName") + " " + obj.getString("lastName"));
                    }
                })
                .removeField("firstName")
                .removeField("lastName");
    }
}
