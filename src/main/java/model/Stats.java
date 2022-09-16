package model;

public class Stats {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public String toString() {
        return "Stats{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
