# Cove User Guide

Cove is a desktop chatbot that helps you manage your tasks. 
Type commands into the chat window and Cove will keep track of your to-dos, 
deadlines, and events, saving everything automatically so nothing is lost 
between sessions.

---

## Features

### Adding a To-Do Task
Adds a task with no date attached.

**Command:** `todo DESCRIPTION`

**Example:**
```
todo buy groceries
```

---

### Adding a Deadline Task
Adds a task that must be completed by a specific date.

**Command:** `deadline DESCRIPTION /by yyyy/MM/dd`

**Example:**
```
deadline submit assignment /by 2025/03/15
```

---

### Adding an Event Task
Adds a task that has a start and end date.

**Command:** `event DESCRIPTION /from yyyy/MM/dd /to yyyy/MM/dd`

**Example:**
```
event vacation /from 2025/04/01 /to 2025/04/03
```

---

### Listing All Tasks
Displays all tasks currently in your list.

**Command:** `list`

---

### Marking a Task as Done
Marks the specified task as completed.

**Command:** `mark TASK_NUMBER`

**Example:**
```
mark 2
```

---

### Marking a Task as Not Done
Marks the specified task as incomplete.

**Command:** `unmark TASK_NUMBER`

**Example:**
```
unmark 2
```

---

### Deleting a Task
Removes the specified task from your list.

**Command:** `delete TASK_NUMBER`

**Example:**
```
delete 3
```

---

### Finding Tasks by Keyword
Searches for tasks whose descriptions contain the given keyword (case-insensitive). Results are shown with their original task numbers from your full list.

**Command:** `find KEYWORD`

**Example:**
```
find assignment
```

---

### Updating a Task
Updates a single field of an existing task. Only one field can be updated per command.

**Command:** `update TASK_NUMBER /FIELD NEW_VALUE`

| Task Type | Accepted Fields          |
|-----------|--------------------------|
| ToDo      | `/desc`                  |
| Deadline  | `/desc`, `/by`           |
| Event     | `/desc`, `/from`, `/to`  |

**Examples:**
```
update 1 /desc buy milk and eggs
update 2 /by 2025/03/20
update 3 /from 2025/04/02
update 3 /to 2025/04/05
```

---

### Exiting Cove
Sends a farewell message. You have to close the window manually 
to exit the application.

**Command:** `bye`

---

## Date Format

All dates must be entered in **`yyyy/MM/dd`** format.

| Input        | Meaning          |
|--------------|------------------|
| `2025/03/15` | 15 March 2025    |
| `2025/12/01` | 1 December 2025  |

---

## Data Storage

Your tasks are saved automatically to `./data/cove.txt` after every change. There is no need to save manually. The tasks are loaded from the file automatically the next time you start Cove.