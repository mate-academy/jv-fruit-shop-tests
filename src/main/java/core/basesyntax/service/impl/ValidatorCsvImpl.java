package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import core.basesyntax.service.activitiy.ActivityHandler;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class ValidatorCsvImpl implements Validator {
    private static final String CSV_LINE_PATTERN = "[a-z],[a-z]+,[0-9]+";
    private static final int CSV_LINE_TYPE_LETTER_INDEX = 0;
    private final Map<String, ActivityHandler> activityHandlerMap;

    public ValidatorCsvImpl(Map<String, ActivityHandler> activityHandlerMap) {
        this.activityHandlerMap = activityHandlerMap;
    }

    public boolean validate(List<String> fileData) {
        if (fileData.isEmpty() || fileData.size() == 1
                || !fileData.get(0).equals("type,fruit,quantity")) {
            throw new RuntimeException("Input file have invalid header");
        }
        Predicate<String> csvLinePredicate = line -> !(Pattern.matches(CSV_LINE_PATTERN, line)
                && activityHandlerMap
                .containsKey(String.valueOf(line.charAt(CSV_LINE_TYPE_LETTER_INDEX)))
                && line.charAt(line.lastIndexOf(',') + 1) != '0');
        boolean haveInvalidLines = IntStream.range(1, fileData.size())
                .mapToObj(fileData::get)
                .anyMatch(csvLinePredicate);
        if (haveInvalidLines) {
            throw new RuntimeException("Input file have invalid line(s)");
        }
        return true;
    }
}
