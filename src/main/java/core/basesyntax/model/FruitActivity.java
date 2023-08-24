package core.basesyntax.model;

import core.basesyntax.service.exceptions.UnsupportedFruitActivityException;
import java.util.Arrays;
import java.util.Objects;

public class FruitActivity {
    private final Type activityType;
    private final String fruitName;
    private final Integer quantity;

    public FruitActivity(Type activityType, String fruitName, Integer quantity) {
        this.activityType = activityType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FruitActivity)) {
            return false;
        }
        FruitActivity other = (FruitActivity) object;
        return Objects.equals(activityType, other.activityType)
                && Objects.equals(fruitName, other.fruitName)
                && Objects.equals(quantity, other.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityType, fruitName, quantity);
    }

    public Type getActivityType() {
        return activityType;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public enum Type {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        Type(String code) {
            this.code = code.replaceAll(" ", "");
        }

        public String getCode() {
            return code;
        }

        public static Type getType(String code) {
            return Arrays.stream(Type.values())
                    .filter(t -> t.getCode().equals(code))
                    .findFirst().orElseThrow(() -> new UnsupportedFruitActivityException(code));
        }
    }
}
