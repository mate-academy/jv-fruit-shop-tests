package core.basesyntax.shop.item;

public class Fruit {
    private String name;
    private Integer quality;

    public Fruit(String name, Integer quality) {
        this.name = name;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public Integer getQuality() {
        return quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return name.equals(fruit.name);
    }

}
