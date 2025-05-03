package core.basesyntax.service.impl;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import java.util.ArrayList;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private static final String REGEX = ",";
    private static final int INDEX_BY_LETTER = 0;
    private static final int INDEX_BY_FRUIT = 1;
    private static final int INDEX_BY_NUMBER = 2;

    @Override
    public List<FruitTransaction> processFruitLines(List<String> linesFromFile) {
        List<FruitTransaction> fruitTransactionArrayList = new ArrayList<>();
        for (String string : linesFromFile) {
            String[] resultData = string.split(REGEX);
            ActivityType shortOperation = ActivityType
                    .getOperationTypeByName(resultData[INDEX_BY_LETTER]);
            String fruitName = resultData[INDEX_BY_FRUIT];
            int number = checkNumber(resultData[INDEX_BY_NUMBER]);
            fruitTransactionArrayList.add(
                    new FruitTransaction(shortOperation, fruitName, number));
        }
        return fruitTransactionArrayList;
    }

    private static int checkNumber(String index) {
        int number = Integer.parseInt(index);
        if (number < 0) {
            throw new RuntimeException("Quantity " + number + " can't be negative");
        }
        return number;
    }
}
