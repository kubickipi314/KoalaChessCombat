package com.io.service.snapshot;

import com.j256.ormlite.dao.Dao;

public interface DatabaseSnapshotInterface {
    <T> Dao<T, Long> getDAO(Class<T> cls);
}
