package controller;

import java.util.List;
import model.Category;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import repository.CategoryRepository;
import repository.ItemRepository;

@Controller
public class CategoryController {

    private CategoryRepository repository;
    private ItemRepository itemRepository;

    @Autowired
    public CategoryController(CategoryRepository repository, ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/categorySelection")
    public ModelAndView showAllCategories() {
        ModelAndView modelAndView = new ModelAndView("categorySelection");
        List<Category> categories = repository.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("selectedCategory", new Category());
        return modelAndView;
    }

    @PostMapping("/categorySelection")
    public String selectCategory(@ModelAttribute Category selectedCategory) {
        return "redirect:" + "/categories/" + selectedCategory.getId();
    }

    @GetMapping("/categories/{id}")
    @Transactional
    public ModelAndView selectedCategory(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("categories");
        List<Item> items = itemRepository.findAllItemsByCategory(id);
        modelAndView.addObject("items", items);
        return modelAndView;
    }

    @GetMapping("/createCategory")
    public ModelAndView showCreateCategoryForm() {
        return new ModelAndView("createCategory");
    }

    @PostMapping("/createCategory")
    @ResponseBody
    public String creatingCategory(String newCategory) {
        Category category = new Category(newCategory);
        repository.save(category);
        return "Success! Category added!";
    }

    @GetMapping("/deleteCategory")
    public ModelAndView showDeleteCategoryPage() {
        ModelAndView modelAndView = new ModelAndView("deleteCategory");
        return modelAndView;
    }

}
