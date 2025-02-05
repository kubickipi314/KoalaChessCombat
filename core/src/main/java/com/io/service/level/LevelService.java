package com.io.service.level;

import com.io.core.character.CharacterEnum;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.LevelEntity;
import com.io.db.entity.SnapshotEntity;

import java.sql.SQLException;
import java.util.List;

public class LevelService {
    private final DatabaseLevelInterface dbEngine;

    private final List<Long> levels;
    private int levelIdx = 0;

    public LevelService(DatabaseLevelInterface dbEngine) {
        this.dbEngine = dbEngine;

        var lvl = getLevels();
        if (lvl.isEmpty()) {
            loadLevels();
            lvl = getLevels();
        }
        levels = lvl;
    }

    public long getCurrentLevel() {
        return levels.get(levelIdx);
    }

    public void previousLevel() {
        levelIdx = (levelIdx + levels.size() - 1) % levels.size();
    }

    public void nextLevel() {
        levelIdx = (levelIdx + 1) % levels.size();
    }

    private void createLevel(
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

    private void loadLevels() {
        dbEngine.clear();

        {
            // LEVEL 1
            SnapshotEntity snapshotEntity = new SnapshotEntity(5, 5);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                    new CharacterEntity(0, 0, CharacterEnum.Player, 0),
                    new CharacterEntity(2, 4, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(3, 4, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(4, 3, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(4, 4, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(4, 2, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(3, 3, CharacterEnum.GoodEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of(
                    new CellEntity(1, 3, true),
                    new CellEntity(2, 3, true),
                    new CellEntity(2, 1, true),
                    new CellEntity(3, 1, true)
            );
            createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }
        {
            // LEVEL 2
            SnapshotEntity snapshotEntity = new SnapshotEntity(5, 5);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                    new CharacterEntity(0, 0, CharacterEnum.Player, 0),
                    new CharacterEntity(1, 4, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(3, 4, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(4, 1, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(4, 0, CharacterEnum.GoodEnemy, 1),
                    //new CharacterEntity(4, 1, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(4, 2, CharacterEnum.GoodEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of(
                    new CellEntity(0, 3, true),
                    new CellEntity(1, 3, true),
                    new CellEntity(3, 3, true),
                    new CellEntity(4, 3, true)
            );
            createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }
        {
            // LEVEL 3
            SnapshotEntity snapshotEntity = new SnapshotEntity(5, 6);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                    new CharacterEntity(2, 0, CharacterEnum.Player, 0),
                    new CharacterEntity(2, 2, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(0, 4, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(4, 3, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(0, 3, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(1, 5, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(3, 5, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(4, 4, CharacterEnum.MeleeEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of(
                    new CellEntity(1, 4, true),
                    new CellEntity(1, 3, true),
                    new CellEntity(1, 2, true),
                    new CellEntity(1, 0, true),
                    new CellEntity(3, 4, true),
                    new CellEntity(3, 3, true),
                    new CellEntity(3, 2, true),
                    new CellEntity(3, 0, true)
            );
            createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }
        {
            // LEVEL 4
            SnapshotEntity snapshotEntity = new SnapshotEntity(6, 8);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                    new CharacterEntity(3, 0, CharacterEnum.Player, 0),
                    new CharacterEntity(0, 7, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(2, 7, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(4, 7, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(5, 7, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(5, 4, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(0, 4, CharacterEnum.GoodEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of(
                    new CellEntity(0, 5, true),
                    new CellEntity(2, 5, true),
                    new CellEntity(3, 5, true),
                    new CellEntity(5, 5, true),
                    new CellEntity(1, 2, true),
                    new CellEntity(4, 2, true),
                    new CellEntity(0, 0, true),
                    new CellEntity(5, 0, true)
            );
            createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }
        {
            // LEVEL 4
            SnapshotEntity snapshotEntity = new SnapshotEntity(8, 8);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                    new CharacterEntity(0, 0, CharacterEnum.Player, 0),
                    new CharacterEntity(0, 7, CharacterEnum.RangeEnemy, 1),
                    //new CharacterEntity(1, 7, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(1, 6, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(7, 7, CharacterEnum.RangeEnemy, 1),
                    new CharacterEntity(6, 7, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(6, 6, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(7, 0, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(0, 3, CharacterEnum.GoodEnemy, 1),
                    new CharacterEntity(3, 1, CharacterEnum.MeleeEnemy, 1),
                    new CharacterEntity(3, 0, CharacterEnum.GoodEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of(
                    new CellEntity(0, 5, true),
                    new CellEntity(1, 5, true),
                    new CellEntity(2, 5, true),
                    new CellEntity(3, 5, true),
                    new CellEntity(3, 6, true),
                    new CellEntity(3, 4, true),
                    new CellEntity(7, 5, true),
                    new CellEntity(6, 5, true),
                    new CellEntity(5, 5, true),
                    new CellEntity(5, 6, true),
                    new CellEntity(5, 3, true),
                    new CellEntity(6, 3, true),
                    new CellEntity(7, 3, true),
                    new CellEntity(4, 2, true),
                    new CellEntity(4, 1, true),
                    new CellEntity(4, 0, true),
                    new CellEntity(0, 2, true),
                    new CellEntity(1, 2, true),
                    new CellEntity(3, 2, true)
            );
            createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }
    }

    private List<Long> getLevels() {
        var levelDAO = dbEngine.getDAO(LevelEntity.class);
        try {
            return levelDAO.queryForAll().stream().map(LevelEntity::getId).toList();
        } catch (SQLException ignored) {
        }
        return List.of();
    }
}
