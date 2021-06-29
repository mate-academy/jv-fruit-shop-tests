package core.basesyntax.services;

import java.util.function.Predicate;

public class RecordValidation implements Predicate<String[]> {
    @Override
    public boolean test(String[] fields) {
        return fields.length == 3
                && !fields[0].isEmpty()
                && !fields[1].isEmpty()
                && !fields[2].isEmpty()
                && Integer.parseInt(fields[2]) >= 0;
    }
}
