package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        final List<FruitTransaction> fruitTransactionList = new ArrayList<>();

        if (inputReport == null) {
            throw new RuntimeException("input data can not be null");
        }

        if (inputReport.isEmpty()) {
            throw new RuntimeException("input list is empty");
        }

        if (inputReport.size() == 1) {
            throw new RuntimeException("list has only header");
        }

        for (int i = 1; i < inputReport.size(); i++) {
            String[] splitLine = inputReport.get(i).split(",");

            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransaction.setOperation(splitLine[0]);
            fruitTransaction.setFruit(splitLine[1]);

            try {
                fruitTransaction.setQuantity(Integer.parseInt(splitLine[2]));
            } catch (NumberFormatException e) {
                throw new RuntimeException("invalid quantity value");
            }

            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }
}
