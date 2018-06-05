package dao;

import java.util.List;

public interface EntityDao<T> {

    List<T> findAllItemsByTitle(String itemModel);
}
