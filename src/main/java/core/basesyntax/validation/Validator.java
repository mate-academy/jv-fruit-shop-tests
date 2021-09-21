package core.basesyntax.validation;

import java.util.Arrays;
import java.util.List;

public class Validator {
    private static final int ACTIVITY_POSITION = 0;
    private static final int FRUIT_AMOUNT_POSITION = 2;
    private static final int VALUES_IN_LINE = 3;
    private static final List<String> ACTIVITIES = Arrays.asList("b", "p", "r", "s");

    public static boolean validate(List<String> lines) {
        if (lines.size() <= 1) {
            return false;
        }
        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            if (!ACTIVITIES.contains(values[ACTIVITY_POSITION])) {
                return false;
            }
            if (values.length < VALUES_IN_LINE || values.length > VALUES_IN_LINE) {
                return false;
            } else if (Integer.parseInt(values[FRUIT_AMOUNT_POSITION]) < 0) {
                return false;
            }
        }
        return true;
    }
}
