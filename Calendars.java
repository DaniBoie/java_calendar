import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
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
  private List<Event> events;
  // Event deletedEvent; Removed because it is not needed
  private TimeZone timeZone; // set by application
  private Theme theme; // set by application
  private Visibility visibility = Visibility.PRIVATE; // editable

  // public void updateEvent(Event event) {
  // // Implementation here
  // } Likely do not need this becuase events will be updateable from thier direct
  // Class

  public void createEvent(LocalDateTime startTime, LocalDateTime endTime) {
    // Implementation here
    Event newEvent = new Event();
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
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private List<String> sharedUsers;
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

  public void setStartTime(LocalDateTime localTime) {
    this.startTime = localTime;
  }

  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  public void setEndTime(LocalDateTime localTime) {
    this.endTime = localTime;
  }

  public LocalDateTime getEndTime() {
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
          break;
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
              case 1:
                  calendarsApp.unsetUser();
                break;
              case 2:
                  System.out.println("Create Calendar:: Provide a Calendar name");
                  String newCalendarName = scanner.nextLine();
                  calendarsApp.currentUser.addCalendar(newCalendarName);
                break;
              case 3:
                if (hasCalendars) {
                  System.out.println("Here are your Calendars ::");
                  for (Calendar userCalendar : calendarsApp.currentUser.getCalendars()) {
                    System.out.println(userCalendar.getName());
                  }
                  break;
                }
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
                break;
            }

            
          } 
          break;      
        default:
          break;
      }
    }
}

}
