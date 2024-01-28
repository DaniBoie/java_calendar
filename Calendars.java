import java.util.ArrayList;
import java.util.List;
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

  public void setUsername(String name) {
    this.username = name;
  }

  public String getUsername() {
    return this.username;
  }

  // public void updateCalendar(Calendar calendar) {
  //   // Implementation here
  // } We likely don't need this because the user will be able to update the Calendars directly form thier class
}

enum Visibility {
  PUBLIC,
  PRIVATE
}

class Calendar {
  private String name; //editable
  private List<Event> events;
  // Event deletedEvent; Removed because it is not needed 
  private TimeZone timeZone; // set by application
  private Theme theme; // set by application
  private Visibility visibility = Visibility.PRIVATE; // editable

  // public void updateEvent(Event event) {
  //   // Implementation here
  // } Likely do not need this becuase events will be updateable from thier direct Class

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

  public String getName(){
    return this.name;
  }
}

class Event {
  private String name;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  // private String description; Future Implementation

  public void shareUser(User user) {
    // Implementation Here.
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

class Configuration {
  List<TimeZone> timeZones;
  List<Theme> themes;

  public void configureTimeZone(TimeZone timeZone) {
    // Implementation here
  }

  public void changeTheme(Theme theme) {
    // Implementation here
  }
}

class Theme {
  String themeName;

  // Additional methods and attributes as per requirements.
}

class CalendarApp {
  private List<User> users = new ArrayList<User>();
  public User currentUser;

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
        return true;
      }
    }
    // user not registered, prompt again
    return false;
  }
}

class Calendars {
public static void main(String[] args) {
    // code to be executed

    System.out.println("WELCOME TO CALENDARS!");
    CalendarApp calendarsApp = new CalendarApp();

    // Create New User
    calendarsApp.createUser("dani");
    boolean userIsSet = calendarsApp.setUser("dani");
    System.out.println(userIsSet);
    System.out.println(calendarsApp.currentUser);
}

}
