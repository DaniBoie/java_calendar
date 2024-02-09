import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class User {
  private String username;
  private List<Calendar> calendars = new ArrayList<Calendar>();

  public void addCalendar(String title) {
    Calendar newCalendar = new Calendar();
    newCalendar.setName(title);
    this.calendars.add(newCalendar);
  }

  public void removeCalendar(String title) {
    for (int i = 0; i < this.calendars.size(); i++) {
      if (this.calendars.get(i).getName().equals(title)) {
        this.calendars.remove(i);
      }
    }
  }

  public List<Calendar> getCalendars() {
    return this.calendars;
  }

  public void setUsername(String name) {
    this.username = name;
  }

  public String getUsername() {
    return this.username;
  }
}

enum Visibility {
  PUBLIC,
  PRIVATE
}

class Calendar {
  private String name;
  private List<Event> events = new ArrayList<Event>();
  private Visibility visibility = Visibility.PRIVATE;

  public List<Event> getEvents() {
    return this.events;
  }

  // Create Parameter Object for createEvent
  public void createEvent(String name, Date startTime, Date endTime) {
    Event newEvent = new Event();
    newEvent.setEventName(name);
    newEvent.setStartTime(startTime);
    newEvent.setEndTime(endTime);

    this.events.add(newEvent);
  }

