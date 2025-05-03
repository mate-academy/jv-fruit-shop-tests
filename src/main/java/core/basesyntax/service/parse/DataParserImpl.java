package core.basesyntax.service.parse;

import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;

public class DataParserImpl implements DataParser {
    private static final int OPERATION_TYPE_POSITION = 0;
    private static final int FRUIT_NAME_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;

    @Override
    public List<FruitRecord> parseFruitRecords(List<String> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }
        List<FruitRecord> transactions = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");
            try {
                int quantity = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid quantity format in line.");
            }

            String operationCode = parts[OPERATION_TYPE_POSITION];
            String fruit = parts[FRUIT_NAME_POSITION];
            int quantity = Integer.parseInt(parts[QUANTITY_POSITION]);

            FruitRecord.Operation operation = FruitRecord.Operation.fromCode(operationCode);
            transactions.add(new FruitRecord(operation, fruit, quantity));
        }
        return transactions;
    }
}
