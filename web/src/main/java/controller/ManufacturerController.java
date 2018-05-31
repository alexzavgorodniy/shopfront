package controller;

import java.util.List;
import model.Category;
import model.Item;
import model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import repository.ItemRepository;
import repository.ManufacturerRepository;

@Controller
public class ManufacturerController {

    private ManufacturerRepository manufacturerRepository;
    private ItemRepository itemRepository;

    @Autowired
    public ManufacturerController(ManufacturerRepository manufacturerRepository,
            ItemRepository itemRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/manufacturerSelection")
    public ModelAndView showAllManufacturers() {
        ModelAndView modelAndView = new ModelAndView("manufacturerSelection");
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        modelAndView.addObject("manufacturers", manufacturers);
        modelAndView.addObject("selectedManufacturer", new Manufacturer());
        return modelAndView;
    }

    @PostMapping("/manufacturerSelection")
    public String selectManufacturer(@RequestParam(required = false) String param,
            @ModelAttribute Manufacturer selectedManufacturer) {
        if (param != null) {
            if (param.equals("DELETE")) {
                manufacturerRepository.delete(selectedManufacturer.getId());
                return "redirect:/successPage";
            } else if (param.equals("UPDATE")) {
                return "redirect:/updateManufacturer/" + selectedManufacturer.getId();
            }
        }
        return "redirect:/manufacturers/" + selectedManufacturer.getId();
    }

    @GetMapping("/manufacturers/{id}")
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
    public String selectedPrices(String newManufacturer) {
        Manufacturer manufacturer = new Manufacturer(newManufacturer);
        manufacturerRepository.save(manufacturer);
        return "redirect:/successPage";
    }

    @GetMapping("/updateManufacturer/{id}")
    public ModelAndView showUpdateManufacturerPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("updateManufacturer");
        Manufacturer oldManufacturer = manufacturerRepository.findOne(id);
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setId(id);
        modelAndView.addObject("oldManufacturer", oldManufacturer);
        modelAndView.addObject("newManufacturer", newManufacturer);
        return modelAndView;
    }

    @PostMapping("/updateManufacturer/{id}")
    public String updatingManufacturer(@ModelAttribute Manufacturer newManufacturer) {
        manufacturerRepository.save(newManufacturer);
        return "redirect:/successPage";
    }
}
