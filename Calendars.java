import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

class User {
  private String username;
  private List<Calendar> calendars = new ArrayList<Calendar>();

  public void addCalendar(String title) {
    // Implementation here
    Calendar newCalendar = new Calendar();
    newCalendar.setName(title);
    this.calendars.add(newCalendar);
  }

  public void removeCalendar(String title) {
    // Implementation here
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

  // public void updateCalendar(Calendar calendar) {
  // // Implementation here
  // } We likely don't need this because the user will be able to update the
  // Calendars directly form thier class
}

enum Visibility {
  PUBLIC,
  PRIVATE
}

class Calendar {
  private String name; // editable
  private List<Event> events = new ArrayList<Event>();
  // Event deletedEvent; Removed because it is not needed
  private TimeZone timeZone; // set by application
  // private Theme theme; // set by application
  private Visibility visibility = Visibility.PRIVATE; // editable

  // public void updateEvent(Event event) {
  // // Implementation here
  // } Likely do not need this becuase events will be updateable from thier direct
  // Class

  public List<Event> getEvents() {
    return this.events;
  } 

  public void createEvent(String name, Date startTime, Date endTime) {
    // Implementation here
    Event newEvent = new Event();
    newEvent.setEventName(name);
    newEvent.setStartTime(startTime);
    newEvent.setEndTime(endTime);

    this.events.add(newEvent);
  }

  public void removeEvent(String eventName) {
    // Implementation here
    for (int i = 0; i < this.events.size(); i++) {
      if (this.events.get(i).getEventName().equals(eventName)) {
        this.events.remove(i);
      }
    }
  }

