package com.io.service;

import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.db.entity.CharactersEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;
import java.util.List;

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
            snapshotDAO.create(new SnapshotEntity());
            var snapshotId = snapshotEntity.getId();

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

    public List<SnapshotEntity> getSnapshot(long id) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        try {
            return snapshotDAO.queryBuilder().where().eq("snapshotId", id).query();
        } catch (SQLException e) {
            return null;
        }
    }
}
