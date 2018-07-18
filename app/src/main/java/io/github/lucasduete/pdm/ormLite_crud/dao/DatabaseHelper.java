package io.github.lucasduete.pdm.ormLite_crud.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import io.github.lucasduete.pdm.ormLite_crud.R;
import io.github.lucasduete.pdm.ormLite_crud.entities.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "helloOrmLite.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> userDao = null;
    private RuntimeExceptionDao<User, Integer> runtimeExceptionDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        RuntimeExceptionDao<User, Integer> dao = getSimpleDataDao();
        long millis = System.currentTimeMillis();

        User user= new User("Maria", "maria@gmail.com");
        dao.create(user);
        user = new User("Joao", "joao@gmail.com");
        dao.create(user);
        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<User, Integer> getDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public RuntimeExceptionDao<User, Integer> getSimpleDataDao() {
        if (runtimeExceptionDao == null) {
            runtimeExceptionDao = getRuntimeExceptionDao(User.class);
        }
        return runtimeExceptionDao;
    }

    @Override
    public void close() {
        super.close();
        userDao= null;
        runtimeExceptionDao = null;
    }

}
