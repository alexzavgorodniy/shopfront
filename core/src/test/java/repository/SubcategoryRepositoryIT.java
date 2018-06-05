package repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.persistence.EntityManager;
import model.Subcategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SubcategoryRepositoryIT extends RepositoryIT {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldSaveSubcategory() {
        //given
        String subcategorySubname = "new Subcategory";
        Subcategory subcategory = new Subcategory(subcategorySubname);
        //when
        subcategoryRepository.save(subcategory);
        //then
        List<Subcategory> resultList = entityManager
                .createQuery("SELECT s FROM Subcategory s", Subcategory.class).getResultList();
        Subcategory maybeSubcategory = resultList.get(resultList.size() - 1);
        assertEquals(subcategory, maybeSubcategory);
    }
}
