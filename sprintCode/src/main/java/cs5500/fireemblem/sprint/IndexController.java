package cs5500.fireemblem.sprint;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements ErrorController {

  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public String error(){
    return "forward:/index.html";
  }

  public String getErrorPath() {
    return null;
  }
}

