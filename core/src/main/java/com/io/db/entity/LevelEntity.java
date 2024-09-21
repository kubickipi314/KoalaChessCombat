package com.io.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Level")
public class LevelEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private Long startingSnapshot;

    @DatabaseField
    private Long currentSnapshot;

    public LevelEntity() {
    }

    public long getId() {
        return id;
    }

    public long getStartingSnapshot() {
        return startingSnapshot;
    }

    public void setStartingSnapshot(long startingSnapshot) {
        this.startingSnapshot = startingSnapshot;
    }

    public Long getCurrentSnapshot() {
        return currentSnapshot;
    }

    public void setCurrentSnapshot(long currentSnapshot) {
        this.currentSnapshot = currentSnapshot;
    }
}
