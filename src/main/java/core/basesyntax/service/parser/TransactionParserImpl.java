package core.basesyntax.service.parser;

import core.basesyntax.entity.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    public static final int ACTIVITY_POSITION = 0;
    public static final int FRUIT_POSITION = 1;
    public static final int QUANTITY_POSITION = 2;

    private static final String LINE_FORMAT_REGEX = "\\w{1},[a-z]+,\\d+";

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        List<FruitTransaction> result = new ArrayList<>();
        for (String line : lines) {
            String cleanedLine = line.trim();
            if (!cleanedLine.matches(LINE_FORMAT_REGEX)) {
                throw new IllegalArgumentException("Invalid line format: " + line);
            }

            String[] parts = cleanedLine.split(",");
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.fromCode(parts[ACTIVITY_POSITION]));
            transaction.setFruit(parts[FRUIT_POSITION]);
            transaction.setQuantity(Integer.parseInt(parts[QUANTITY_POSITION]));
            result.add(transaction);
        }
        return result;
    }
}