  public void setVisibility(Visibility visibility) {
    // Implementation here
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
  private List<String> sharedUsers = new ArrayList<String>();
  // private String description; Future Implementation

  public void shareUser(String username) {
    this.sharedUsers.add(username);
  }

  public List<String> getSharedUsers() {
    return this.sharedUsers;
  }

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

// class Configuration {
// List<TimeZone> timeZones;
// List<Theme> themes;

// public void configureTimeZone(TimeZone timeZone) {
// // Implementation here
// }

// public void changeTheme(Theme theme) {
// // Implementation here
// }
// }

// class Theme {
// String themeName;

// // Additional methods and attributes as per requirements.
// } Hopefully not needed for the 1/2 requirement minimum.

class CalendarApp {
  private List<User> users = new ArrayList<User>();
  public User currentUser;
  public boolean loggedIn = false;

  public void createUser(String name) {
    User newUser = new User();
    newUser.setUsername(name);
    this.users.add(newUser);
  }

  public boolean setUser(String name) {
    for (User user : this.users) {
      if (user.getUsername().equals(name)) {
        this.currentUser = user;
        // user is registered, send to next instructions. (Managing User Calendars)
        this.loggedIn = true;
        return true;
      }
    }
    // user not registered, prompt again
    this.loggedIn = false;
    return false;
  }

  public void unsetUser() {

    this.currentUser = null;
    this.loggedIn = false;
  }

  public List<User> getUsers() {
    return this.users;
  }
}

class Calendars {
public static void main(String[] args) {
    // code to be executed
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    System.out.println("WELCOME TO CALENDARS!");
    CalendarApp calendarsApp = new CalendarApp();
    Scanner scanner = new Scanner(System.in);

    // User Login Loop
    while (true) {
      System.out.println("Choose an option:");
      System.out.println("1. Create User");
      System.out.println("2. Login User");

      int menuChoice = scanner.nextInt();
      scanner.nextLine();

      switch (menuChoice) {
        case 1:
          // Register User Choice
            System.out.println("Register User :: Provide a username (used for login)");
            String newUsername = scanner.nextLine();
            calendarsApp.createUser(newUsername);
            System.out.println("Created User:" + newUsername);
          break; // case 1 userChoice
        case 2:
          // Log In User Choice
          System.out.println("Login User :: Provide username to login");
          String loginUsername = scanner.nextLine();

          boolean userSet = calendarsApp.setUser(loginUsername);
          if (userSet) {
            System.out.println("Logged in User :: " + calendarsApp.currentUser.getUsername());
          } else {
            System.out.println("Invalid Username, try again");
            break;
          }

          while (calendarsApp.loggedIn) {
            // Calendar functionality here

            System.out.println("Choose an option:");
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
                  calendarsApp.unsetUser();
                break; // case 1 calendarChoice
              // Create Calendar
              case 2:
                  System.out.println("Create Calendar:: Provide a Calendar name");
                  String newCalendarName = scanner.nextLine();
                  calendarsApp.currentUser.addCalendar(newCalendarName);
                break; // case 2 calendarChoice
              // Manage Calendar
              case 3:
                if (hasCalendars) {
                  System.out.println("Select which Calendar you would like to manage");
                  List<Calendar> userCalendars = calendarsApp.currentUser.getCalendars();
                  for (int i = 0; i < userCalendars.size(); i++) {
                    System.out.println(i + ": " + userCalendars.get(i).getName());
                  }

                  int selectedCalendar = scanner.nextInt();
                  scanner.nextLine();
                  
                  String selectedCalendarName = userCalendars.get(selectedCalendar).getName();
                  // Gather and show calendar data here in a pretty form
                  while (selectedCalendarName != "") {
            
                      System.out.println("Managing " + selectedCalendarName + ":");
                      System.out.println("1. Back");
                      System.out.println("2. Change Calendar Name");
                      System.out.println("3. Set Calendar Visiblity");
                      System.out.println("4. Create Event");

                      boolean hasEvents = calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().size() > 0;

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
                          System.out.println("Provide a new name for " + selectedCalendarName);
                          String newName = scanner.nextLine();
                          calendarsApp.currentUser.getCalendars().get(selectedCalendar).setName(newName);
                          System.out.println(selectedCalendarName + " was changed to: " + newName);
                          selectedCalendarName = newName;

                          break;
                        case 3:
                            System.out.println("Choose a Visiblity for " + selectedCalendarName);
                            System.out.println("1. PUBLIC");
                            System.out.println("2. PRIVATE");

                            int visibilityChoice = scanner.nextInt();
                            switch (visibilityChoice) {
                              case 1:
                                  calendarsApp.currentUser.getCalendars().get(selectedCalendar).setVisibility(Visibility.PUBLIC);
                                  System.out.println(selectedCalendarName + " is now PUBLIC");
                                break; // case 1 visibilityChoice
                              case 2:
                                  calendarsApp.currentUser.getCalendars().get(selectedCalendar).setVisibility(Visibility.PRIVATE);
                                  System.out.println(selectedCalendarName + " is now PRIVATE");
                                break; // case 2 visibilityChoice                       
                              default:
                                break; // default case visibilityChoice
                            }
                          break; // case 2 eventChoice
                        case 4:
                            System.out.println("Choose a name for this event:");
                            String newEventName = scanner.nextLine();
                            boolean validStart = false;
                            boolean validEnd = false;
                            Date startDateTime = null;
                            Date endDateTime = null;
                            while (!validStart) {
                              try {
                                System.out.println("Enter start date and time (dd/MM/yyyy HH:mm:ss): ");
                                String startDateTimeString = scanner.nextLine();
                                startDateTime = dateFormat.parse(startDateTimeString);    
                                validStart = true;
                              } catch (ParseException e) {
                                // TODO: handle exception
                                System.out.println("Unable to parse date & time, try again");
                              }
                            }
                            while (!validEnd) {
                              try {
                                System.out.println("Enter end date and time (dd/MM/yyyy HH:mm:ss): ");
                                String endDateTimeString = scanner.nextLine();
                                endDateTime = dateFormat.parse(endDateTimeString);
                                validEnd = true;
                              } catch (ParseException e) {
                                // TODO: handle exception
                                System.out.println("Unable to parse date & time, try again");
                              }
                            }

                            calendarsApp.currentUser.getCalendars().get(selectedCalendar).createEvent(newEventName, startDateTime, endDateTime);
                            System.out.println(newEventName + " has been created!");
                          break; // case 3 eventChoice

                        case 5:
                            // print events here and do the event loop
                            if (hasEvents) {
                              System.out.println("Select which Event you would like to manage");
                              
                              for (int i = 0; i < calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().size(); i++) {
                                System.out.println(i + ": " + calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(i).getEventName());
                              }

                              int selectedEvent = scanner.nextInt();
                              scanner.nextLine();

                              String selectedEventName = calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).getEventName();
                              System.out.println("Managing " + selectedEventName);

                              while (selectedEventName != "") {
                                System.out.println("Choose an option:");
                                System.out.println("1. Back");
                                System.out.println("2. Share With User");
                                System.out.println("3. Change Event Name");
                                System.out.println("4. Change Start Time");
                                System.out.println("5. Change End Time");

                                int eventChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (eventChoice) {
                                  case 1:
                                      selectedEventName = "";
                                    break;
                                  case 2:
                                      System.out.println("Share User Functionality Here");
                                    break;

                                  case 3:
                                    System.out.println("Provide a new name for " + selectedEventName);
                                    String changeEventName = scanner.nextLine();
                                    calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).setEventName(changeEventName);
                                    System.out.println(selectedEventName + " was changed to: " + changeEventName);
                                    selectedEventName = changeEventName;
                                    break;

                                  case 4:
                                    boolean validTime = false;
                                    while (!validTime) {
                                      try {
                                        System.out.println("Enter a new start date and time (dd/MM/yyyy HH:mm:ss): ");
                                        String startDateTimeString = scanner.nextLine();
                                        startDateTime = dateFormat.parse(startDateTimeString);
                                        calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).setStartTime(startDateTime);
                                        validTime = true;
                                      } catch (ParseException e) {
                                        // TODO: handle exception
                                        System.out.println("Unable to parse date & time, try again");
                                      }
                                    }
                                    break;
                                  case 5:
                                    boolean validChangeTime = false;
                                    while (!validChangeTime) {
                                      try {
                                        System.out.println("Enter a new start date and time (dd/MM/yyyy HH:mm:ss): ");
                                        String endDateTimeString = scanner.nextLine();
                                        endDateTime = dateFormat.parse(endDateTimeString);
                                        calendarsApp.currentUser.getCalendars().get(selectedCalendar).getEvents().get(selectedEvent).setEndTime(endDateTime);
                                        validChangeTime = true;
                                      } catch (ParseException e) {
                                        // TODO: handle exception
                                        System.out.println("Unable to parse date & time, try again");
                                      }
                                    }
                                    break;
                                  default:
                                    break;
                                }

                              }
                              break;
                            }
                          
                        default: 
                        // error handle here
                          break; // default case eventChoice
                      }
                  }

                  break; // case 3 calendarChoice
                }
              // Remove Calendar
              case 4:
                if (hasCalendars) {
                  System.out.println("Select which Calendar you would like to remove");
                  List<Calendar> userCalendars = calendarsApp.currentUser.getCalendars();
                  for (int i = 0; i < userCalendars.size(); i++) {
                    System.out.println(i + ": " + userCalendars.get(i).getName());
                  }

                  int removeCalendarIndex = scanner.nextInt();
                  scanner.nextLine();

                  calendarsApp.currentUser.removeCalendar(userCalendars.get(removeCalendarIndex).getName());
                  break;
                }
              default:
              System.out.println("Invalid Choice, please re-try.");
                break; // default case calendarChoice
            }

            
          } 
          break; // case 2 userChoice
        default:
          break; // default case userChoce
      }
    }
}

}

// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

// System.out.println("Enter start date and time (dd/MM/yyyy HH:mm:ss): ");
// String startDateTimeString = scanner.nextLine();
// Date startDateTime = dateFormat.parse(startDateTimeString);

// System.out.println("Enter end date and time (dd/MM/yyyy HH:mm:ss): ");
// String endDateTimeString = scanner.nextLine();
// Date endDateTime = dateFormat.parse(endDateTimeString);