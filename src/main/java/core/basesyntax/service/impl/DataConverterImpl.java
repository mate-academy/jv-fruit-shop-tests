package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> list) {
        List<FruitTransaction> result = new ArrayList<>(list.size() - 1);
        list.remove(0);
        for (String s : list) {
            String[] parts = s.split(",");
            if (parts.length != 3 || parts[0].length() != 1) {
                throw new RuntimeException("Wrong data format");
            }
            FruitTransaction.Operation mode;
            switch (parts[0]) {
                case "b" -> mode = FruitTransaction.Operation.BALANCE;
                case "s" -> mode = FruitTransaction.Operation.SUPPLY;
                case "p" -> mode = FruitTransaction.Operation.PURCHASE;
                case "r" -> mode = FruitTransaction.Operation.RETURN;
                default -> mode = null;
            }
            if (mode == null) {
                throw new RuntimeException("Wrong data format");
            }
            try {
                Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Wrong data format");
            }
            FruitTransaction fruitTransaction = new FruitTransaction(mode, new Fruit(parts[1]),
                    Integer.parseInt(parts[2]));
            result.add(fruitTransaction);
        }
        return result;
    }
}
