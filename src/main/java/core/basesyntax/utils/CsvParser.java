package core.basesyntax.utils;

import core.basesyntax.exception.CsvParseException;
import core.basesyntax.transactions.FruitsTransaction;
import core.basesyntax.transactions.FruitsTransaction.Operation;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int LINE_LENGTH = 3;
    private static final String COMMA_DELIMITER = ",";

    public static List<FruitsTransaction> parseTransactions(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new CsvParseException("Empty or null input line");
        }
        List<FruitsTransaction> transactions = new ArrayList<>();
        List<FruitsTransaction> tr = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] tokens = line.split(COMMA_DELIMITER);
            if (tokens.length >= LINE_LENGTH) {
                Operation operation = parseOperation(tokens[OPERATION_INDEX]);
                String fruit = tokens[FRUIT_INDEX].trim();
                int quantity = parseQuantity(tokens[QUANTITY_INDEX]);
                transactions.add(new FruitsTransaction(operation, fruit, quantity));
            } else {
                throw new CsvParseException("Invalid line format, expected: "
                        + LINE_LENGTH + " elements, but found: " + tokens.length);
            }
        }
        return transactions;
    }

    private static Operation parseOperation(String token) {
        try {
            return Operation.fromCode(token.trim());
        } catch (IllegalArgumentException e) {
            throw new CsvParseException("Invalid operation code: " + token, e);
        }
    }

    private static int parseQuantity(String token) {
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
            throw new CsvParseException("Invalid quantity format: " + token, e);
        }
    }
}
