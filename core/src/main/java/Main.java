import config.JpaConfig;
import model.Availability;
import model.Category;
import model.Item;
import model.Manufacturer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.CategoryRepository;
import repository.ItemRepository;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                JpaConfig.class);
    }

}
