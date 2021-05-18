package controllers;

import models.Member;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.mvc.Controller;

/**
 * Controller class to handle request to fetch a member.
 * Register and Authenticate logins
 */
public class Accounts extends Controller {
  public static void signup() {
    render("signUp.html");
  }

  public static void login() {
    render("login.html");
  }

  /**
   * Fetch the member who is logged in and renders their details on
   * the member page
   */
  public static void member() {
    Member member = getLoggedInMember();
    render("member.html", member);
  }

  /**
   * Changes the details of the logged in member and saves to the database.
   *
   * @param firstname
   * @param lastname
   * @param email
   * @param password
   */
  public static void editMemberDetails(String firstname, String lastname, String email, String password) {
    try {
      Member member = getLoggedInMember();
      member.firstname = StringUtils.capitalize(firstname);
      member.lastname = StringUtils.capitalize(lastname);
      member.password = password;
      member.email = email;
      member.save();
      redirect("/member");
    } catch (Exception e) {
      System.err.println("Caught Exception: " + e);
      render("error.html");
    }
  }

  /**
   * Creates and saves a Member object with inputted details.
   *
   * @param firstname
   * @param lastname
   * @param email
   * @param password
   */
  public static void register(String firstname, String lastname, String email, String password) {
    Logger.info("Registering new user " + email);
    Member member = new Member(StringUtils.capitalize(firstname), StringUtils.capitalize(lastname), email, password);
    member.save();
    redirect("/");
  }

  /**
   * Authenticates user details and starts a session
   *
   * @param email
   * @param password
   */
  public static void authenticate(String email, String password) {
    Logger.info("Attempting to authenticate with " + email + ":" + password);
    try {
      Member member = Member.findByEmail(email);
      if ((member != null) && (member.checkPassword(password) == true)) {
        Logger.info("Authentication successful");
        session.put("logged_in_Memberid", member.id);
        redirect("/dashboard");
      } else {
        Logger.info("Authentication failed");
        redirect("/login");
      }
    } catch (Exception e) {
      System.err.println("Caught Exception: " + e);
      render("error.html");
    }
  }

  /**
   * Ends session
   */
  public static void logout() {
    session.clear();
    redirect("/");
  }

  /**
   * Fetch logged in member
   *
   * @return logged in member
   */
  public static Member getLoggedInMember() {
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
    } else {
      login();

    }
    return member;
  }
}