package core.basesyntax.service.parsers.impl;

import core.basesyntax.model.Activity;
import core.basesyntax.service.Validator;
import core.basesyntax.service.impl.ValidatorImpl;
import core.basesyntax.service.parsers.ActivityParser;
import core.basesyntax.service.parsers.ActivityTypeParser;
import core.basesyntax.service.parsers.FruitParser;
import java.util.Arrays;

public class ActivityParserImpl implements ActivityParser {
    private static final String CSV_SEPARATOR = ",";
    private Validator validator;
    private ActivityTypeParser activityTypeParser;
    private FruitParser fruitParser;

    public ActivityParserImpl() {
        validator = new ValidatorImpl();
        activityTypeParser = new ActivityTypeParserImpl();
        fruitParser = new FruitParserImpl();
    }

    @Override
    public Activity parse(String line) {
        if (line == null) {
            throw new RuntimeException("Line is empty");
        }
        String[] values = Arrays.stream(line.split(CSV_SEPARATOR))
                .map(String::trim)
                .toArray(String[]::new);
        validator.validate(values);
        Activity activity = new Activity.Builder()
                .setActivityType(activityTypeParser.parse(values[0].trim().charAt(0)))
                .setFruit(fruitParser.parse(values[1].trim()))
                .setQuantity(Integer.valueOf(values[2].trim()))
                .build();
        return activity;
    }
}
