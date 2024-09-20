package com.io.core.snapshot;

import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.SnapshotEntity;

import java.util.List;

public record GameSnapshot(
        SnapshotEntity snapshotEntity,
        List<CharacterEntity> characterEntityList,
        List<CellEntity> cellEntityList
) {
    public Long getId() {
        return snapshotEntity.getId();
    }
}
