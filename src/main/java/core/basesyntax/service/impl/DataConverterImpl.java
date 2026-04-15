package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        if (data == null) {
            throw new RuntimeException("Input data cannot be null");
        }

        List<FruitTransaction> result = new ArrayList<>();

        for (String line : data) {
            if (line == null || line.isBlank()) {
                continue;
            }

            String[] parts = line.split(",");

            if (parts.length < 3) {
                throw new RuntimeException("Invalid CSV line: " + line);
            }

            FruitTransaction tx = new FruitTransaction();
            tx.setOperation(FruitTransaction.Operation.fromCode(parts[0].trim()));
            tx.setFruit(parts[1].trim());
            tx.setQuantity(Integer.parseInt(parts[2].trim()));

            result.add(tx);
        }

        return result;
    }
}
