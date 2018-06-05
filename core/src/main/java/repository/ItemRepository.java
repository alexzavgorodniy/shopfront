package repository;

import java.util.List;
import model.Availability;
import model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    @Query(value = "SELECT i FROM Item i WHERE subcategory_id = :id ORDER BY subcategory_id ASC")
    List<Item> findAllItemsBySubcategoryId(@Param("id") Long id);

    @Query(value = "SELECT i FROM Item i WHERE manufacturer_id = :id ORDER BY manufacturer_id ASC")
    List<Item> findAllItemsByManufacturer(@Param("id") Long id);

    @Query(value = "SELECT i FROM Item i WHERE availability = :selectedAvailability ORDER BY availability DESC")
    List<Item> findAllItemsByAvailability(@Param("selectedAvailability") Availability availability);

    @Query(value = "SELECT i FROM Item i WHERE price >= :minPrice AND price <= :maxPrice ORDER BY price ASC")
    List<Item> findAllItemsByPrice(@Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
