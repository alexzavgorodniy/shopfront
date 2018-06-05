package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import model.Manufacturer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ManufacturerRepositoryIT extends RepositoryIT {

    @Autowired
    private ManufacturerRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldSaveManufacturer() {
        //given
        Manufacturer manufacturer = new Manufacturer("Honda");
        //when
        manufacturer = repository.save(manufacturer);
        //then
        List<Manufacturer> resultList = entityManager
                .createQuery("SELECT m FROM Manufacturer m", Manufacturer.class).getResultList();
        Manufacturer maybeManufacturer = resultList.get(resultList.size() - 1);
        Long id = maybeManufacturer.getId();
        assertEquals(manufacturer, maybeManufacturer);
        assertThat(id, is(manufacturer.getId()));
    }
}
