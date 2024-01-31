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

class Calendars {
  public static void main(String[] args) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    String logo = """


         ▄████████    ▄████████  ▄█          ▄████████ ███▄▄▄▄   ████████▄     ▄████████    ▄████████    ▄████████
        ███    ███   ███    ███ ███         ███    ███ ███▀▀▀██▄ ███   ▀███   ███    ███   ███    ███   ███    ███
        ███    █▀    ███    ███ ███         ███    █▀  ███   ███ ███    ███   ███    ███   ███    ███   ███    █▀
        ███          ███    ███ ███        ▄███▄▄▄     ███   ███ ███    ███   ███    ███  ▄███▄▄▄▄██▀   ███
        ███        ▀███████████ ███       ▀▀███▀▀▀     ███   ███ ███    ███ ▀███████████ ▀▀███▀▀▀▀▀   ▀███████████
        ███    █▄    ███    ███ ███         ███    █▄  ███   ███ ███    ███   ███    ███ ▀███████████          ███
        ███    ███   ███    ███ ███▌    ▄   ███    ███ ███   ███ ███   ▄███   ███    ███   ███    ███    ▄█    ███
        ████████▀    ███    █▀  █████▄▄██   ██████████  ▀█   █▀  ████████▀    ███    █▀    ███    ███  ▄████████▀
                                ▀                                                          ███    ███
    """;
    System.out.println(logo);

