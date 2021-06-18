package core.basesyntax.services;

import java.util.function.Predicate;

public class RecordValidation implements Predicate<String[]> {
    @Override
    public boolean test(String[] fields) {
        return fields.length == 3
                && !fields[0].equals("")
                && !fields[1].equals("")
                && !fields[2].equals("")
                && Integer.parseInt(fields[2]) >= 0;
    }
}
