package tasktracker;

public enum Status {
  TODO("todo"),
  IN_PROGRESS("In Progress"),
  DONE("done");

  private final String value;

  Status(String value){
    this.value = value;
  }



}
