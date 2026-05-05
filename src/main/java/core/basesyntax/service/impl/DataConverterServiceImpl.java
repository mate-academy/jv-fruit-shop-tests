package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.util.ArrayList;
import java.util.List;

public class DataConverterServiceImpl implements DataConverterService {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        List<FruitTransaction> result = new ArrayList<>();

        if (lines == null) {
            return result;
        }

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            String[] parts = line.split(",");
            if (parts.length != 3) {
                continue;
            }

            FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode(parts[0]);
            int quantity = Integer.parseInt(parts[2]);
            result.add(new FruitTransaction(
                    operation,
                    parts[1],
                    quantity));
        }
        return result;
    }
}
