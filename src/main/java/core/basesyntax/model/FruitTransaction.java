package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private ActionType type;
    private String labelGoods;
    private int value;

    public FruitTransaction(ActionType type, String labelGoods, int value) {
        this.type = type;
        this.labelGoods = labelGoods;
        this.value = value;
    }

    public ActionType getType() {
        return type;
    }

    public String getLabelGoods() {
        return labelGoods;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return value == that.value && type == that.type
                && Objects.equals(labelGoods, that.labelGoods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, labelGoods, value);
    }

    public enum ActionType {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String code;

        ActionType(String code) {
            this.code = code;
        }

        public String getType() {
            return code;
        }
    }
}
