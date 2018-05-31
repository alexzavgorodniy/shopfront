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

    @Query(value = "SELECT i FROM Item i WHERE category_id = :id")
    List<Item> findAllItemsByCategory(@Param("id") Long id);

    @Query(value = "SELECT i FROM Item i WHERE manufacturer_id = :id")
    List<Item> findAllItemsByManufacturer(@Param("id") Long id);

    @Query(value = "SELECT i FROM Item i WHERE availability = :selectedAvailability")
    List<Item> findAllItemsByAvailability(@Param("selectedAvailability") Availability availability);

    @Query(value = "SELECT i FROM Item i WHERE price >= :minPrice AND price <= :maxPrice")
    List<Item> findAllItemsByPrice(@Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