  public void removeEvent(String eventName) {
    for (int i = 0; i < this.events.size(); i++) {
      if (this.events.get(i).getEventName().equals(eventName)) {
        this.events.remove(i);
      }
    }
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  public Visibility getVisibility() {
    return this.visibility;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}

class Event {
  private String name;
  private Date startTime;
  private Date endTime;

  public void setEventName(String name) {
    this.name = name;
  }

  public String getEventName() {
    return this.name;
  }

  public void setStartTime(Date localTime) {
    this.startTime = localTime;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  public void setEndTime(Date localTime) {
    this.endTime = localTime;
  }

  public Date getEndTime() {
    return this.endTime;
  }

}

class CalendarApp {

  private List<User> users = new ArrayList<User>();
  public User currentUser;
  public boolean loggedIn = false;

  // !REFACTOR SINGLETON!
  private static CalendarApp calendarApp;

  private CalendarApp() {
  }

  public static CalendarApp getInstance() {
    if (calendarApp == null) {
      calendarApp = new CalendarApp();
    }
    return calendarApp;
  }

  // !REFACTOR SINGLETON!
  public void createUser(String name) {
    User newUser = new User();
    newUser.setUsername(name);
    this.users.add(newUser);
  }

  public boolean loginUser(String name) {
    for (User user : this.users) {
      if (user.getUsername().equals(name)) {
        this.currentUser = user;
        this.loggedIn = true;
        return true;
      }
    }
    this.loggedIn = false;
    return false;
  }

  public void logoutUser() {

    this.currentUser = null;
    this.loggedIn = false;
  }

  public List<User> getUsers() {
    return this.users;
  }
}

// Created Facade due to long message chains when managing application
// !REFACTOR FACADE START!
class CalendarAppFacade {

  CalendarApp app = CalendarApp.getInstance();

  // User Related Methods
  void createUser(String name) {
    app.createUser(name);
  }

  boolean hasUsers() {
    return app.getUsers().size() > 0;
  }

  int getUserCount() {
    return app.getUsers().size();
  }

  String getUsername() {
    return app.currentUser.getUsername();
  }

  String getUsernameAt(int userIndex) {
    return app.getUsers().get(userIndex).getUsername();
  }

  boolean isLoggedIn() {
    return app.loggedIn;
  }

  boolean loginUser(String name) {
    return app.loginUser(name);
  }

  public void logoutUser() {
    app.logoutUser();
  }

  // Calendar Related Methods
  void addUserCalendar(String calendarName) {
    app.currentUser.addCalendar(calendarName);
  }

  boolean userHasCalendars() {
    return app.currentUser.getCalendars().size() > 0;
  }

  int getUserCalendarCount() {
    return app.currentUser.getCalendars().size();
  }

  String getUserCalendarNameAt(int calendarIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getName();
  }

  Visibility getUserCalendarVisibilityAt(int calendarIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getVisibility();
  }

  void setUserCalendarName(int calendarIndex, String calendarName) {
    app.currentUser.getCalendars().get(calendarIndex).setName(calendarName);
  }

  void setUserCalendarVisibility(int calendarIndex, Visibility visibility) {
    app.currentUser.getCalendars().get(calendarIndex).setVisibility(visibility);
  }

  void removeUserCalendar(String calendarName) {
    app.currentUser.removeCalendar(calendarName);
  }

  // Event Related Methods
  void addCalendarEvent(int calendarIndex, String eventName, Date startTime, Date endTime) {
    app.currentUser.getCalendars().get(calendarIndex).createEvent(eventName, startTime, endTime);
  }

  boolean userCalendarHasEvents(int calendarIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getEvents().size() > 0;
  }

  int getCalendarEventCount(int calendarIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getEvents().size();
  }

  String getCalendarEventNameAt(int calendarIndex, int eventIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getEvents().get(eventIndex).getEventName();
  }

  Date getCalendarEventStartAt(int calendarIndex, int eventIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getEvents().get(eventIndex).getStartTime();
  }

  Date getCalendarEventEndAt(int calendarIndex, int eventIndex) {
    return app.currentUser.getCalendars().get(calendarIndex).getEvents().get(eventIndex).getEndTime();
  }

  void setCalendarEventNameAt(int calendarIndex, int eventIndex, String eventName) {
    app.currentUser.getCalendars().get(calendarIndex).getEvents().get(eventIndex).setEventName(eventName);
  }

  void setCalendarEventStartAt(int calendarIndex, int eventIndex, Date startTime) {
    app.currentUser.getCalendars().get(calendarIndex).getEvents().get(eventIndex).setStartTime(startTime);
  }

  Date setCalendarEventEndAt(int calendarIndex, int eventIndex, Date endTime) {
    return app.currentUser.getCalendars().get(calendarIndex).getEvents().get(eventIndex).getEndTime();
  }

  void removeCalendarEvent(int calendarIndex, String name) {
    app.currentUser.getCalendars().get(calendarIndex).removeEvent(name);
  }
}
// !REFACTOR FACADE!

class Calendars {

  static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  static CalendarAppFacade calendarsApp = new CalendarAppFacade();
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("""


             ▄████████    ▄████████  ▄█          ▄████████ ███▄▄▄▄   ████████▄     ▄████████    ▄████████    ▄████████
            ███    ███   ███    ███ ███         ███    ███ ███▀▀▀██▄ ███   ▀███   ███    ███   ███    ███   ███    ███
            ███    █▀    ███    ███ ███         ███    █▀  ███   ███ ███    ███   ███    ███   ███    ███   ███    █▀
            ███          ███    ███ ███        ▄███▄▄▄     ███   ███ ███    ███   ███    ███  ▄███▄▄▄▄██▀   ███
            ███        ▀███████████ ███       ▀▀███▀▀▀     ███   ███ ███    ███ ▀███████████ ▀▀███▀▀▀▀▀   ▀███████████
            ███    █▄    ███    ███ ███         ███    █▄  ███   ███ ███    ███   ███    ███ ▀███████████          ███
            ███    ███   ███    ███ ███▌    ▄   ███    ███ ███   ███ ███   ▄███   ███    ███   ███    ███    ▄█    ███
            ████████▀    ███    █▀  █████▄▄██   ██████████  ▀█   █▀  ████████▀    ███    █▀    ███    ███  ▄████████▀
                                    ▀                                                          ███    ███
        """);

    appLoop();
  }

  // Helper Methods For appLoop()
  static void createUserInput() {
    System.out.println("\nRegister Username: (Provide Text for Username)");
    String newUsername = scanner.nextLine();
    calendarsApp.createUser(newUsername);
    System.out.println("Created User:" + newUsername);
  }

  // Main App Loop
  static void appLoop() {
    boolean quitApp = false;

    while (!quitApp) {
      System.out.println("\nCreate/Login User:");
      System.out.println("1. Quit Application");
      System.out.println("2. Create User");

      // boolean hasUsers = calendarsApp.getUsers().size() > 0;
      boolean hasUsers = calendarsApp.hasUsers();
      if (hasUsers) {
        System.out.println("3. Login User");
      }
      int menuChoice = scanner.nextInt();
      scanner.nextLine();

      switch (menuChoice) {
        // Quit Application
        case 1:
          quitApp = true;
          break;
        // Create User
        case 2:
          createUserInput();
          break;
        // Login User
        case 3:
          if (!hasUsers) {
            System.out.println("Invalid Choice, try again");
            break;
          }
          userLoginLoop();
          break;
        // Fallback
        default:
          System.out.println("Invalid Choice, Try Again\n");
          break;
      }
    }
  }

  // Helper Methods for userLoginLoop()
  static void userLoginLoopInput() {
    boolean validUser = false;
    // Log In User Choice
    while (!validUser) {
      System.out.println("\nSelect User to Login:");
      // for (int i = 0; i < calendarsApp.getUsers().size(); i++) {
      for (int i = 0; i < calendarsApp.getUserCount(); i++) {
        // System.out.println(i + ". " + calendarsApp.getUsers().get(i).getUsername());
        System.out.println(i + ". " + calendarsApp.getUsernameAt(i));
      }

      int loginUserIndex = scanner.nextInt();
      scanner.nextLine();

      try {
        // calendarsApp.loginUser(calendarsApp.getUsers().get(loginUserIndex).getUsername());
        calendarsApp.loginUser(calendarsApp.getUsernameAt(loginUserIndex));
        // System.out.println("\n" + calendarsApp.currentUser.getUsername() + " Logged In:");
        System.out.println("\n" + calendarsApp.getUsername() + " Logged In:");
        validUser = true;
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Invalid Choice, try again\n");
      }
    }
  }

  static void removeCalendarInput() {
    System.out.println("\nSelect which Calendar you would like to remove");
    // List<Calendar> userCalendars = calendarsApp.currentUser.getCalendars();
    // for (int i = 0; i < userCalendars.size(); i++) {
    for (int i = 0; i < calendarsApp.getUserCalendarCount(); i++) {
      // System.out.println(i + ": " + userCalendars.get(i).getName());
      System.out.println(i + ": " + calendarsApp.getUserCalendarNameAt(i));
    }

    int removeCalendarIndex = scanner.nextInt();
    scanner.nextLine();
    try {
      // calendarsApp.currentUser.removeCalendar(userCalendars.get(removeCalendarIndex).getName());
      calendarsApp.removeUserCalendar(calendarsApp.getUserCalendarNameAt(removeCalendarIndex));
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Invalid Choice, Try Again");
    }
  }

  static void createCalendarInput() {
    System.out.println("\nCreate Calendar:: (Provide Text for Calendar Name)");
    String newCalendarName = scanner.nextLine();
    // calendarsApp.currentUser.addCalendar(newCalendarName);
    calendarsApp.addUserCalendar(newCalendarName);
    System.out.println(newCalendarName + " Successfully Created!");
  }

  // Loop while user is logged in
  static void userLoginLoop() {
    userLoginLoopInput();

    // while (calendarsApp.loggedIn) {
    while (calendarsApp.isLoggedIn()) {
      System.out.println("\nMain Menu:");
      System.out.println("1. Logout User");
      System.out.println("2. Create Calendar");

      // boolean hasCalendars = calendarsApp.currentUser.getCalendars().size() > 0;
      boolean hasCalendars = calendarsApp.userHasCalendars();
      if (hasCalendars) {
        System.out.println("3. Manage Calendars");
        System.out.println("4. Remove Calendar");
      }

      int calendarChoice = scanner.nextInt();
      scanner.nextLine();

      switch (calendarChoice) {
        // Logout User
        case 1:
          calendarsApp.logoutUser();
          break;
        // Create Calendar
        case 2:
          createCalendarInput();
          break;
        // Manage Calendar
        case 3:
          if (hasCalendars) {
            calendarLoop();
            break;
          }
        // Remove Calendar
        case 4:
          if (hasCalendars) {
            removeCalendarInput();
            break;
          } 
        // Fallback
        default:
          System.out.println("Invalid Choice, Try Again");
          break;
      }

    }
  }

  // Helper Methods for calendarLoop()
  static int calendarLoopInput() {
    int selectedCalendar = 10000;
    boolean validChoice = false;
    // Facade decouples the need to hold calendars by providing new functions
    // List<Calendar> userCalendars = calendarsApp.currentUser.getCalendars();
    int userCalendarCount = calendarsApp.getUserCalendarCount();
    while (!validChoice) {
      System.out.println("\nSelect which Calendar you would like to manage");
      // for (int i = 0; i < userCalendars.size(); i++) {
      for (int i = 0; i < userCalendarCount; i++) {
        // System.out.println(i + ": " + userCalendars.get(i).getName());
        System.out.println(i + ": " + calendarsApp.getUserCalendarNameAt(i));
      }

      selectedCalendar = scanner.nextInt();
      scanner.nextLine();

      // if (selectedCalendar > userCalendars.size() - 1 || selectedCalendar < 0) {
      if (selectedCalendar > userCalendarCount - 1 || selectedCalendar < 0) {
        System.out.println("Invalid Choice, Try Again");
      } else {
        validChoice = true;
      }
    }

    return selectedCalendar;
  }

  static String calendarNameInput(int selectedCalendar) {
    String selectedCalendarName = calendarsApp.getUserCalendarNameAt(selectedCalendar);
    System.out.println("\nProvide a new name for " + selectedCalendarName + ": (Provide Text for Calendar Name)");
    String newName = scanner.nextLine();
    // calendarsApp.currentUser.getCalendars().get(selectedCalendar).setName(newName);
    calendarsApp.setUserCalendarName(selectedCalendar, newName);
    System.out.println(selectedCalendarName + " was changed to: " + newName);
    return newName;
  }

  static void calendarVisibilityInput(int selectedCalendar) {
    String selectedCalendarName = calendarsApp.getUserCalendarNameAt(selectedCalendar);
    System.out.println("\nChoose a Visiblity for " + selectedCalendarName);
    System.out.println("1. PUBLIC");
    System.out.println("2. PRIVATE");

    int visibilityChoice = scanner.nextInt();
    switch (visibilityChoice) {
      case 1:
        // calendarsApp.currentUser.getCalendars().get(selectedCalendar).setVisibility(Visibility.PUBLIC);
        calendarsApp.setUserCalendarVisibility(selectedCalendar, Visibility.PUBLIC);
        System.out.println(selectedCalendarName + " is now PUBLIC");
        break;
      case 2:
        // calendarsApp.currentUser.getCalendars().get(selectedCalendar).setVisibility(Visibility.PRIVATE);
        calendarsApp.setUserCalendarVisibility(selectedCalendar, Visibility.PRIVATE);
        System.out.println(selectedCalendarName + " is now PRIVATE");
        break;
      default:
        System.out.println("Invalid Choice, Try Again");
        break;
    }
  }

  static void createEventInput(int selectedCalendar) {
    System.out.println("\nChoose a name for this event: (Provide Text for Event Name)");
    String newEventName = scanner.nextLine();
    boolean validStart = false;
    boolean validEnd = false;
    Date startDateTime = null;
    Date endDateTime = null;
    while (!validStart) {
      try {
        System.out.println("\nEnter start date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
        String startDateTimeString = scanner.nextLine();
        startDateTime = dateFormat.parse(startDateTimeString);
        validStart = true;
      } catch (ParseException e) {
        System.out.println("Unable to parse date & time, try again");
      }
    }
    while (!validEnd) {
      try {
        System.out.println("\nEnter end date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
        String endDateTimeString = scanner.nextLine();
        endDateTime = dateFormat.parse(endDateTimeString);
        validEnd = true;
      } catch (ParseException e) {
        System.out.println("Unable to parse date & time, try again");
      }
    }

    // calendarsApp.currentUser.getCalendars().get(selectedCalendar).createEvent(newEventName, startDateTime, endDateTime);
    calendarsApp.addCalendarEvent(selectedCalendar, newEventName, startDateTime, endDateTime);
    System.out.println(newEventName + " has been created!");
  }

  static void removeEventInput(int selectedCalendar) {
    System.out.println("\nSelect which Event you would like to remove");

    // for (int i = 0; i <
    // calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().size();
    // i++) {
    for (int i = 0; i < calendarsApp.getCalendarEventCount(selectedCalendar); i++) {
      // System.out.println(i + ": " +
      // calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(i).getEventName());
      System.out.println(i + ": " + calendarsApp.getCalendarEventNameAt(selectedCalendar, i));
    }

    int selectedEvent = scanner.nextInt();
    scanner.nextLine();

    try {
      // calendarsApp.currentUser.getCalendars().get(selectedCalendar).removeEvent(calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).getEventName());
      calendarsApp.removeCalendarEvent(selectedCalendar,
          calendarsApp.getCalendarEventNameAt(selectedCalendar, selectedEvent));
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Invalid Choice, Try Again");
    }
  }

  // Loop while calendar is selected
  static void calendarLoop() {

    int selectedCalendar = calendarLoopInput();
    // String selectedCalendarName = userCalendars.get(selectedCalendar).getName();
    String selectedCalendarName = calendarsApp.getUserCalendarNameAt(selectedCalendar);

    // Gather and show calendar data here in a pretty form
    while (selectedCalendarName != "") {

      System.out.println("\nManaging Calendar: " + selectedCalendarName);
      // System.out.println("Calendar Visibility: " + userCalendars.get(selectedCalendar).getVisibility());
      System.out.println("Calendar Visibility: " + calendarsApp.getUserCalendarVisibilityAt(selectedCalendar));
      System.out.println("1. Back");
      System.out.println("2. Change Calendar Name");
      System.out.println("3. Set Calendar Visiblity");
      System.out.println("4. Create Event");

      // boolean hasEvents = calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().size() > 0;
      boolean hasEvents = calendarsApp.userCalendarHasEvents(selectedCalendar);

      if (hasEvents) {
        System.out.println("5. Manage Events");
        System.out.println("6. Remove Event");
      }

      int calenderMenuChoice = scanner.nextInt();
      scanner.nextLine();

      switch (calenderMenuChoice) {
        // Back
        case 1:
          selectedCalendarName = "";
          break;
        // Change Calendar Name
        case 2:
          selectedCalendarName = calendarNameInput(selectedCalendar);
          break;
        // Set Calendar Visibility
        case 3:
          calendarVisibilityInput(selectedCalendar);
          break;
        // Create Event
        case 4:
          createEventInput(selectedCalendar);
          break;
        // Manage Events
        case 5:
          if (hasEvents) {
            eventLoop(selectedCalendar);
            break;
          }
        // Remove Event
        case 6:
          if (hasEvents) {
            removeEventInput(selectedCalendar);
            break;
          }
        // Fallback
        default:
          System.out.println("Invalid Choice, Try Again");
          break;
      }
    }
    
  }

  // Helper Methods for eventLoop()
  static int eventLoopInput(int selectedCalendar) {
    int selectedEvent = 100000;
    boolean validEventChoice = false;
    while (!validEventChoice) {
      System.out.println("\nSelect which Event you would like to manage");

      // for (int i = 0; i < calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().size(); i++) {
      for (int i = 0; i < calendarsApp.getCalendarEventCount(selectedCalendar); i++) {
        // System.out.println(i + ": " + calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(i).getEventName());
        System.out.println(i + ": " + calendarsApp.getCalendarEventNameAt(selectedCalendar, i));
      }

      selectedEvent = scanner.nextInt();
      scanner.nextLine();

      // if (selectedEvent > calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().size() - 1 || selectedEvent < 0) {
      if (selectedEvent > calendarsApp.getCalendarEventCount(selectedCalendar) - 1 || selectedEvent < 0) {
        System.out.println("Invalid Choice, Try Again");
      } else {
        validEventChoice = true;
      }
    }

    // Event eventSelected = calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent);
    // String selectedEventName = eventSelected.getEventName();
    return selectedEvent;
  }

  static String changeEventNameInput(int selectedCalendar, int selectedEvent) {
    String selectedEventName = calendarsApp.getCalendarEventNameAt(selectedCalendar, selectedEvent);
    System.out.println("\nProvide a new name for " + selectedEventName + ": (Provide Text for Event Name)");
    String changeEventName = scanner.nextLine();
    // calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).setEventName(changeEventName);
    calendarsApp.setCalendarEventNameAt(selectedCalendar, selectedEvent, changeEventName);
    System.out.println(selectedEventName + " was changed to: " + changeEventName);
    return changeEventName;
  }

  static void changeEventStartInput(int selectedCalendar, int selectedEvent) {
    boolean validTime = false;
    while (!validTime) {
      try {
        System.out.println(
            "\nEnter a new start date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
        String startDateTimeString = scanner.nextLine();
        Date startDateTime = dateFormat.parse(startDateTimeString);
        // calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).setStartTime(startDateTime);
        calendarsApp.setCalendarEventStartAt(selectedCalendar, selectedEvent, startDateTime);
        validTime = true;
      } catch (ParseException e) {
        System.out.println("Unable to parse date & time, try again");
      }
    }
  }

  static void changeEventEndInput(int selectedCalendar, int selectedEvent) {
    boolean validChangeTime = false;
    while (!validChangeTime) {
      try {
        System.out.println(
            "\nEnter a new start date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
        String endDateTimeString = scanner.nextLine();
        Date endDateTime = dateFormat.parse(endDateTimeString);
        // calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).setEndTime(endDateTime);
        calendarsApp.setCalendarEventEndAt(selectedCalendar, selectedEvent, endDateTime);
        validChangeTime = true;
      } catch (ParseException e) {
        System.out.println("Unable to parse date & time, try again");
      }
    }
  }

  // Loop while event is selected
  static void eventLoop(int selectedCalendar) {

    int selectedEvent = eventLoopInput(selectedCalendar);
    String selectedEventName = calendarsApp.getCalendarEventNameAt(selectedCalendar, selectedEvent);

    while (selectedEventName != "") {
      System.out.println("\nManaging Event: " + selectedEventName);
      // System.out.println("Start Time: " + eventSelected.getStartTime());
      // System.out.println("End Time: " + eventSelected.getEndTime());
      System.out.println("Start Time: " + calendarsApp.getCalendarEventStartAt(selectedCalendar, selectedEvent));
      System.out.println("End Time: " + calendarsApp.getCalendarEventEndAt(selectedCalendar, selectedEvent));

      System.out.println("1. Back");
      System.out.println("2. Change Event Name");
      System.out.println("3. Change Start Time");
      System.out.println("4. Change End Time");

      int eventMenuChoice = scanner.nextInt();
      scanner.nextLine();

      switch (eventMenuChoice) {
        // Back
        case 1:
          selectedEventName = "";
          break;
        // Change Event Name
        case 2:
          selectedEventName = changeEventNameInput(selectedCalendar, selectedEvent);
          break;
        // Change Start Time
        case 3:
          changeEventStartInput(selectedCalendar, selectedEvent);
          break;
        // Change End Time
        case 4:
          changeEventEndInput(selectedCalendar, selectedEvent);
          break;
        // Fallback
        default:
          System.out.println("Invalid Choice, Try Again");
          break;
      }

    }
  }
}
