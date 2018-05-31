package controller;

import java.util.Arrays;
import java.util.List;
import model.Availability;
import model.Category;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import repository.ItemRepository;

@Controller
public class AvailabilityController {

    private ItemRepository itemRepository;

    @Autowired
    public AvailabilityController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/availabilitySelection")
    public ModelAndView showAvailabilitySelectionPage() {
        ModelAndView modelAndView = new ModelAndView("availabilitySelection");
        modelAndView.addObject("availabilities", Arrays.asList(Availability.values()));
        modelAndView.addObject("selectedItemAvailability", new Item());
        return modelAndView;
    }

    @PostMapping("/availabilitySelection")
    public String selectCategory(@ModelAttribute Item item) {
        Availability availability = item.getAvailability();
        return "redirect:/availabilities/" + availability.toString();
    }


    @GetMapping("/availabilities/{str}")
    public ModelAndView selectedCategory(@PathVariable String str) {
        ModelAndView modelAndView = new ModelAndView("availabilities");
        Availability availability = Availability.of(str);
        List<Item> items = itemRepository.findAllItemsByAvailability(availability);
        modelAndView.addObject("items", items);
        return modelAndView;
    }
}
