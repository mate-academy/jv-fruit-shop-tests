package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.impl.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.List;

public class DataParserImpl implements DataParser {
    private static final String COMMA = ",";
    private static final int OPERATION = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;

    @Override
    public List<FruitTransaction> getParsedData(String data) {
        if (data.length() == 0) {
            throw new InvalidDataException("Can't parsed empty data");
        }
        List<FruitTransaction> transactions = new ArrayList<>();
        String[] splittedData = data.split(System.lineSeparator());
        for (int i = 1; i < splittedData.length; i++) {
            String[] current = splittedData[i].split(COMMA);
            Operation operation = Operation.getByOperationCode(current[OPERATION]);
            String fruit = current[FRUIT];
            int quantity = Integer.parseInt(current[QUANTITY]);
            FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
            transactions.add(transaction);
        }
        return transactions;
    }
}
