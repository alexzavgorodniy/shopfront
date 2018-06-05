package controller;

import exception.ErrorMessage;
import java.util.List;
import model.Category;
import model.Item;
import model.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import repository.CategoryRepository;
import repository.ItemRepository;
import repository.SubcategoryRepository;

@Controller
public class SubcategoryController {

    private CategoryRepository categoryRepository;

    private SubcategoryRepository subcategoryRepository;

    private ItemRepository itemRepository;

    @Autowired
    public SubcategoryController(CategoryRepository categoryRepository,
            SubcategoryRepository subcategoryRepository,
            ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/subcategorySelection/{id}")
    public ModelAndView prepareSubcategories(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("subcategorySelection");
        Category category = categoryRepository.findOne(id);
        List<Subcategory> subcategories = category.getSubcategories();
        modelAndView.addObject("categoryName", category.getName());
        modelAndView.addObject("subcategories", subcategories);
        modelAndView.addObject("selectedSubcategory", new Subcategory());
        return modelAndView;
    }

    @PostMapping("/subcategorySelection/{id}")
    public String selectSubcategory(@ModelAttribute Subcategory selectedSubCategory) {
        return "redirect:/categories/" + selectedSubCategory.getId();
    }

    @GetMapping("/addSubcategory/{id}")
    public ModelAndView showAddSubcategoryPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("addSubcategory");
        Category category = categoryRepository.findOne(id);
        modelAndView.addObject("categoryName", category.getName());
        modelAndView.addObject("newSubcategory", "");
        return modelAndView;
    }

    @PostMapping("/addSubcategory/{id}")
    public String addingSubcategory(@PathVariable Long id, String newSubcategory) {
        Category category = categoryRepository.findOne(id);
        Subcategory subcategory = new Subcategory(newSubcategory);
        category.addSubcategory(subcategory);
        categoryRepository.save(category);
        subcategoryRepository.save(subcategory);
        return "redirect:/success";
    }

    @GetMapping("/deleteSubcategory/{id}")
    public ModelAndView showDeleteSubcategoryPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("deleteSubcategory");
        Category category = categoryRepository.findOne(id);
        List<Subcategory> subcategories = category.getSubcategories();
        modelAndView.addObject("category", category);
        modelAndView.addObject("subcategories", subcategories);
        modelAndView.addObject("selectedSubcategory", new Subcategory());
        return modelAndView;
    }

    @PostMapping("/deleteSubcategory/{id}")
    public String deletecategoryPage(@PathVariable Long id,
            @ModelAttribute Subcategory selectedSubcategory) {
        Long subcategoryId = selectedSubcategory.getId();
        List<Item> items = itemRepository.findAllItemsBySubcategoryId(subcategoryId);
        try {
            if (items.isEmpty()) {
                Category category = categoryRepository.findOne(id);
                Subcategory subcategory = subcategoryRepository.findOne(subcategoryId);
                category.removeSubcategory(subcategory);
                subcategoryRepository.delete(subcategoryId);
            } else {
                throw new DataIntegrityViolationException("");
            }
        } catch (DataIntegrityViolationException e) {
            return "redirect:/error/" + ErrorMessage.CATEGORY_CANNOT_BE_DELETED.getMessage();
        }
        return "redirect:/success";
    }

    @GetMapping("/updateSubcategory/{id}")
    public ModelAndView showUpdateSubcategoryPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("updateSubcategory");
        Category category = categoryRepository.findOne(id);
        List<Subcategory> subcategories = category.getSubcategories();
        modelAndView.addObject("category", category);
        modelAndView.addObject("subcategories", subcategories);
        modelAndView.addObject("selectedSubcategory", new Subcategory());
        return modelAndView;
    }

    @PostMapping("/updateSubcategory/{id}")
    public String updateSubcategory(@PathVariable Long id,
            @ModelAttribute Subcategory subcategory) {
        return "redirect:/updateValueofSubcategory/" + id + "/" + subcategory.getId();
    }

    @GetMapping("/updateValueofSubcategory/{catId}/{subCatId}")
    public ModelAndView showUpdateValueofSubcategoryPage(@PathVariable("catId") Long catId, @PathVariable("subCatId") Long subCatId) {
        ModelAndView modelAndView = new ModelAndView("updateValueofSubcategory");
        Category category = categoryRepository.findOne(catId);
        Subcategory oldSubcategory = subcategoryRepository.findOne(subCatId);
        Subcategory newSubcategory = new Subcategory();
        newSubcategory.setId(oldSubcategory.getId());
        newSubcategory.setCategory(category);
        modelAndView.addObject("category", category);
        modelAndView.addObject("oldSubcategory", oldSubcategory);
        modelAndView.addObject("newSubcategory", newSubcategory);
        return modelAndView;
    }

    @PostMapping("/updateValueofSubcategory/{catId}/{subCatId}")
    public String showUpdateValueofSubcategory(@ModelAttribute Subcategory newSubcategory,
            @PathVariable("subCatId") Long subCatId) {
        Subcategory oldSubcategory = subcategoryRepository.findOne(subCatId);
        Category oldCategory = oldSubcategory.getCategory();
        newSubcategory.setCategory(oldCategory);
        newSubcategory.setId(subCatId);
        subcategoryRepository.save(newSubcategory);
        return "redirect:/success";
    }
}
