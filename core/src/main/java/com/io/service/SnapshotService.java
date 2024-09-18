package com.io.service;

import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.db.entity.CharactersEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;

public class SnapshotService {
    private final DatabaseEngine dbEngine;

    public SnapshotService(DatabaseEngine dbEngine) {
        this.dbEngine = dbEngine;
    }

    public Long createSnapshot(GameSnapshot gameSnapshot) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var charactersDAO = dbEngine.getDAO(CharactersEntity.class);

        try {
            var snapshotEntity = new SnapshotEntity();
            snapshotDAO.create(snapshotEntity);
            var snapshotId = snapshotEntity.getId();
            System.out.println("Created new snapshot with id=" + snapshotId);

            for (var chs : gameSnapshot.characterSnapshotList()) {
                CharactersEntity charactersEntity = new CharactersEntity();
                charactersEntity.init(snapshotId, chs);
                charactersDAO.create(charactersEntity);
            }

            return snapshotId;
        } catch (SQLException ignored) {
            return null;
        }
    }

    public GameSnapshot getSnapshot(long id) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharactersEntity.class);
        try {
            var snapshotEntity = snapshotDAO.queryForId(id);
            var characterEntityList = characterDAO.queryBuilder().where().eq("snapshotId", id).query();
            var characterSnapshotList = characterEntityList.stream().map(CharactersEntity::export).toList();
            return new GameSnapshot(snapshotEntity.getId(), characterSnapshotList);
        } catch (SQLException e) {
            return null;
        }
    }

    public GameSnapshot getLastSnapshot() {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        try {
            var lastSnapshotEntity = snapshotDAO.queryBuilder().orderBy("id", false).queryForFirst();
            if (lastSnapshotEntity == null) return null;
            return getSnapshot(lastSnapshotEntity.getId());
        } catch (SQLException e) {
            return null;
        }
    }

    public void deleteSnapshot(long id) {
        System.out.println("DELETE");
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharactersEntity.class);
        try {
            snapshotDAO.deleteById(id);

            var deleteBuilder = characterDAO.deleteBuilder();
            deleteBuilder.where().eq("snapshotId", 1);
            deleteBuilder.delete();
        } catch (SQLException e) {
        }
    }
}
