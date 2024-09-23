package com.io.db;

import com.io.db.entity.CellEntity;
import com.io.db.entity.CharacterEntity;
import com.io.db.entity.LevelEntity;
import com.io.db.entity.SnapshotEntity;
import com.io.service.level.DatabaseLevelInterface;
import com.io.service.snapshot.DatabaseSnapshotInterface;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseEngine implements DatabaseSnapshotInterface, DatabaseLevelInterface {
    private JdbcPooledConnectionSource connectionSource;

    public DatabaseEngine(String url) {
        try {
            connectionSource = new JdbcPooledConnectionSource(url);
        } catch (SQLException e) {
            System.err.println("Failed to create db connection.\t" + e);
        }
    }

    public void clear() {
        try {
            TableUtils.dropTable(connectionSource, LevelEntity.class, true);
            TableUtils.dropTable(connectionSource, SnapshotEntity.class, true);
            TableUtils.dropTable(connectionSource, CharacterEntity.class, true);
            TableUtils.dropTable(connectionSource, CellEntity.class, true);

            TableUtils.createTable(connectionSource, LevelEntity.class);
            TableUtils.createTable(connectionSource, SnapshotEntity.class);
            TableUtils.createTable(connectionSource, CharacterEntity.class);
            TableUtils.createTable(connectionSource, CellEntity.class);
        } catch (SQLException ignored) {
        }
    }

    public void closeConnection() {
        try {
            connectionSource.close();
        } catch (Exception ignored) {
        }
    }

    public <T> Dao<T, Long> getDAO(Class<T> cls) {
        try {
            return DaoManager.createDao(connectionSource, cls);
        } catch (SQLException e) {
            System.err.println("Failed to get DAO.\t" + e);
            return null;
        }
    }
}
