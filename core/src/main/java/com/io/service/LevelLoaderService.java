package com.io.service;

import com.io.db.DatabaseEngine;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.LevelEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;
import java.util.List;

public class LevelLoaderService {
    private final DatabaseEngine dbEngine;

    public LevelLoaderService(DatabaseEngine dbEngine) {
        this.dbEngine = dbEngine;
    }

    public List<Long> getLevels() {
        var levelDAO = dbEngine.getDAO(LevelEntity.class);
        try {
            return levelDAO.queryForAll().stream().map(LevelEntity::getId).toList();
        } catch (SQLException ignored) {
        }
        return List.of();
    }

    public void createLevel(
        LevelEntity levelEntity,
        SnapshotEntity snapshotEntity,
        List<CharacterEntity> characterEntityList,
        List<CellEntity> cellEntityList
    ) {
        var snapshotDAO = dbEngine.getDAO(SnapshotEntity.class);
        var levelDAO = dbEngine.getDAO(LevelEntity.class);
        var characterDAO = dbEngine.getDAO(CharacterEntity.class);
        var cellDAO = dbEngine.getDAO(CellEntity.class);

        try {
            snapshotDAO.create(snapshotEntity);
            var snapshotId = snapshotEntity.getId();

            levelEntity.setStartingSnapshot(snapshotId);
            levelDAO.create(levelEntity);

            characterEntityList.forEach(entity -> entity.setSnapshotId(snapshotId));
            for (var entity : characterEntityList) characterDAO.create(entity);

            cellEntityList.forEach(entity -> entity.setSnapshotId(snapshotId));
            for (var entity : cellEntityList) cellDAO.create(entity);
        } catch (SQLException ignored) {
        }
    }
}
