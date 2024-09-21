package com.io;

import com.badlogic.gdx.Game;
import com.io.core.character.CharacterEnum;
import com.io.db.DatabaseEngine;
import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.LevelEntity;
import com.io.db.entity.SnapshotEntity;
import com.io.service.LevelLoaderService;

import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private DatabaseEngine dbEngine;
    private Coordinator coordinator;

    private void loadLevels() {
        //TODO

        dbEngine.clear();
        LevelLoaderService lls = new LevelLoaderService(dbEngine);

        {
            // LEVEL 1
            SnapshotEntity snapshotEntity = new SnapshotEntity(5, 5);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                new CharacterEntity(0, 0, CharacterEnum.Player, 0),
                new CharacterEntity(2, 4, CharacterEnum.MeleeEnemy, 1),
                new CharacterEntity(3, 4, CharacterEnum.MeleeEnemy, 1),
                new CharacterEntity(4, 3, CharacterEnum.MeleeEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of();
            lls.createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }

        {
            // LEVEL 2
            SnapshotEntity snapshotEntity = new SnapshotEntity(5, 5);
            LevelEntity levelEntity = new LevelEntity();
            List<CharacterEntity> characterEntityList = List.of(
                new CharacterEntity(0, 0, CharacterEnum.Player, 0),
                new CharacterEntity(1, 4, CharacterEnum.MeleeEnemy, 1),
                new CharacterEntity(2, 4, CharacterEnum.MeleeEnemy, 1),
                new CharacterEntity(3, 4, CharacterEnum.MeleeEnemy, 1),
                new CharacterEntity(4, 4, CharacterEnum.MeleeEnemy, 1)
            );
            List<CellEntity> cellEntityList = List.of(
                new CellEntity(0, 3, true),
                new CellEntity(1, 3, true),
                new CellEntity(2, 3, true),
                new CellEntity(3, 3, true),
                new CellEntity(4, 3, true)
            );
            lls.createLevel(levelEntity, snapshotEntity, characterEntityList, cellEntityList);
        }
    }

    @Override
    public void create() {
        dbEngine = new DatabaseEngine("jdbc:sqlite:game.db");
        loadLevels();

        coordinator = new Coordinator(this, dbEngine);
        coordinator.setStartScreen();
    }

    @Override
    public void dispose() {
        super.dispose();

        coordinator.quit();
        dbEngine.closeConnection();
    }
}
