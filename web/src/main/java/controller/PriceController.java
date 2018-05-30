package controller;

import java.util.List;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import repository.ItemRepository;

@Controller
public class PriceController {

    private ItemRepository repository;

    @Autowired
    public PriceController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/priceSelection")
    public ModelAndView showPriceSelectionPage() {
        ModelAndView modelAndView = new ModelAndView("priceSelection");
        modelAndView.addObject("minPrice", 0d);
        modelAndView.addObject("maxPrice", 0d);
        return modelAndView;
    }

    @PostMapping("/priceSelection")
    public String selectedPrices(Double minPrice, Double maxPrice) {
        if (minPrice == null || minPrice < 0) {
            minPrice = 0d;
        }
        if (maxPrice == null || maxPrice < 0) {
            maxPrice = Double.MAX_VALUE;
        }
        return "redirect:" + "/prices/" + minPrice + "/" + maxPrice;
    }

    @GetMapping("/prices/{minPrice}/{maxPrice}")
    public ModelAndView showItemsInSelectedRange(@PathVariable Double minPrice,
            @PathVariable Double maxPrice) {
        ModelAndView modelAndView = new ModelAndView("prices");
        List<Item> items = repository.findAllItemsByPrice(minPrice, maxPrice);
        modelAndView.addObject("items", items);
        return modelAndView;
    }
}
