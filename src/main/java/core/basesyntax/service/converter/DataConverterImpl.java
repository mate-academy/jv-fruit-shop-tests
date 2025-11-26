package core.basesyntax.service.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertFruitTransactions(List<String> listTransactionFromFile) {
        if (listTransactionFromFile == null) {
            throw new RuntimeException("List transaction empty");
        }

        List<FruitTransaction> fruitTransactions = new ArrayList<>();

        for (int i = 1; i < listTransactionFromFile.size(); i++) {
            String[] split = listTransactionFromFile.get(i).split(",");

            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.SUPPLY.getCode(split[0]);

            checkValidationSlitString(split);

            FruitTransaction fruitTransaction = new FruitTransaction(
                    operation, split[1], Integer.parseInt(split[2]));
            fruitTransactions.add(fruitTransaction);

        }

        return fruitTransactions;
    }

    private void checkValidationSlitString(String[] splitString) {
        if (splitString.length != 3) {
            throw new RuntimeException("Split string != 3");
        }
        if (Integer.parseInt(splitString[2]) < 0) {
            throw new RuntimeException("Quantity negative value");
        }
    }
}

