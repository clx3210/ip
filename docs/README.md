# Olivero User Guide

![Product Screenshot](https://github.com/clx3210/ip/blob/master/docs/Ui.png)

**Olivero** is an *angry-penguin-inspired* chatbot that helps you keep track of your tasks. 
If you can type fast, Olivero can get your task management done faster than traditional GUI apps!!!!

## Quick start

Prerequisites: Java 17 installed in your Computer.<br>
**Mac users:** Ensure you have the precise JDK version specified in the CS2103T website.

1. Download the latest `olivero.jar` from [here](https://github.com/clx3210/ip/releases)
2. Open your terminal and navigate to the directory where `olivero.jar` is located.
3. Run the command `java -jar olivero.jar` to start the application.


## Adding todos `todo`

Gets Olivero to add a todo task to your task list.

### Format:

`todo <TASK_DESCRIPTION>`

### Examples:

Input with an initially empty task list:

`todo read book`

Expected Output:

```
Got it. I've added this task:
 [T][] read book
Now you have 1 task(s) in the list.
```

Input with 1 task in list initially:

`todo buy groceries`

Expected Output:

```
Got it. I've added this task:
 [T][] buy groceries
Now you have 2 task(s) in the list.
```

## Adding deadlines `deadline`

Gets Olivero to add a deadline task to your task list.

### Format:

`deadline <TASK_DESCRIPTION> /by <yyyy-mm-dd HHmm>`

### Examples:

Input with an initially empty task list:

`deadline finalize CS2103T iP /by 2025-02-21 2359`

Expected Output:
```
Got it. I've added this task:
 [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
Now you have 1 task(s) in the list.
```

Input with 1 task in list initially:

`deadline study for GESS1025 group project /by 2025-12-22 2359`

Expected Output:
```
Got it. I've added this task:
 [D][] study for GESS1025 group project (by: Dec 22 2025 2359)
Now you have 2 task(s) in the list.
```

## Adding events `event`

Gets Olivero to add an event task to your task list.

### Format:

`event <TASK_DESCRIPTION> /from <yyyy-mm-dd HHmm> /to <yyyy-mm-dd HHmm>`

### Examples:

Input with an originally empty task list:

`event CS2103T lecture /from 2025-01-01 1400 /to 2025-01-01 1600`

Expected Output:
```
Got it. I've added this task:
 [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
Now you have 1 task(s) in the list.
```

Input with 1 task originally in task list:

`event GESS1025 group project meeting /from 2025-12-22 1400 /to 2025-12-22 1600`

Expected Output:
```
Got it. I've added this task:
 [E][] GESS1025 group project meeting (from: Dec 22 2025 1400 to: Dec 22 2025 1600)
Now you have 2 task(s) in the list.
```

## Listing tasks `list`

Lists all currently saved tasks in the task list.

### Format:

`list`

### Example:

Current list of tasks:
```
1. [T][] read book
2. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][] stream on twitch
```

Input:

`list`

Expected Output:

```
Here are the tasks in your list:
1. [T][] read book
2. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][] stream on twitch
```

## Deleting tasks `delete`

Gets Olivero to delete task(s) from the task list.
Up to 100 tasks can be deleted in 1 such command.

### Formats:

1. Single delete: `delete <TASK_NUMBER>`
2. Multiple delete: `delete -m <TASK_NUMBER_1> <TASK_NUMBER_2> ...`
3. Range delete: `delete -m <TASK_NUMBER_START>-<TASK_NUMBER_END>`

### Examples:

Current list of tasks:
```
1. [T][] read book
2. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][] stream on twitch
```

Input (single delete):

`delete 2`

Expected Output:

```
OK, I've removed the following task(s):
1. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
Now you have 4 task(s) in the list.
```

Input (multiple delete, continuing from previously):

`delete -m 1 2`

Expected Output:

```
OK, I've removed the following task(s):
1. [T][] read book
2. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
Now you have 2 task(s) in the list.
```

Input (range delete, continuing from previously):

`delete -m 1-2`

Expected Output:

```
OK, I've removed the following task(s):
1. [T][] watch movie 
2. [T][] stream on twitch
Now you have 0 task(s) in the list.
```

## Marking tasks as done `mark`

Gets olivero to mark task(s) on the task list as done.
Up to 100 tasks can be marked in 1 such command.
Marking an already marked task has no effect on that task.

### Formats:

1. Single mark: `mark <TASK_NUMBER>`
2. Multiple mark: `mark -m <TASK_NUMBER_1> <TASK_NUMBER_2> ...`
3. Range mark: `mark -m <TASK_NUMBER_START>-<TASK_NUMBER_END>`

### Examples:

Current list of tasks:
```
1. [T][] read book
2. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][] stream on twitch
```

Input (single mark):

`mark 1`

Expected Output:

```
Cool! I've marked the following task(s) as done:
1. [T][X] read book
```

Input (multiple mark):

`mark -m 1 2 3`

Expected Output:

```
Cool! I've marked the following task(s) as done:
1. [T][X] read book
2. [D][X] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][X] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
```

Input (ranged mark):

`mark -m 1-5`

Expected Output:

```
Cool! I've marked the following task(s) as done:
1. [T][X] read book
2. [D][X] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][X] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][X] watch movie 
5. [T][X] stream on twitch
```

## Marking tasks as undone/incomplete `unmark`

Marks task(s) on the task list as undone/incomplete.
Up to 100 tasks can be unmarked in 1 such command.
Unmarking an already unmarked task has no effect.

### Formats:
1. Single unmark: `unmark <TASK_NUMBER>`
2. Multiple unmark: `unmark -m <TASK_NUMBER_1> <TASK_NUMBER_2> ...`
3. Range unmark: `unmark -m <TASK_NUMBER_START>-<TASK_NUMBER_END>`

### Examples:

Current list of tasks:
```
1. [T][] read book
2. [D][X] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][X] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][X] stream on twitch
```

Input (single unmark):

`unmark 2`

Expected Output:

```
Alright! I've un-marked the following task(s):
1. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
```

Input (multiple unmark):

`unmark 1 3 5`

Expected Output:

```
Alright! I've un-marked the following task(s):
1. [T][] read book
2. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
3. [T][] stream on twitch
```

Input (range unmark):

`unmark 1-5`

Expected Output:

```
Alright! I've un-marked the following task(s):
1. [T][] read book
2. [D][] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][] stream on twitch
```

# Finding tasks `find`

Finds tasks in the task list that contain the given keyword (cannot be blank).

### Format:

`find <KEYWORD>`

### Examples:

Current list of tasks:
```
1. [T][] read book
2. [D][X] finalize CS2103T iP (by: Feb 21 2025 2359)
3. [E][X] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
4. [T][] watch movie 
5. [T][X] stream on twitch
```

Input:

`find book`

Expected Output:

```
Here are the matching tasks in your list:
1. [T][] read book
```

Input:

`find CS2103T`

Expected Output:

```
Here are the matching tasks in your list:
1. [D][X] finalize CS2103T iP (by: Feb 21 2025 2359)
2. [E][X] CS2103T lecture (from: Jan 01 2025 1400 to: Jan 01 2025 1600)
```

## Exiting the program `bye`

Closes Olivero.

### Format:

`bye`

### Examples:

Input:

`bye`

Expected:

Application closes, no output shown to user.

## Saving data

Olivero will attempt to save all task list data in the hard disk
automatically after any command which modifies the data.
Manual saving is not required.

