package tasktracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskCLI {

    static final Path FILE_PATH = Path.of("src","tasktracker","tasks.json");

    static List<Task> tasks = new ArrayList<>();

    TaskCLI(){
      tasks = loadTask();
    }

    public static void main(String[] args) {

      TaskCLI taskCLI = new TaskCLI();

        if(args.length == 0){
            System.out.println("Please enter the task name");
            return;
        }

        String command = args[0];

        switch (command) {

//           Adding a new task
            case "add":
                addTask(args[1]);
                break;

//           Updating  tasks
            case "update":
                updateTask(args[1],args[2]);
                break;

//           Listing all tasks
            case "list":
              if(args.length <2){
                listTask("All");
              }else{
                Status filterStatus;
                try{
                  filterStatus = Status.valueOf(args[1].toUpperCase().replace("-","_"));
                }catch (IllegalArgumentException e){
                  System.out.println("Invalid Status :"+ args[1]);
                  return;
                }
                listTask(filterStatus.toString());
              }
              break;

//          Deleting tasks
            case "delete":
              deleteTask(args[1]);
              break;

//          Marking a task as in progress
            case "mark-in-progress":
              markInProgress(args[1]);
              break;

//           Marking a task as done
            case "mark-done":
              markDone(args[1]);
              break;
            default:
                System.out.println("Invalid command");
        }
    }

   static void markInProgress(String id) {
      Task task = tasks.stream().filter((t)->(t.id == Integer.parseInt(id))).findFirst().orElseThrow(() -> new IllegalArgumentException("Task " + id+ " does not exist"));
        task.status = Status.IN_PROGRESS;
        saveTasks();
  }

  static void markDone(String id){
      Task task = tasks.stream().filter((t)->(t.id == Integer.parseInt(id))).findFirst().orElseThrow(()->new IllegalArgumentException("Task " + id + " does not exist"));
      task.status = Status.DONE;
      saveTasks();
  }

  static void addTask(String description){
        int id = tasks.isEmpty() ? 1 : tasks.get(tasks.size()-1).id +1;
        String now = LocalDate.now().toString();
        Task task = new Task(id,description,Status.TODO,now,now);
        tasks.add(task);
        saveTasks();
        System.out.println("Task added successfully for ID:  "+ id);

    }

    static void deleteTask(String id){
      Task task = tasks.stream().filter((t) -> t.id == Integer.parseInt(id)).findFirst().orElseThrow(()->new IllegalArgumentException("Task " + id + " does not exist"));
      tasks.remove(task);
      saveTasks();
      System.out.println("Task deleted successfully for ID:  "+ id);
    }

    static void updateTask(String id, String description){
      Task task = tasks.stream().filter((t) -> t.id == Integer.parseInt(id)).findFirst().orElseThrow(()->new IllegalArgumentException("Task " + id + " does not exist"));
      for(Task t: tasks){
        if(t.id==Integer.parseInt(id)){
          task = t;
          break;
        }
      }
      task.description = description;
      saveTasks();
    }

  List<Task> loadTask(){
    List<Task> tasks = new ArrayList<>();
    try{
      if(!Files.exists(FILE_PATH)){
        return new ArrayList<>();
      }
      String content = Files.readString(FILE_PATH);
      if(content.isBlank()){
        return new ArrayList<>();
      }
      String[] lines = content.replace("[","").replace("]","").split("},");
      for(String line: lines){
        if(!line.endsWith("}")){
          line +="}";
          tasks.add(Task.fromJson(line));
        }else{
          tasks.add(Task.fromJson(line));
        }
      }
    }catch (IOException e){
      e.printStackTrace();
    }
    return tasks;
  }

    static void listTask(String status){
      System.out.println("Tasks listed successfully: ");
      System.out.printf("%-5s | %-20s | %-12s%n", "ID", "Description", "Status");
      System.out.println("--------------------------------------------------");
      for (Task task : tasks) {
        if(task.status.equals(status) || status.equals("All")){
          System.out.printf("%-5d | %-20s | %-12s%n",
                  task.id,
                  task.description,
                  task.status);
        }
      }
    }

  static void saveTasks() {
    StringBuilder sb = new StringBuilder();
    sb.append("[\n");
    for(int i =0; i< tasks.size();i++){
      sb.append(tasks.get(i).toJson());
      if(i<tasks.size()-1){
        sb.append(",\n");
      }
    }
    sb.append("\n]");
    try{
      Files.writeString(FILE_PATH,sb.toString());
    }catch (IOException e){
      e.printStackTrace();
    }
  }
}
