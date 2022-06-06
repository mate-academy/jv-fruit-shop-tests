package core.basesyntax.java.core.basesyntax.service.impl;

import core.basesyntax.java.core.basesyntax.model.FruitTransaction;
import core.basesyntax.java.core.basesyntax.service.SplitDataValidator;

public class SplitDataValidatorCsvImpl implements SplitDataValidator {
    @Override
    public boolean isNotValid(String[] data) {
        if (data.length != 3) {
            return true;
        }
        FruitTransaction.Operation[] operations = FruitTransaction.Operation.values();
        boolean valid = true;
        for (FruitTransaction.Operation operation : operations) {
            if (data[SplitServiceCsvImpl.Index.TYPE.ordinal()].equals(operation.getOperation())) {
                valid = false;
                break;
            }
        }
        if (!valid && data[SplitServiceCsvImpl.Index.QUANTITY.ordinal()].matches("\\d+")) {
            return false;
        }
        return true;
    }
}
