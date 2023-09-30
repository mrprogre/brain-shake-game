package org.mrprogre;

import org.mrprogre.utils.Common;

public class Main {
    public static final String APP_VERSION = "1.1";
    public static final String APP_VERSION_DATE = "01.10.2023";

    public static void main(String[] args) {
        Common.createFiles();
        Common.createGui();
    }
}