    CalendarApp calendarsApp = new CalendarApp();
    Scanner scanner = new Scanner(System.in);
    boolean quitApp = false;
    // User Login Loop
    while (!quitApp) {
      System.out.println("\nCreate/Login User:");
      System.out.println("1. Quit Application");
      System.out.println("2. Create User");

      boolean hasUsers = calendarsApp.getUsers().size() > 0;
      if (hasUsers) {
        System.out.println("3. Login User");
      }
      int menuChoice = scanner.nextInt();
      scanner.nextLine();

      switch (menuChoice) {
        case 1:
          quitApp = true;
          break;
        case 2:
          // Register User Choice
          System.out.println("\nRegister Username: (Provide Text for Username)");
          String newUsername = scanner.nextLine();
          calendarsApp.createUser(newUsername);
          System.out.println("Created User:" + newUsername);
          break; // case 1 userChoice
        case 3:

          if (!hasUsers) {
            System.out.println("Invalid Choice, try again");
            break;
          }

          boolean validUser = false;
          // Log In User Choice
          while (!validUser) {
            System.out.println("\nSelect User to Login:");
            for (int i = 0; i < calendarsApp.getUsers().size(); i++) {
              System.out.println(i + ". " + calendarsApp.getUsers().get(i).getUsername());
            }

            int loginUserIndex = scanner.nextInt();
            scanner.nextLine();

            try {
              calendarsApp.loginUser(calendarsApp.getUsers().get(loginUserIndex).getUsername());
              System.out.println("\n" + calendarsApp.currentUser.getUsername() + " Logged In:");
              validUser = true;
            } catch (IndexOutOfBoundsException e) {
              System.out.println("Invalid Choice, try again\n");
            }
          }

          while (calendarsApp.loggedIn) {
            // Calendar functionality here
            System.out.println("\nMain Menu:");
            System.out.println("1. Logout User");
            System.out.println("2. Create Calendar");

            boolean hasCalendars = calendarsApp.currentUser.getCalendars().size() > 0;
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
                break; // case 1 calendarChoice
              // Create Calendar
              case 2:
                System.out.println("\nCreate Calendar:: (Provide Text for Calendar Name)");
                String newCalendarName = scanner.nextLine();
                calendarsApp.currentUser.addCalendar(newCalendarName);
                System.out.println(newCalendarName + " Successfully Created!");
                break; // case 2 calendarChoice
              // Manage Calendar
              case 3:
                if (hasCalendars) {
                  int selectedCalendar = 10000;
                  boolean validChoice = false;
                  List<Calendar> userCalendars = calendarsApp.currentUser.getCalendars();

                  while (!validChoice) {
                    System.out.println("\nSelect which Calendar you would like to manage");
                    for (int i = 0; i < userCalendars.size(); i++) {
                      System.out.println(i + ": " + userCalendars.get(i).getName());
                    }

                    selectedCalendar = scanner.nextInt();
                    scanner.nextLine();

                    if (selectedCalendar > userCalendars.size() - 1 || selectedCalendar < 0) {
                      System.out.println("Invalid Choice, Try Again");
                    } else {
                      validChoice = true;
                    }
                  }

                  String selectedCalendarName = userCalendars.get(selectedCalendar).getName();
                  // Gather and show calendar data here in a pretty form
                  while (selectedCalendarName != "") {

                    System.out.println("\nManaging Calendar: " + selectedCalendarName);
                    System.out.println("Calendar Visibility: " + userCalendars.get(selectedCalendar).getVisibility());
                    System.out.println("1. Back");
                    System.out.println("2. Change Calendar Name");
                    System.out.println("3. Set Calendar Visiblity");
                    System.out.println("4. Create Event");

                    boolean hasEvents = calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents()
                        .size() > 0;

                    if (hasEvents) {
                      System.out.println("5. Manage Events");
                      System.out.println("6. Remove Event");
                    }

                    int calenderMenuChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (calenderMenuChoice) {
                      case 1:
                        selectedCalendarName = "";
                        break; // case 1 eventChoice
                      case 2:
                        System.out.println(
                            "\nProvide a new name for " + selectedCalendarName + ": (Provide Text for Calendar Name)");
                        String newName = scanner.nextLine();
                        calendarsApp.currentUser.getCalendars().get(selectedCalendar).setName(newName);
                        System.out.println(selectedCalendarName + " was changed to: " + newName);
                        selectedCalendarName = newName;

                        break;
                      case 3:
                        System.out.println("\nChoose a Visiblity for " + selectedCalendarName);
                        System.out.println("1. PUBLIC");
                        System.out.println("2. PRIVATE");

                        int visibilityChoice = scanner.nextInt();
                        switch (visibilityChoice) {
                          case 1:
                            calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                                .setVisibility(Visibility.PUBLIC);
                            System.out.println(selectedCalendarName + " is now PUBLIC");
                            break; // case 1 visibilityChoice
                          case 2:
                            calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                                .setVisibility(Visibility.PRIVATE);
                            System.out.println(selectedCalendarName + " is now PRIVATE");
                            break; // case 2 visibilityChoice
                          default:
                            System.out.println("Invalid Choice, Try Again");
                            break; // default case visibilityChoice
                        }
                        break; // case 2 eventChoice
                      case 4:
                        System.out.println("\nChoose a name for this event: (Provide Text for Event Name)");
                        String newEventName = scanner.nextLine();
                        boolean validStart = false;
                        boolean validEnd = false;
                        Date startDateTime = null;
                        Date endDateTime = null;
                        while (!validStart) {
                          try {
                            System.out.println(
                                "\nEnter start date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
                            String startDateTimeString = scanner.nextLine();
                            startDateTime = dateFormat.parse(startDateTimeString);
                            validStart = true;
                          } catch (ParseException e) {
                            System.out.println("Unable to parse date & time, try again");
                          }
                        }
                        while (!validEnd) {
                          try {
                            System.out.println(
                                "\nEnter end date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
                            String endDateTimeString = scanner.nextLine();
                            endDateTime = dateFormat.parse(endDateTimeString);
                            validEnd = true;
                          } catch (ParseException e) {
                            System.out.println("Unable to parse date & time, try again");
                          }
                        }

                        calendarsApp.currentUser.getCalendars().get(selectedCalendar).createEvent(newEventName,
                            startDateTime, endDateTime);
                        System.out.println(newEventName + " has been created!");
                        break; // case 3 eventChoice

                      case 5:
                        // print events here and do the event loop
                        if (hasEvents) {
                          int selectedEvent = 100000;
                          boolean validEventChoice = false;
                          while (!validEventChoice) {
                            System.out.println("\nSelect which Event you would like to manage");

                            for (int i = 0; i < calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                                .getEvents().size(); i++) {
                              System.out.println(i + ": " + calendarsApp.currentUser.getCalendars()
                                  .get(selectedCalendar).getEvents().get(i).getEventName());
                            }

                            selectedEvent = scanner.nextInt();
                            scanner.nextLine();

                            if (selectedEvent > calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                                .getEvents().size() - 1 || selectedEvent < 0) {
                              System.out.println("Invalid Choice, Try Again");
                            } else {
                              validEventChoice = true;
                            }
                          }
                          Event eventSelected = calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                              .getEvents().get(selectedEvent);
                          String selectedEventName = eventSelected.getEventName();

                          while (selectedEventName != "") {
                            System.out.println("\nManaging Event: " + selectedEventName);
                            System.out.println("Start Time: " + eventSelected.getStartTime());
                            System.out.println("End Time: " + eventSelected.getEndTime());

                            System.out.println("1. Back");
                            System.out.println("2. Change Event Name");
                            System.out.println("3. Change Start Time");
                            System.out.println("4. Change End Time");

                            int eventChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (eventChoice) {
                              case 1:
                                selectedEventName = "";
                                break;

                              case 2:
                                System.out.println("\nProvide a new name for " + selectedEventName
                                    + ": (Provide Text for Event Name)");
                                String changeEventName = scanner.nextLine();
                                calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents()
                                    .get(selectedEvent).setEventName(changeEventName);
                                System.out.println(selectedEventName + " was changed to: " + changeEventName);
                                selectedEventName = changeEventName;
                                break;

                              case 3:
                                boolean validTime = false;
                                while (!validTime) {
                                  try {
                                    System.out.println(
                                        "\nEnter a new start date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
                                    String startDateTimeString = scanner.nextLine();
                                    startDateTime = dateFormat.parse(startDateTimeString);
                                    calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents()
                                        .get(selectedEvent).setStartTime(startDateTime);
                                    validTime = true;
                                  } catch (ParseException e) {
                                    System.out.println("Unable to parse date & time, try again");
                                  }
                                }
                                break;
                              case 4:
                                boolean validChangeTime = false;
                                while (!validChangeTime) {
                                  try {
                                    System.out.println(
                                        "\nEnter a new start date and time (dd/MM/yyyy HH:mm:ss): (Provide Text for DateTime)");
                                    String endDateTimeString = scanner.nextLine();
                                    endDateTime = dateFormat.parse(endDateTimeString);
                                    calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents()
                                        .get(selectedEvent).setEndTime(endDateTime);
                                    validChangeTime = true;
                                  } catch (ParseException e) {
                                    System.out.println("Unable to parse date & time, try again");
                                  }
                                }
                                break;
                              default:
                                System.out.println("Invalid Choice, Try Again");
                                break;
                            }

                          }
                          break;
                        }

                      case 6:
                        if (hasEvents) {
                          System.out.println("\nSelect which Event you would like to remove");

                          for (int i = 0; i < calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents()
                              .size(); i++) {
                            System.out.println(i + ": " + calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                                .getEvents().get(i).getEventName());
                          }

                          int selectedEvent = scanner.nextInt();
                          scanner.nextLine();

                          try {
                            calendarsApp.currentUser.getCalendars().get(selectedCalendar)
                                .removeEvent(calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents()
                                    .get(selectedEvent).getEventName());
                          } catch (IndexOutOfBoundsException e) {
                            System.out.println("Invalid Choice, Try Again");
                          }

                          break;
                        }

                      default:
                        System.out.println("Invalid Choice, Try Again");
                        break; // default case calendarMenuChoice
                    }
                  }

                  break; // case 3 calendarChoice
                }
                // Remove Calendar
              case 4:
                if (hasCalendars) {
                  System.out.println("\nSelect which Calendar you would like to remove");
                  List<Calendar> userCalendars = calendarsApp.currentUser.getCalendars();
                  for (int i = 0; i < userCalendars.size(); i++) {
                    System.out.println(i + ": " + userCalendars.get(i).getName());
                  }

                  int removeCalendarIndex = scanner.nextInt();
                  scanner.nextLine();
                  try {
                    calendarsApp.currentUser.removeCalendar(userCalendars.get(removeCalendarIndex).getName());
                  } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid Choice, Try Again");
                  }

                  break;
                }
              default:
                System.out.println("Invalid Choice, Try Again");
                break; // default case calendarChoice
            }

          }
          break; // case 2 userChoice
        default:
          System.out.println("Invalid Choice, Try Again\n");
          break; // default case userChoce
      }
    }

    scanner.close();
  }

}