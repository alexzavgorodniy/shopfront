package dao;

import model.Item;

public interface DaoFactory<T> {

    T getConnection();

    EntityDao<Item> getItemDao();

    void closeConnection();
}
