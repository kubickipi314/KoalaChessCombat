package com.io.service;

//import com.io.core.snapshot.GameSnapshot;

import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;
import java.util.List;

public class SnapshotService {
    private final DatabaseEngine dbEngine;

    public SnapshotService(DatabaseEngine dbEngine) {
        this.dbEngine = dbEngine;
    }

    public void createSnapshot(Long id, SnapshotEntity snapshotEntity, List<CharacterEntity> charactersEntityList, List<CellEntity> cellEntityList) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharacterEntity.class);
        var cellDAO = dbEngine.getDAO(CellEntity.class);

        try {
            if (id != null) deleteSnapshot(id);

            snapshotDAO.create(snapshotEntity);
            var snapshotId = snapshotEntity.getId();
            System.out.println("Created new snapshot with id=" + snapshotId);
            for (var charactersEntity : charactersEntityList) {
                charactersEntity.setSnapshotId(snapshotId);
                characterDAO.create(charactersEntity);
            }
            for (var cellEntity : cellEntityList) {
                cellEntity.setSnapshotId(snapshotId);
                cellDAO.create(cellEntity);
            }
        } catch (SQLException ignored) {
        }
    }

    public GameSnapshot getSnapshot(long id) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharacterEntity.class);
        var cellDAO = dbEngine.getDAO(CellEntity.class);

        try {
            var snapshotEntity = snapshotDAO.queryForId(id);
            var characterEntityList = characterDAO.queryBuilder().where().eq("snapshotId", id).query();
            var cellEntityList = cellDAO.queryBuilder().where().eq("snapshotId", id).query();
            return new GameSnapshot(snapshotEntity, characterEntityList, cellEntityList);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public GameSnapshot getLastSnapshot() {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        try {
            var lastSnapshotEntity = snapshotDAO.queryBuilder().orderBy("id", false).queryForFirst();
            if (lastSnapshotEntity == null) return null;
            return getSnapshot(lastSnapshotEntity.getId());
        } catch (SQLException ignored) {
            return null;
        }
    }

    public void deleteSnapshot(long id) {
        System.out.println("DELETE");
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharacterEntity.class);
        try {
            snapshotDAO.deleteById(id);

            var deleteBuilder = characterDAO.deleteBuilder();
            deleteBuilder.where().eq("snapshotId", id);
            deleteBuilder.delete();
        } catch (SQLException ignored) {
        }
    }
}
