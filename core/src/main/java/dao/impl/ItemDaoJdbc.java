package dao.impl;

import dao.EntityDao;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Availability;
import model.Item;
import model.Manufacturer;
import model.Subcategory;

public class ItemDaoJdbc implements EntityDao<Item> {

    @Override
    public List<Item> findAllItemsByTitle(String itemTitle) {
        Connection connection = DaoFactoryJdbcImpl.getInstance().getConnection();
        try (PreparedStatement statementSelectAll = connection
                .prepareStatement("SELECT * FROM item WHERE item.title LIKE ?")) {
            statementSelectAll.setString(1, "%" + itemTitle + "%");
            ResultSet resultSet = statementSelectAll.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<Item> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            Availability availability = Availability.of(resultSet.getString(2));
            String description = resultSet.getString(3);
            Double price = resultSet.getDouble(4);
            String title = resultSet.getString(5);
            Long manufacturerId = resultSet.getLong(6);
            Long subcategoryId = resultSet.getLong(7);
            Item item = new Item();
            item.setId(id);
            item.setAvailability(availability);
            item.setDescription(description);
            item.setPrice(price);
            item.setTitle(title);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(manufacturerId);
            Subcategory subcategory = new Subcategory();
            subcategory.setId(subcategoryId);
            item.setManufacturer(manufacturer);
            item.setSubcategory(subcategory);
            items.add(item);
        }
        return items;
    }
}
