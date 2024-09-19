package com.io.service;

//import com.io.core.snapshot.GameSnapshot;

import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;
import java.util.List;

public class SnapshotService {
    private final DatabaseEngine dbEngine;

    public SnapshotService(DatabaseEngine dbEngine) {
        this.dbEngine = dbEngine;
    }

    public void createSnapshot(Long id, SnapshotEntity snapshotEntity, List<CharacterEntity> charactersEntityList) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var charactersDAO = dbEngine.getDAO(CharacterEntity.class);
        try {
            if (id != null) deleteSnapshot(id);

            snapshotDAO.create(snapshotEntity);
            var snapshotId = snapshotEntity.getId();
            System.out.println("Created new snapshot with id=" + snapshotId);
            for (var charactersEntity : charactersEntityList) {
                charactersEntity.setSnapshotId(snapshotId);
                charactersDAO.create(charactersEntity);
            }
        } catch (SQLException ignored) {
        }
    }

    public GameSnapshot getSnapshot(long id) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharacterEntity.class);
        try {
            var snapshotEntity = snapshotDAO.queryForId(id);
            var characterEntityList = characterDAO.queryBuilder().where().eq("snapshotId", id).query();
            System.out.println(id + "_" + characterEntityList);
            return new GameSnapshot(snapshotEntity, characterEntityList);
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
