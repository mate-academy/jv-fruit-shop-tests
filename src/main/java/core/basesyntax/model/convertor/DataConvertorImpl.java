package core.basesyntax.model.convertor;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConvertorImpl implements DataConvertor {
    private static final String SEPARATOR = ",";
    private static final String PATTERN = "\\d+";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        List<FruitTransaction> transactions = new ArrayList<>();

        inputReport.stream()
                .skip(1)
                .filter(line -> line != null && !line.isBlank())
                .map(line -> line.split(SEPARATOR))
                .forEach(parts -> {
                    if (parts.length != 3 || !parts[2].matches(PATTERN)) {
                        throw new IllegalArgumentException(
                                "Invalid data format: " + String.join(SEPARATOR, parts)
                        );
                    }
                    transactions.add(new FruitTransaction(
                            FruitTransaction.Operation.fromCode(parts[0]),
                            parts[1],
                            Integer.parseInt(parts[2])
                    ));
                });

        return transactions;
    }
}
