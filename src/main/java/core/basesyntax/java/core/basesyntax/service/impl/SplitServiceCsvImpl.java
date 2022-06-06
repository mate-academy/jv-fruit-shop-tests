package core.basesyntax.java.core.basesyntax.service.impl;

import core.basesyntax.java.core.basesyntax.model.FruitTransaction;
import core.basesyntax.java.core.basesyntax.service.SplitDataValidator;
import core.basesyntax.java.core.basesyntax.service.SplitService;
import java.util.Arrays;

public class SplitServiceCsvImpl implements SplitService {
    private SplitDataValidator checkSplitData;

    public SplitServiceCsvImpl() {
        this.checkSplitData = new SplitDataValidatorCsvImpl();
    }

    @Override
    public FruitTransaction getTransactionFromRow(String line) {
        String[] splitData = line.split(",");
        if (checkSplitData.isNotValid(splitData)) {
            throw new RuntimeException("Bad data in *.csv file");
        }
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(Arrays.stream(FruitTransaction.Operation.values())
                .filter(o -> o.getOperation().equals(splitData[Index.TYPE.ordinal()]))
                .findFirst()
                .get());
        fruitTransaction.setFruit(splitData[Index.FRUIT.ordinal()]);
        fruitTransaction.setQuantity(Integer.parseInt(splitData[Index.QUANTITY.ordinal()]));
        return fruitTransaction;
    }

    public enum Index {
        TYPE,
        FRUIT,
        QUANTITY
    }
}
