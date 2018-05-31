package controller;

import dto.ItemDto;
import java.util.Arrays;
import java.util.List;
import model.Availability;
import model.Category;
import model.Item;
import model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import repository.CategoryRepository;
import repository.ItemRepository;
import repository.ManufacturerRepository;

@Controller
public class ItemController {

    private ItemRepository itemRepository;

    private CategoryRepository categoryRepository;

    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository,
            CategoryRepository categoryRepository,
            ManufacturerRepository manufacturerRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/itemSelection")
    public ModelAndView showItemSelectionPage() {
        ModelAndView modelAndView = new ModelAndView("itemSelection");
        List<Item> items = itemRepository.findAll();
        modelAndView.addObject("items", items);
        modelAndView.addObject("selectedItem", new Item());
        return modelAndView;
    }

    @PostMapping("/itemSelection")
    public String processSelectedItem(@RequestParam(required = false) String param,
            @ModelAttribute Item selectedItem) {
        if (param.equals("DELETE")) {
            itemRepository.delete(selectedItem.getId());
            return "redirect:/successPage";
        } else if (param.equals("UPDATE")) {
            return "redirect:/updateItem/" + selectedItem.getId();
        }
        return "redirect:/createItem";
    }

    @Transactional
    @GetMapping("/createItem")
    public ModelAndView createItem() {
        ModelAndView modelAndView = new ModelAndView("createItem");
        List<Category> categories = categoryRepository.findAll();
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        Availability availability = Availability.ABSENT;
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("manufacturers", manufacturers);
        modelAndView.addObject("availability", availability);
        modelAndView.addObject("newItem", new Item());
        return modelAndView;
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute(name = "newItem") Item newItem) {
        itemRepository.save(newItem);
        return "redirect:/successPage";
    }

    @GetMapping("/updateItem/{id}")
    public ModelAndView showUpdateItemPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("updateItem");
        Item oldItem = itemRepository.findOne(id);
        Item newItem = new Item();
        newItem.setId(id);
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        modelAndView.addObject("oldItem", oldItem);
        modelAndView.addObject("manufacturers", manufacturers);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("availabilities", Arrays.asList(Availability.values()));
        modelAndView.addObject("newItem", newItem);
        return modelAndView;
    }

    @PostMapping("/updateItem/{id}")
    public String showItemUpdated(@ModelAttribute Item newItem) {
        itemRepository.save(newItem);
        return "redirect:/successPage";
    }
}
