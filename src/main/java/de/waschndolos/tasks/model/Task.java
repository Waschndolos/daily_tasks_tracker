package de.waschndolos.tasks.model;

public class Task {

    private final String name;

    private final boolean isHomeOffice;

    public Task(String name, boolean isHomeOffice) {
        this.name = name;
        this.isHomeOffice = isHomeOffice;
    }

    public String getName() {
        return name;
    }

    public boolean isHomeOffice() {
        return isHomeOffice;
    }
}
