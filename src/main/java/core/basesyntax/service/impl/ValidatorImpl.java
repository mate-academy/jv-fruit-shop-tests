package core.basesyntax.service.impl;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.Validator;
import java.util.Arrays;

public class ValidatorImpl implements Validator {
    private static final int VALUES_QUANTITY = 3;

    @Override
    public boolean validate(String[] values) {
        if (values.length != VALUES_QUANTITY
                || values[0] == null
                || !Arrays.stream(ActivityType.values())
                .anyMatch(v -> values[0].equals(v.getActivityIdentifier() + ""))
                || values[1] == null
                || !Arrays.stream(Fruit.values()).anyMatch(v -> v.getFruitName().equals(values[1]))
                || values[2] == null
                || Integer.valueOf(values[2]) <= 0) {
            throw new RuntimeException("Wrong value(s), negative value or null");
        }
        return true;
    }
}
