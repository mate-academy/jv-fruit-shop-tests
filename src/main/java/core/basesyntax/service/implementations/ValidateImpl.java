package core.basesyntax.service.implementations;

import core.basesyntax.service.inerfaces.Validate;
import java.util.List;
import java.util.regex.Pattern;

public class ValidateImpl implements Validate {
    public static final int INDEX_AFTER_FIRST_ROW = 1;
    private static final Pattern REGEX_FOR_DATA = Pattern.compile("[bprs],[a-z]*,[0-9]*");

    @Override
    public boolean validate(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Empty input or null!");
        }
        for (int i = 0; i < list.size(); i++) {
            if (i != 0 && !REGEX_FOR_DATA.matcher(list.get(i)).matches()
                    || list.size() <= INDEX_AFTER_FIRST_ROW) {
                throw new RuntimeException("Invalid input!");
            }
        }
        return true;
    }
}
