package core.basesyntax;

import core.basesyntax.FruitTransaction.Operation;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convert(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                throw new IllegalArgumentException("Line can not be blank");
            }
            String[] parts = line.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid input line" + line);
            }
            String code = parts[0].strip();
            String fruit = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());

            Operation operation = Operation.fromCode(code);

            FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
            transactions.add(transaction);

        }
        return transactions;
    }
}
