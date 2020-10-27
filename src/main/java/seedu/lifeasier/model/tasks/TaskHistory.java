package seedu.lifeasier.model.tasks;

import seedu.lifeasier.ui.Ui;

import java.util.ArrayList;

/**
 * The TaskHistory class represents the list of changes made to Task objects,
 * edits or deletions in particular.
 */
public class TaskHistory {

    private int changeCount;
    private int editCount;
    private int deleteCount;

    private ArrayList<Task> taskHistory;

    private static final int DEFAULT_EDIT_NUMBER = -999999;

    public TaskHistory() {
        this.changeCount = 0;
        this.editCount = 0;
        this.deleteCount = 0;
        this.taskHistory = new ArrayList<>();
    }

    public Task getLastTask() {
        return taskHistory.get(changeCount - 1);
    }

    public int getEditCount() {
        return editCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void incrementChangeCount() {
        this.changeCount++;
    }

    public void decrementChangeCount() {
        this.changeCount--;
    }

    public void pushOldCopy(Task oldCopyOfTask, Ui ui) {
        taskHistory.add(oldCopyOfTask);
        incrementChangeCount();
    }

    public void popLastTask() {
        int indexOfLastTask = changeCount - 1;
        taskHistory.remove(indexOfLastTask);
        decrementChangeCount();
    }

    /**
     * Returns a copy of the Task object before it is edited.
     *
     * @param tasks represents the TaskList object.
     * @param userIndexChoice the index of the Task object the user wants to edit.
     *
     */
    public Task getCurrCopyOfTaskToEdit(TaskList tasks, int userIndexChoice) {
        Task task = tasks.getTask(userIndexChoice);

        int taskEditNumber = task.getEditNumber();
        int editID;

        if (taskEditNumber == DEFAULT_EDIT_NUMBER) {
            editID = getEditCount() + 1;
            task.setEditNumber(editID);
        } else {
            editID = taskEditNumber;
        }
        editCount++;

        return copyTask(tasks, userIndexChoice, task, editID);
    }

    /**
     * Returns a copy of the Task object before it is deleted.
     *
     * @param tasks represents the TaskList object.
     * @param userIndexChoice the index of the Task object the user wants to delete.
     *
     */
    public Task getCurrCopyOfTaskToDelete(TaskList tasks, int userIndexChoice) {
        Task task = tasks.getTask(userIndexChoice);

        int taskEditNumber = task.getEditNumber();
        int deleteID;

        if (taskEditNumber == DEFAULT_EDIT_NUMBER) {
            deleteID = getDeleteCount() - 1;
            task.setEditNumber(deleteID);
        } else {
            deleteID = taskEditNumber;
        }
        deleteCount--;

        return copyTask(tasks, userIndexChoice, task, deleteID);
    }

    public Task copyTask(TaskList tasks, int userIndexChoice, Task task, int editID) {
        if (task instanceof Deadline) {
            return new Deadline(tasks.getTask(userIndexChoice), editID);
        } else if (task instanceof Event) {
            return new Event(tasks.getTask(userIndexChoice), editID);
        } else {
            return new Lesson(tasks.getTask(userIndexChoice), editID);
        }
    }

    public void printTaskHistory() {
        for (Task t : taskHistory) {
            System.out.println(t.toString() + " " + t.getEditNumber());
        }
    }
}