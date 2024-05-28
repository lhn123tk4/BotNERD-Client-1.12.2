package study.botnerd.utils;

import java.util.Objects;

public class Setting {
    private String name;
    private double value;
    private double minValue;
    private double maxValue;
    private boolean onlyIntegers;

    public Setting(String name, double defaultValue, double minValue, double maxValue, boolean onlyIntegers) {
        this.name = name;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.onlyIntegers = onlyIntegers;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        if (value >= minValue && value <= maxValue) {
            if (onlyIntegers) {
                this.value = (int) value;
            } else {
                this.value = value;
            }
        }
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public boolean isOnlyIntegers() {
        return onlyIntegers;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", onlyIntegers=" + onlyIntegers +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, minValue, maxValue, onlyIntegers);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Setting setting = (Setting) obj;
        return Double.compare(setting.value, value) == 0 &&
                Double.compare(setting.minValue, minValue) == 0 &&
                Double.compare(setting.maxValue, maxValue) == 0 &&
                onlyIntegers == setting.onlyIntegers &&
                Objects.equals(name, setting.name);
    }
}
