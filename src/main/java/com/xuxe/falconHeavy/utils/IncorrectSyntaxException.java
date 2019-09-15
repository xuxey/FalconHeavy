package com.xuxe.falconHeavy.utils;

public class IncorrectSyntaxException extends Exception {
    private String specialSyntax = "";

    public IncorrectSyntaxException(String specialSyntax) {
        this.specialSyntax = specialSyntax;
    }

    public IncorrectSyntaxException() {
    }

    public String getSpecialSyntax() {
        return specialSyntax;
    }

    public void setSpecialSyntax(String specialSyntax) {
        this.specialSyntax = specialSyntax;
    }
}
