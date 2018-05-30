package controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import repository.ItemRepository;
import repository.ManufacturerRepository;

@Controller
public class ManufacturerController {

    private ManufacturerRepository repository;
    private ItemRepository itemRepository;

    @Autowired
    public ManufacturerController(ManufacturerRepository repository,
            ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/manufacturerSelection")
    public ModelAndView showAllManufacturers() {
        ModelAndView modelAndView = new ModelAndView("manufacturerSelection");
        List<Manufacturer> manufacturers = repository.findAll();
        modelAndView.addObject("manufacturers", manufacturers);
        modelAndView.addObject("selectedManufacturer", new Manufacturer());
        return modelAndView;
    }

    @Transactional
    @PostMapping("/manufacturerSelection")
    public String selectManufacturer(@ModelAttribute Manufacturer selectedManufacturer) {
        return "redirect:" + "/manufacturers/" + selectedManufacturer.getId();
    }

    @GetMapping("/manufacturers/{id}")
    @Transactional
    public ModelAndView selectedManufacturer(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("manufacturers");
        List<Item> items = itemRepository.findAllItemsByManufacturer(id);
        modelAndView.addObject("items", items);
        return modelAndView;
    }

    @GetMapping("/createManufacturer")
    public ModelAndView showAddCategoryPage() {
        return new ModelAndView("createManufacturer");
    }

    @PostMapping("/createManufacturer")
    @ResponseBody
    public String selectedPrices(String newManufacturer) {
        Manufacturer manufacturer = new Manufacturer(newManufacturer);
        repository.save(manufacturer);
        return "Success! Manufacturer added!";
    }
}
