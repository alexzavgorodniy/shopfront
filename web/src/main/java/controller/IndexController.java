package controller;

import java.util.List;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import repository.ItemRepository;

@Controller
public class IndexController {

    private ItemRepository itemRepository;

    @Autowired
    public IndexController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping({"/","/index"})
    public ModelAndView show() {
        return new ModelAndView("index");
    }

    @GetMapping("/viewSelection")
    public ModelAndView showViewSelection() {
        return new ModelAndView("viewSelection");
    }

    @GetMapping("/administrationPage")
    public ModelAndView showAdministrationPage() {
        return new ModelAndView("administrationPage");
    }
}
