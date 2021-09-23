package core.basesyntax.service.validators;

import core.basesyntax.model.ActivityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputValidatorImpl implements InputValidator {
    private static final int HEAD_STRING = 0;
    private static final int ACTIVITY_TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String TYPE = "type";
    private static final String FRUIT = "fruit";
    private static final String QUANTITY = "quantity";
    private static final String SPLITTER = ",";
    private static final String emptyDataMessage = "Input data is empty";
    private static final String invalidHeadMessage = "Input head is invalid";
    private static final String emptyDataLineMessage = "Data line is empty";
    private static final String invalidActivityMessage = "Invalid parameters in Activity";
    private static final String invalidNumberOfColumnsInDataLineMessage
            = "Invalid number of columns in data line";
    private static final String notUniqueBalanceElementsMessage
            = "Invalid number of columns in data line";
    private static final String fruitNotInStorage
            = "Founded fruit not in Storage";

    @Override
    public void validate(List<String> data) throws IllegalArgumentException {
        if (data == null || data.size() == 0) {
            throw new IllegalArgumentException(emptyDataMessage);
        }
        String head = data.get(HEAD_STRING);
        headValidation(head);
        List<String> dataToValidate = new ArrayList<>(List.copyOf(data));
        dataToValidate.remove(HEAD_STRING);
        for (String activity : dataToValidate) {
            activityValidation(activity);
        }
        Map<String, Long> balanceElementsMap = 
                uniquenessOfBalanceElementsValidationAndMapper(dataToValidate);
        Set<String> fruits = balanceElementsMap.keySet();
        fruitsExistInStorageValidation(dataToValidate, fruits);
    }

    private void headValidation(String head) throws IllegalArgumentException {
        String[] headElements = baseValidationAndSplitter(head);
        if (!TYPE.equals(headElements[ACTIVITY_TYPE_INDEX])
                || !FRUIT.equals(headElements[FRUIT_INDEX])
                || !QUANTITY.equals(headElements[AMOUNT_INDEX])) {
            throw new IllegalArgumentException(invalidHeadMessage);
        }
    }

    private void activityValidation(String activity) throws IllegalArgumentException {
        String[] activityElements = baseValidationAndSplitter(activity);
        if (activityElements[ACTIVITY_TYPE_INDEX].isEmpty()
                || activityElements[FRUIT_INDEX].isEmpty()
                || numericValueOfStringOrThrows(activityElements[AMOUNT_INDEX]) < 0) {
            throw new IllegalArgumentException(invalidActivityMessage);
        }
    }

    private int numericValueOfStringOrThrows(String strNum) throws IllegalArgumentException {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(invalidActivityMessage);
        }
    }

    private String[] baseValidationAndSplitter(String dataLine) throws IllegalArgumentException {
        if (dataLine == null || dataLine.isEmpty()) {
            throw new IllegalArgumentException(emptyDataLineMessage);
        }
        String[] lineElements;
        if ((lineElements = dataLine.split(SPLITTER)).length != 3) {
            throw new IllegalArgumentException(invalidNumberOfColumnsInDataLineMessage);
        }
        return lineElements;
    }

    private Map<String, Long> uniquenessOfBalanceElementsValidationAndMapper(
            List<String> data) throws IllegalArgumentException {
        Map<String, Long> balanceElementsMap = data.stream()
                .map(s -> s.split(SPLITTER))
                .filter(s -> ActivityType.valueOf(s[ACTIVITY_TYPE_INDEX]).equals(ActivityType.b))
                .map(s -> s[FRUIT_INDEX])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        if (balanceElementsMap.values().stream().anyMatch(i -> i > 1)) {
            throw new IllegalArgumentException(notUniqueBalanceElementsMessage);
        }
        return balanceElementsMap;
    }
    
    private void fruitsExistInStorageValidation(
            List<String> data, Set<String> fruits) throws IllegalArgumentException {
        if (data.stream()
                .map(s -> s.split(SPLITTER))
                .anyMatch(s -> !fruits.contains(s[FRUIT_INDEX]))) {
            throw new IllegalArgumentException(fruitNotInStorage);
        }
    }
}
