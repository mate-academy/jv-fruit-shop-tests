package core.basesyntax.service.transaction.impl;

import core.basesyntax.exception.ParsingException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.FruitTransactionParsingService;
import java.util.ArrayList;
import java.util.List;

public class CsvFruitTransactionParsingService implements FruitTransactionParsingService {
    private static final String DATA_LIST_FIRST_LINE = "type,fruit,quantity";
    private static final String CSV_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> data) {
        if (data.isEmpty() || !data.get(0).equals(DATA_LIST_FIRST_LINE)) {
            throw new ParsingException("Data list should start with " + DATA_LIST_FIRST_LINE);
        }

        try {
            List<FruitTransaction> fruitTransactions = new ArrayList<>();
            for (int i = 1; i < data.size(); i++) {
                String[] parts = data.get(i).split(CSV_SEPARATOR);
                fruitTransactions.add(new FruitTransaction(
                        FruitTransaction.Operation.ofCode(parts[OPERATION_INDEX]),
                        parts[FRUIT_INDEX],
                        Integer.parseInt(parts[QUANTITY_INDEX])
                ));
            }
            return fruitTransactions;
        } catch (Exception e) {
            throw new ParsingException("Cannot parse data: " + data, e);
        }
    }
}
