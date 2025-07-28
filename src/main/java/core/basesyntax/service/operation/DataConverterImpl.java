package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    public static final String COMMA = ",";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] split = line.split(COMMA);

            String operationValue = split[0];
            String fruit = split[1];
            int amount = Integer.parseInt(split[2]);

            FruitTransaction.Operation operation = FruitTransaction.Operation
                    .mapToOperation(operationValue);
            FruitTransaction transaction = new FruitTransaction(operation, fruit, amount);
            transactions.add(transaction);
            if (fruit.isEmpty()) {
                throw new RuntimeException("Fruit paramateres are empty!");
            }
            if (amount <= 0) {
                throw new RuntimeException("Amount can't be less than 1!");
            }
            if (!Character.isLetter(fruit.charAt(0))) {
                throw new RuntimeException("Fruit name should start from letters!");
            }
        }
        return transactions;
    }
}
