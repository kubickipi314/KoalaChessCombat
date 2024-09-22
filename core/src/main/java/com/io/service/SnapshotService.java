package com.io.service;

import com.io.core.snapshot.GameSnapshot;
import com.io.db.DatabaseEngine;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.LevelEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;
import java.util.List;

public class SnapshotService {
    private final DatabaseEngine dbEngine;

    public SnapshotService(DatabaseEngine dbEngine) {
        this.dbEngine = dbEngine;
    }

    public void createLevelSnapshot(long levelId, SnapshotEntity snapshotEntity, List<CharacterEntity> charactersEntityList, List<CellEntity> cellEntityList) {
        var levelDAO = dbEngine.getDAO(LevelEntity.class);
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var characterDAO = dbEngine.getDAO(CharacterEntity.class);
        var cellDAO = dbEngine.getDAO(CellEntity.class);

        try {
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
            var updateBuilder = levelDAO.updateBuilder();
            updateBuilder.where().idEq(levelId);
            updateBuilder.updateColumnValue("currentSnapshot", snapshotId);
            updateBuilder.update();
        } catch (SQLException ignored) {
        }
    }

    public GameSnapshot getLevelSnapshot(long id) {
        var levelDAO = dbEngine.getDAO(LevelEntity.class);

        try {
            System.out.println("id:" + id);
            var levelEntity = levelDAO.queryForId(id);
            System.out.println(id);
            long snapshotId = levelEntity.getCurrentSnapshot() == null
                    ? levelEntity.getStartingSnapshot()
                    : levelEntity.getCurrentSnapshot();
            return getSnapshot(snapshotId);
        } catch (SQLException ignored) {
        }
        return null;
    }

    public void removeLevelSnapshot(long levelId) {
        var levelDAO = dbEngine.getDAO(LevelEntity.class);
        try {
            var updateBuilder = levelDAO.updateBuilder();
            updateBuilder.where().idEq(levelId);
            updateBuilder.updateColumnValue("currentSnapshot", null);
            updateBuilder.update();
        } catch (SQLException ignored) {
        }
    }

    private GameSnapshot getSnapshot(long id) {
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
}
