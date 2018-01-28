package com.company;

final class Log {
    static public void info(String toLog, String describe) {
        String view = ">> INFO! Information about -> " + describe + ";\n";
        view += ">> Value of this variable -> " + toLog + ";\n";
        System.out.print(view + "\n");
    }

    static public void error(String toError, String describe) {
        String view = ">> Error! Information about -> " + describe + ";\n";
        view += ">> Value of this variable -> " + toError + ";\n";
        System.out.print(view + "\n");
    }

    static public void header(String header_name) {
        System.out.print("\n------------------{ " + header_name + " }---------------------\n");
    }
}
