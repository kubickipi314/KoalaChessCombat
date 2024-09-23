package com.io.service.level;

import com.j256.ormlite.dao.Dao;

public interface DatabaseLevelInterface {
    <T> Dao<T, Long> getDAO(Class<T> cls);

    void clear();
}
