package com.philpicinic.easybillsplit.service;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import com.philpicinic.easybillsplit.activity.GroupCreateActivity;
import com.philpicinic.easybillsplit.activity.GroupSelectActivity;
import com.philpicinic.easybillsplit.contact.ContactPerson;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.contact.MyGroup;
import com.philpicinic.easybillsplit.contact.TextPerson;
import com.philpicinic.easybillsplit.dao.DaoMaster;
import com.philpicinic.easybillsplit.dao.User;
import com.philpicinic.easybillsplit.dao.UserDao;
import com.philpicinic.easybillsplit.dao.UserGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 10/6/14.
 */
public class DatabaseService {

    private static volatile DatabaseService instance = null;

    private DaoMaster daoMaster;

    public DatabaseService(){
        DaoMaster.DevOpenHelper helper = new
                DaoMaster.DevOpenHelper(GroupSelectActivity.getInstance(), "group-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
    }

    public List<MyGroup> getGroups(){
        List<UserGroup> groupsRaw = daoMaster.newSession().getUserGroupDao().queryBuilder().list();
        ArrayList<MyGroup> groups = new ArrayList<MyGroup>(groupsRaw.size());
        for(UserGroup g : groupsRaw){
            groups.add(new MyGroup(g.getId(), g.getName()));
        }
        return groups;
    }

    public void saveGroup(String name, ArrayList<IPerson> members){
        UserGroup group = new UserGroup();
        group.setName(name);
        long groupId = daoMaster.newSession().getUserGroupDao().insert(group);
        UserDao userDao = daoMaster.newSession().getUserDao();
        for(IPerson person : members){
            User user = person.createDatabaseUser();
            user.setGroupId(groupId);
            userDao.insert(user);
        }
    }

    public List<User> getUsersByGroup(long groupId){
        return daoMaster.newSession().getUserDao().
                queryBuilder().
                where(UserDao.Properties.GroupId.eq(groupId)).
                list();
    }

    public void deleteGroup(long groupId){
        daoMaster.newSession().getUserGroupDao().deleteByKey(groupId);
        UserDao userDao = daoMaster.newSession().getUserDao();
        userDao.queryBuilder().where(UserDao.Properties.GroupId.eq(groupId)).
                buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static DatabaseService getInstance(){
        if(instance == null){
            synchronized (DatabaseService.class){
                if(instance == null){
                    instance = new DatabaseService();
                }
            }
        }
        return instance;
    }
}
