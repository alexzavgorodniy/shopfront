package controller;

import java.util.Arrays;
import java.util.List;
import model.Availability;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ModelAndView showAllAvailableItems() {
        ModelAndView modelAndView = new ModelAndView("availabilitySelection");
        modelAndView.addObject("availabilities", Arrays.asList(Availability.values()));
        modelAndView.addObject("selectedAvail", "");
        return modelAndView;
    }

    @PostMapping("/availabilitySelection")
    public String selectedAvailability(@ModelAttribute String selectedAvail) {
        return "redirect:" + "/availabilities/" + selectedAvail;
    }

    @GetMapping("/availabilities/{selectedAvail}")
    public ModelAndView showAvailabilities(@PathVariable String selectedAvail) {
        ModelAndView modelAndView = new ModelAndView("availabilities");
        Availability availabilityOf = Availability.of(selectedAvail);
        List<Item> items = itemRepository.findAllItemsByAvaibility(availabilityOf);
        modelAndView.addObject("items", items);
        return modelAndView;
    }
}
