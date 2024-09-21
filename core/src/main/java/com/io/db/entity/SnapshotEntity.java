package com.io.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Snapshot")
public class SnapshotEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private int boardWidth;

    @DatabaseField
    private int boardHeight;

    private SnapshotEntity() {
    }

    public SnapshotEntity(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public long getId() {
        return id;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}
