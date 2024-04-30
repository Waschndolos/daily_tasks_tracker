package de.waschndolos.tasks.model;

public class Task {

    private final int value;

    private final boolean isHomeOffice;

    public Task(int value, boolean isHomeOffice) {
        this.value = value;
        this.isHomeOffice = isHomeOffice;
    }

    public int getValue() {
        return value;
    }

    public boolean isHomeOffice() {
        return isHomeOffice;
    }
}
