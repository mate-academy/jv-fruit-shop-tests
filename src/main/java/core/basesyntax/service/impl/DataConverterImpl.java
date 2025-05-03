package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String COMMA_SEPARATOR = ",";
    private static final int PARTS_LENGTH = 3;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> list) {
        List<FruitTransaction> result = new ArrayList<>(list.size() - 1);
        for (int i = 1; i < list.size(); i++) {
            String[] parts = list.get(i).split(COMMA_SEPARATOR);
            if (parts.length != PARTS_LENGTH) {
                throw new RuntimeException("Wrong data format");
            }
            FruitTransaction.Operation mode;
            switch (parts[OPERATION_INDEX]) {
                case "b" -> mode = FruitTransaction.Operation.BALANCE;
                case "s" -> mode = FruitTransaction.Operation.SUPPLY;
                case "p" -> mode = FruitTransaction.Operation.PURCHASE;
                case "r" -> mode = FruitTransaction.Operation.RETURN;
                default -> throw new RuntimeException("Wrong data format");
            }
            try {
                Integer.parseInt(parts[QUANTITY_INDEX]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Wrong data format");
            }
            FruitTransaction fruitTransaction = new FruitTransaction(mode,
                    new Fruit(parts[FRUIT_INDEX]),Integer.parseInt(parts[QUANTITY_INDEX]));
            result.add(fruitTransaction);
        }
        return result;
    }
}
