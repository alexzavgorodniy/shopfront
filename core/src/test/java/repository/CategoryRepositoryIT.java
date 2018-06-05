package repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
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
        List<Category> resultList = entityManager
                .createQuery("SELECT c FROM Category c", Category.class).getResultList();
        Category maybeCategory = resultList.get(resultList.size() - 1);
        assertEquals(category, maybeCategory);
    }
}
