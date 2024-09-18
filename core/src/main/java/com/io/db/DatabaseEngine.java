package com.io.db;

import com.io.db.entity.CharactersEntity;
import com.io.db.entity.SnapshotEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseEngine {
    private JdbcPooledConnectionSource connectionSource;


    public DatabaseEngine(String url) {
        try {
            connectionSource
                = new JdbcPooledConnectionSource(url);
            TableUtils.createTableIfNotExists(connectionSource, SnapshotEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, CharactersEntity.class);
        } catch (SQLException e) {
            System.err.println("Failed to create db connection.\t" + e);
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
