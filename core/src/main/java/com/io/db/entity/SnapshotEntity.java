package com.io.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Snapshot")
public class SnapshotEntity {

    @DatabaseField(generatedId = true)
    private long id;

    public SnapshotEntity() {
    }

    public long getId() {
        return id;
    }
}
