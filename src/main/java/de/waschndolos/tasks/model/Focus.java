package de.waschndolos.tasks.model;

public enum Focus {

    BACK_TO_WORK( "btn-success", "fa-building");

    private final int id = 0;
    private final String buttonClass;
    private final String iconClass;

    Focus(String buttonClass, String iconClass) {
        this.buttonClass = buttonClass;
        this.iconClass = iconClass;
    }

    public String getButtonClass() {
        return buttonClass;
    }

    public String getIconClass() {
        return iconClass;
    }

    public int getId() {
        return id;
    }
}
