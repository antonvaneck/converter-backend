package mmi.domain;

import java.util.Objects;

public class UnitValue {
    private final String unit;
    private final double value;

    public UnitValue(String unit, double value) {
        this.unit = unit;
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitValue unitValue = (UnitValue) o;
        return Double.compare(unitValue.value, value) == 0 &&
                Objects.equals(unit, unitValue.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, value);
    }

    @Override
    public String toString() {
        return "UnitValue{" +
                "unit=" + unit +
                ", value=" + value +
                '}';
    }
}
