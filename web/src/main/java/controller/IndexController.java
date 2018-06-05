package controller;

import dao.EntityDao;
import dao.impl.DaoFactoryJdbcImpl;
import java.util.List;
import model.Item;
import model.Manufacturer;
import model.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import repository.CategoryRepository;
import repository.ItemRepository;
import repository.ManufacturerRepository;
import repository.SubcategoryRepository;

@Controller
public class IndexController {

    private ItemRepository itemRepository;

    private ManufacturerRepository manufacturerRepository;

    private CategoryRepository categoryRepository;

    private SubcategoryRepository subcategoryRepository;

    @Autowired
    public IndexController(ItemRepository itemRepository,
            ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository,
            SubcategoryRepository subcategoryRepository) {
        this.itemRepository = itemRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @GetMapping({"/", "/index"})
    public ModelAndView show() {
        return new ModelAndView("index");
    }

    @GetMapping("/viewSelection")
    public ModelAndView showViewSelection() {
        ModelAndView modelAndView = new ModelAndView("viewSelection");
        modelAndView.addObject("itemTitle", "");
        return modelAndView;
    }

    @PostMapping("/viewSelection")
    public String searchItemByModel(String itemTitle) {
        return "redirect:/foundItems/" + itemTitle;
    }

    @GetMapping("/foundItems/{itemTitle}")
    public ModelAndView showFoundItemsByTitle(@PathVariable String itemTitle) {
        ModelAndView modelAndView = new ModelAndView("foundItems");
        EntityDao<Item> itemDao = DaoFactoryJdbcImpl.getInstance().getItemDao();
        List<Item> items = itemDao.findAllItemsByTitle(itemTitle);
        for (Item item : items) {
            Manufacturer manufacturer = manufacturerRepository
                    .findOne(item.getManufacturer().getId());
            item.setManufacturer(manufacturer);
            Subcategory subcategory = subcategoryRepository.findOne(item.getSubcategory().getId());
            item.setSubcategory(subcategory);
        }
        modelAndView.addObject("items", items);
        return modelAndView;
    }

    @GetMapping("/administrationPage")
    public ModelAndView showAdministrationPage() {
        return new ModelAndView("administrationPage");
    }

    @GetMapping("/success")
    public ModelAndView showSuccessPage() {
        return new ModelAndView("success");
    }
}
