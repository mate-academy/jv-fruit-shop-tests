package core.basesyntax.dto;

import java.util.Set;

public class CsvFruitRecordsValidator implements FruitRecordsValidator {
    private static final String RECORD_SEPARATOR = System.lineSeparator();
    private static final String RECORD_PATTERN = "(((.+)" + RECORD_SEPARATOR + ")|((.+)\\z))+";
    private static final String RECORD_FORM_PATTER = "\\w,(\\w+),(\\d+)";
    private static final int APPEND_TO_REMOVE_TITLE = System.lineSeparator().length();

    @Override
    public boolean validation(String dataInString, Operator operator) {
        if (operator == null) {
            throw new RuntimeException("Operator link is null");
        }
        if (dataInString.length() == 0) {
            throw new RuntimeException("No data in file: " + dataInString);
        }
        dataInString = dataInString.substring(dataInString.indexOf(RECORD_SEPARATOR)
                + APPEND_TO_REMOVE_TITLE);
        if (!dataInString.matches(RECORD_PATTERN)) {
            throw new RuntimeException("Records separated incorrect: " + dataInString);
        }
        String[] arrOfRecords = dataInString.split(RECORD_SEPARATOR);
        Set<Character> typesSet = operator.getTypesOfOperations().keySet();
        for (String strToChek : arrOfRecords) {
            if (!strToChek.matches(RECORD_FORM_PATTER)) {
                throw new RuntimeException("Records incorrect form: " + strToChek);
            }
            if (!typesSet.contains(strToChek.charAt(0))) {
                throw new RuntimeException("No such operation: " + strToChek);
            }
        }
        return true;
    }
}
