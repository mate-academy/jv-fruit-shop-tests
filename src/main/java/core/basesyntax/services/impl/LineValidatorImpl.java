package core.basesyntax.services.impl;

import core.basesyntax.exceptions.LineParseException;
import core.basesyntax.exceptions.NegativeValueException;
import core.basesyntax.exceptions.NullValueException;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.LineValidator;
import java.util.Arrays;

public class LineValidatorImpl implements LineValidator {
    private static final int VALUES_QUANTITY = 3;
    private static final String CSV_SEPARATOR = ",";

    @Override
    public boolean validate(String line) {
        if (line == null || line.equals("")) {
            throw new LineParseException("Line is empty or null");
        }
        String[] values = Arrays.stream(line.split(CSV_SEPARATOR))
                .map(String::trim)
                .toArray(String[]::new);
        if (values.length != VALUES_QUANTITY) {
            throw new LineParseException("Some argument(s) was(were) lost");
        }
        if (values[0] == null || values[1] == null || values[2] == null
                || values[0].equals("") || values[1].equals("") || values[2].equals("")) {
            throw new NullValueException("Empty value(s) or value(s) is(are) null: "
                    + values[0] + " "
                    + values[1] + " "
                    + values[2]);
        }
        if (!Arrays.stream(Fruit.values())
                .anyMatch(v -> v.getFruitName().equals(values[1]))) {
            throw new LineParseException("Wrong fruit name: " + values[1]);
        }
        if (Integer.valueOf(values[2]) <= 0) {
            throw new NegativeValueException("Quantity of " + values[1] + " is negative");
        }
        if (!Arrays.stream(ActivityType.values())
                .anyMatch(i -> values[0].equals(i.getActivityIdentifier() + ""))) {
            throw new LineParseException("Wrong activity type identifier: " + values[0]);
        }
        return true;
    }
}
