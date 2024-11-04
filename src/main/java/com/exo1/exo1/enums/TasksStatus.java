package com.exo1.exo1.enums;

public enum TasksStatus {
    A_FAIRE("À faire"),
    EN_COURS("En cours"),
    TERMINE("Terminé");

    private final String label;

    TasksStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
