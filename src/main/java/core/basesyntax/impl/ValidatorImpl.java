package core.basesyntax.impl;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.Validator;

public class ValidatorImpl implements Validator {
    private static final int QUANTITY_COLUMN = 2;

    @Override
    public boolean validateRecord(FruitRecordDto record) {
        if (record.getAmount() <= 0 || record.getType() == null || record.getFruit() == null) {
            throw new RuntimeException("Record have mistakes "
                    + record.getType() + ", " + record.getFruit().getName() + ", "
            + record.getAmount());
        }
        return true;
    }

    public boolean validAmount(String line) {
        try {
            int num = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new RuntimeException("quantity must me a number, expected: number,"
                    + " but was: " + line);
        }
        return true;
    }
}
