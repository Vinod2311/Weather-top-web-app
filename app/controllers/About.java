package controllers;

import play.Logger;
import play.mvc.Controller;

/**
 * Controller class used to display About page
 */
public class About extends Controller {
  public static void index() {
    Logger.info("Rendering about");
    render("about.html");
  }
}
