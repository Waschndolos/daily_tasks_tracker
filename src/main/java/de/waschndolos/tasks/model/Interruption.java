package de.waschndolos.tasks.model;

public enum Interruption {


    CALL(1, "btn-primary", "fa-phone"),
    EMAIL(2, "btn-secondary", "fa-envelope"),
    MESSAGE(3, "btn-success", "fa-comments"),
    MEETING(4, "btn-info", "fa-users"),
    HELP_COLLEAGUE(5, "btn-warning", "fa-user-shield"),
    EMERGENCY(6, "btn-danger", "fa-exclamation-triangle"),
    QUICKFIX(7, "btn-light", "fa-wrench"),
    PLAY_RUBBER_DUCK(8, "btn-dark", "fa-play-circle"),
    OTHER(9, "btn-outline-secondary", "fa-question-circle");

    private final int id;
    private final String buttonClass;
    private final String iconClass;

    Interruption(int id, String buttonClass, String iconClass) {
        this.id = id;
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

    public static Interruption getById(int id) {
        for (Interruption interruption : values()) {
            if (interruption.id == id) {
                return interruption;
            }
        }
        throw new IllegalArgumentException("Unmatched id: " + id);
    }
}


