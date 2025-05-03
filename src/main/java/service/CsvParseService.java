package service;

import java.util.List;
import model.FruitTransaction;

public class CsvParseService implements Parser {
    
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final int EXPECTED_FIELDS = 3;
    private static final String SEPARATOR = ",";
    private static final int SKIP_LINES_COUNT = 1;
    
    @Override
    public List<FruitTransaction> parseTransactions(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty.");
        }
        
        return lines.stream()
                .skip(SKIP_LINES_COUNT)
                .map(this::parseTransaction)
                .toList();
    }
    
    public FruitTransaction parseTransaction(String line) {
        String[] parts = line.split(SEPARATOR);
        if (parts.length != EXPECTED_FIELDS) {
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
        return new FruitTransaction(parts[FRUIT_NAME_INDEX],
                Integer.parseInt(parts[QUANTITY_INDEX]),
                FruitTransaction.Operation.fromCode(parts[OPERATION_TYPE_INDEX]));
    }
}
