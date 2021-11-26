package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.service.Operation.getOperationsString;

import core.basesyntax.shop.service.Validator;

public class ValidatorImpl implements Validator {
    @Override
    public boolean validate(String table) {
        if (table.matches("(?si)^type,fruit,quantity(?m)"
                + "(\\n[" + getOperationsString() + "],\\w+,\\d{1,4})+$")) {
            return true;
        }
        throw new RuntimeException("Corrupted file data");
    }
}
