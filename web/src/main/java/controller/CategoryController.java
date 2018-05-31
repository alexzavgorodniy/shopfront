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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import repository.CategoryRepository;
import repository.ItemRepository;

@Controller
public class CategoryController {

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/categorySelection")
    public ModelAndView showAllCategories() {
        ModelAndView modelAndView = new ModelAndView("categorySelection");
        List<Category> categories = categoryRepository.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("selectedCategory", new Category());
        return modelAndView;
    }

    @PostMapping("/categorySelection")
    public String selectCategory(@RequestParam(required = false) String param,
            @ModelAttribute Category selectedCategory) {
        if (param != null) {
            if (param.equals("DELETE")) {
                categoryRepository.delete(selectedCategory.getId());
                return "redirect:/successPage";
            } else if (param.equals("UPDATE")) {
                return "redirect:/updateCategory/" + selectedCategory.getId();
            }
        }
        return "redirect:/categories/" + selectedCategory.getId();
    }

    @GetMapping("/categories/{id}")
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
    public String creatingCategory(String newCategory) {
        Category category = new Category(newCategory);
        categoryRepository.save(category);
        return "redirect:/successPage";
    }

    @GetMapping("/updateCategory/{id}")
    public ModelAndView showUpdateCategoryPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("updateCategory");
        Category oldCategory = categoryRepository.findOne(id);
        Category newCategory = new Category();
        newCategory.setId(id);
        modelAndView.addObject("oldCategory", oldCategory);
        modelAndView.addObject("newCategory", newCategory);
        return modelAndView;
    }

    @PostMapping("/updateCategory/{id}")
    public String updatingCategory(@ModelAttribute Category newCategory) {
        categoryRepository.save(newCategory);
        return "redirect:/successPage";
    }
}
