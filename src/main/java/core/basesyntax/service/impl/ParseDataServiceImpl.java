package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseDataService;
import java.util.ArrayList;
import java.util.List;

public class ParseDataServiceImpl implements ParseDataService {
    public static final int TYPE_OF_OPERATION = 0;
    public static final int FRUIT_TYPE = 1;
    public static final int FRUIT_QUANTITY = 2;
    public static final String DELIMITER_SYMBOL = ",";
    public static final int FILE_HEADER = 0;

    @Override
    public List<FruitTransaction> parseData(List<String> list) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        if (list.isEmpty()) {
            throw new RuntimeException("Input list is empty");
        }
        list.remove(FILE_HEADER);
        for (String fruit : list) {
            String[] splitString = fruit.split(DELIMITER_SYMBOL);
            fruitTransactions.add(new FruitTransaction(splitString[TYPE_OF_OPERATION],
                    new Fruit(splitString[FRUIT_TYPE]),
                    Integer.parseInt(splitString[FRUIT_QUANTITY])));
        }
        return fruitTransactions;
    }
}
