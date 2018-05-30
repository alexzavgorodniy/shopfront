package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import model.Availability;
import model.Category;
import model.Item;
import model.Manufacturer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemRepositoryIT extends RepositoryIT {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldSaveItemIntoDB() {
        //given
        Item item = new Item("GSX-001");
        item.setDescription("motorcycle");
        Category category = new Category("KBT");
        item.setCategory(category);
        item.setAvailability(Availability.ABSENT);
        item.setPrice(2.35);
        Manufacturer manufacturer = new Manufacturer("Honda");
        manufacturerRepository.save(manufacturer);
        item.setManufacturer(manufacturer);
        //when
        repository.save(item);
        //then
        Item maybeItem = entityManager.createQuery("SELECT i FROM Item i", Item.class)
                .getSingleResult();
        assertEquals(item,maybeItem);

    }
}
