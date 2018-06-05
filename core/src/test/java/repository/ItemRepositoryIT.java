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
import model.Subcategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemRepositoryIT extends RepositoryIT {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldSaveItemIntoDB() {
        //given
        Item item = new Item("GSX-001");
        Category category = new Category("KBT");
        Subcategory subcategory = new Subcategory("Washmachine");
        Manufacturer manufacturer = new Manufacturer("Honda");
        category.addSubcategory(subcategory);
        item.setDescription("motorcycle");
        item.setSubcategory(subcategory);
        item.setAvailability(Availability.ABSENT);
        item.setPrice(2.35);
        item.setManufacturer(manufacturer);
        manufacturerRepository.save(manufacturer);
        categoryRepository.save(category);
        subcategoryRepository.save(subcategory);
        //when
        repository.save(item);
        //then
        Item maybeItem = entityManager.createQuery("SELECT i FROM Item i", Item.class)
                .getSingleResult();
        assertEquals(item,maybeItem);
        assertEquals(maybeItem.getSubcategory().getSubname(),"Washmachine");
        assertEquals(maybeItem.getSubcategory().getCategory().getName(),"KBT");

    }
}
