package com.io.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Cell")
public class CellEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private long snapshotId;

    @DatabaseField
    private int positionX;

    @DatabaseField
    private int positionY;

    @DatabaseField
    private boolean isBlocked;

    private CellEntity() {
    }

    public CellEntity(int positionX, int positionY, boolean isBlocked) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isBlocked = isBlocked;
    }

    public void setSnapshotId(long snapshotId) {
        this.snapshotId = snapshotId;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
