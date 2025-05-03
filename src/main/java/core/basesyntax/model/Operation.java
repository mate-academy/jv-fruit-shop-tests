package core.basesyntax.model;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Operation getOperationByCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Code cannot be null");
        }
        for (Operation operation : Operation.values()) {
            if (operation.getCode().equals(code)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }
}
