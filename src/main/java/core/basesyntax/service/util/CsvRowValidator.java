package core.basesyntax.service.util;

import core.basesyntax.model.OperationType;

public class CsvRowValidator {
    private static final String OPERATION_CODES = OperationType.getAllCodes();
    private static final String CSV_ROW_PATTERN =
            "[" + OPERATION_CODES + "]" + ",[a-zA-Z]+,\\d+" + "(\\n)?";

    public static void validate(String row) {
        if (row == null) {
            throw new RuntimeException("Error: This row is null!");
        } else if (!row.matches(CSV_ROW_PATTERN)) {
            throw new RuntimeException("Invalid data in row: " + row
                    + " The expected format should be: b,apple,100");
        }
    }
}
