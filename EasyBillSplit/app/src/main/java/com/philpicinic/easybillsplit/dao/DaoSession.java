package com.philpicinic.easybillsplit.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.philpicinic.easybillsplit.dao.UserGroup;
import com.philpicinic.easybillsplit.dao.User;

import com.philpicinic.easybillsplit.dao.UserGroupDao;
import com.philpicinic.easybillsplit.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userGroupDaoConfig;
    private final DaoConfig userDaoConfig;

    private final UserGroupDao userGroupDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userGroupDaoConfig = daoConfigMap.get(UserGroupDao.class).clone();
        userGroupDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        userGroupDao = new UserGroupDao(userGroupDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(UserGroup.class, userGroupDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        userGroupDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public UserGroupDao getUserGroupDao() {
        return userGroupDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
