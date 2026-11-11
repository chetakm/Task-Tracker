package tasktracker;

import java.util.Locale;

public class Task {
    int id;
    String description;
    Status status;
    String createdAt;
    String updatedAt;

    public Task(int id, String description, Status status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String toJson() {
        return "{\"id\":\"" + id + "\", \"description\":\"" + description.strip() + "\", \"status\":\"" + status.toString() +
                "\", \"createdAt\":\"" + createdAt + "\", \"updatedAt\":\"" + updatedAt + "\"}";
    }
    public static Task fromJson(String json){
        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] jsonData = json.split(",");
        String id = jsonData[0].split(":")[1].strip();
        String description = jsonData[1].split(":")[1].strip();
        Status status = Status.valueOf(jsonData[2].split(":")[1].strip().toUpperCase().replace(" ","_"));
        String createdAt = jsonData[3].split(":")[1].strip();
        String updatedAt = jsonData[4].split(":")[1].strip();
        return new Task(Integer.parseInt(id),description,status,createdAt,updatedAt);
    }
}
