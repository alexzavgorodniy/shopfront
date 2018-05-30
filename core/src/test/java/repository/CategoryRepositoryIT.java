package repository;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import model.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryRepositoryIT extends RepositoryIT {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldSaveCategory() {
        //given
        Category category = new Category("KBT");
        //when
        repository.save(category);
        //then
        Category maybeCategory = entityManager
                .createQuery("SELECT c FROM Category c", Category.class).getSingleResult();
        assertEquals(category,maybeCategory);
    }
}
