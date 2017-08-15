package practice.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class WelcomeController {


    @RequestMapping("/")
    public String home() {
        return "menu";
    }

    @RequestMapping("/menu")
    public String homeMenu() {
        return "menu";
    }
}
