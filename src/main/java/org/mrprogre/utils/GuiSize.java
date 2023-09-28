package org.mrprogre.utils;

public enum GuiSize {
    LARGE("large"),
    MIDDLE("middle"),
    SMALL("small"),
    BABY("baby");

    private final String size;

    GuiSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}