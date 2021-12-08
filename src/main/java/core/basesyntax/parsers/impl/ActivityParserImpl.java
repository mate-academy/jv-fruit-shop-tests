package core.basesyntax.parsers.impl;

import core.basesyntax.model.Activity;
import core.basesyntax.parsers.ActivityParser;
import core.basesyntax.parsers.ActivityTypeParser;
import core.basesyntax.parsers.FruitParser;
import core.basesyntax.services.LineValidator;
import core.basesyntax.services.impl.LineValidatorImpl;
import java.util.Arrays;

public class ActivityParserImpl implements ActivityParser {
    private static final String CSV_SEPARATOR = ",";
    private LineValidator lineValidator;
    private ActivityTypeParser activityTypeParser;
    private FruitParser fruitParser;

    public ActivityParserImpl() {
        lineValidator = new LineValidatorImpl();
        activityTypeParser = new ActivityTypeParserImpl();
        fruitParser = new FruitParserImpl();
    }

    @Override
    public Activity parse(String line) {
        String[] values = Arrays.stream(line.split(CSV_SEPARATOR))
                .map(String::trim)
                .toArray(String[]::new);
        Activity activity = new Activity.Builder()
                .setActivityType(activityTypeParser.parse(values[0].trim().charAt(0)))
                .setFruit(fruitParser.parse(values[1].trim()))
                .setQuantity(Integer.valueOf(values[2].trim()))
                .build();
        return activity;
    }
}
