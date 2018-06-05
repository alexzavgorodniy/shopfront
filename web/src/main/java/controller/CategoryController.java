package controller;

import exception.ErrorMessage;
import java.util.List;
import model.Category;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
    public CategoryController(CategoryRepository categoryRepository,
            ItemRepository itemRepository) {
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
            if (param.equals("ADD_SUBCATEGORY")) {
                return "redirect:/addSubcategory/" + selectedCategory.getId();
            } else if (param.equals("DELETE_SUBCATEGORY")) {
                return "redirect:/deleteSubcategory/" + selectedCategory.getId();
            } else if (param.equals("UPDATE_SUBCATEGORY")) {
                return "redirect:/updateSubcategory/" + selectedCategory.getId();
            }
        }
        return "redirect:/subcategorySelection/" + selectedCategory.getId();
    }

    @GetMapping("/categories/{id}")
    public ModelAndView selectedCategory(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("categories");
        List<Item> items = itemRepository.findAllItemsBySubcategoryId(id);
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
        return "redirect:/success";
    }

    @GetMapping("/deleteCategory")
    public ModelAndView showDeleteCategoryPage() {
        ModelAndView modelAndView = new ModelAndView("deleteCategory");
        List<Category> categories = categoryRepository.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("selectedCategory", new Category());
        return modelAndView;
    }

    @PostMapping("/deleteCategory")
    public String deleteCategoryPage(@ModelAttribute Category deleteCategory) {
        try {
            categoryRepository.delete(deleteCategory.getId());
        } catch (DataIntegrityViolationException e) {
            return "redirect:/error/" + ErrorMessage.CATEGORY_CANNOT_BE_DELETED.getMessage();
        }
        return "redirect:/success";
    }

    @GetMapping("/updateCategory")
    public ModelAndView showUpdateCategory() {
        ModelAndView modelAndView = new ModelAndView("updateCategory");
        List<Category> categories = categoryRepository.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("selectedCategory", new Category());
        return modelAndView;
    }

    @PostMapping("/updateCategory")
    public String updatingCategory(@ModelAttribute Category oldCategory) {
        return "redirect:/updateCategoryForm/" + oldCategory.getId();
    }

    @GetMapping("/updateCategoryForm/{id}")
    public ModelAndView showUpdateCategoryForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("updateCategoryForm");
        Category oldCategory = categoryRepository.findOne(id);
        Category newCategory = new Category();
        newCategory.setId(id);
        newCategory.setSubcategories(oldCategory.getSubcategories());
        modelAndView.addObject("oldCategory", oldCategory);
        modelAndView.addObject("newCategory", newCategory);
        return modelAndView;
    }

    @PostMapping("/updateCategoryForm/{id}")
    public String postUpdateCategoryForm(@ModelAttribute Category newCategory) {
        categoryRepository.save(newCategory);
        return "redirect:/success";
    }
}
