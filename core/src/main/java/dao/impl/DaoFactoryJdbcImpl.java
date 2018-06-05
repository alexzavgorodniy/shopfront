package dao.impl;

import configJDBC.Config;
import dao.DaoFactory;
import dao.EntityDao;
import exception.DaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Item;

public final class DaoFactoryJdbcImpl implements DaoFactory<Connection> {

    private static DaoFactoryJdbcImpl instance;

    private EntityDao<Item> itemEntityDao;

    private DaoFactoryJdbcImpl() {
        try {
            Class.forName(Config.getInstance().driver());
        } catch (ClassNotFoundException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public static DaoFactoryJdbcImpl getInstance() {
        if (instance == null) {
            instance = new DaoFactoryJdbcImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            Config config = Config.getInstance();
            return DriverManager.getConnection(config.url(), config.user(), config.password());
        } catch (SQLException e) {
            throw new DaoException("Could not create SQL Connection");
        }
    }

    @Override
    public EntityDao<Item> getItemDao() {
        if (itemEntityDao == null) {
            itemEntityDao = new ItemDaoJdbc();
        }
        return itemEntityDao;
    }

    @Override
    public void closeConnection() {
        try {
            this.getConnection().close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
