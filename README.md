# Task-Tracker

Build a CLI app to track your tasks and manage your to-do list.

<hr>

# Features
  Add, Update, and Delete tasks
  
  Mark a task as in progress or done
  
  List all tasks
  
  List all tasks that are done
  
  List all tasks that are not done
  
  List all tasks that are in progress

## Usage
Adding a new task
java TaskCLIApp add "Buy groceries"

Output: Task added successfully (ID: 1)

Updating a task
java TaskCLIApp update 1 "Buy groceries and cook dinner"

Output: Task updated successfully (ID: 1)

Deleting a task
java TaskCLIApp delete 1

Output: Task deleted successfully (ID: 1)

Marking a task as in progress
java TaskCLIApp mark-in-progress 1

Output: Task marked as in progress (ID: 1)

Marking a task as done
java TaskCLIApp mark-done 1

Output: Task marked as done (ID: 1)

Listing all tasks
java TaskCLIApp list

Output: List of all tasks

Listing tasks by status
java TaskCLIApp list todo
java TaskCLIApp list in-progress
java TaskCLIApp list done

  
