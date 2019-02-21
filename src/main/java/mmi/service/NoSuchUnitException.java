package mmi.service;

public class NoSuchUnitException extends Exception {
    private final String unit;

    NoSuchUnitException(String unit) {
        super("No unit converter function defined for " + unit);
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
