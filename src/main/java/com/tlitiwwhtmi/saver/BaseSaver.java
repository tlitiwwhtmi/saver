package com.tlitiwwhtmi.saver;

import java.util.List;

/**
 * Created by saver on 16/5/31.
 */
public abstract class BaseSaver {

    public abstract boolean insert(Object obj);

    public abstract boolean delete(Object obj);

    public abstract boolean update(Object obj);

    public abstract Object getById(String key);

    public abstract Object query(String key);

    public abstract List list();
}
