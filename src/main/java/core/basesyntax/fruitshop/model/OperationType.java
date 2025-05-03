package core.basesyntax.fruitshop.model;

import core.basesyntax.fruitshop.service.validators.InvalidDataException;

public enum OperationType {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private static final String INVALID_OPERATION_NOTIFICATION = "No such operation available";
    private final String shortName;

    OperationType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static OperationType valueOfLabel(String shortName) {
        for (OperationType o: values()) {
            if (o.getShortName().equals(shortName)) {
                return o;
            }
        }
        throw new InvalidDataException(INVALID_OPERATION_NOTIFICATION);
    }

}
