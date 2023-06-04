package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
//
public class ParserServiceImpl implements ParserService {
    private static final String ONLY_LETTERS_SEPARATOR = "\\W+";
    private static final byte TYPE = 0;
    private static final byte FRUIT = 1;
    private static final byte QUANTITY = 2;
    private static final byte COLUMNS_NAMES = 0;

    @Override
    public List<FruitTransaction> getFruitTransaction(List<String> data) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        if (data == null) {
            throw new ValidationException("Can't make operations with null data");
        } else if (data.isEmpty()) {
            throw new ValidationException("Can't make operations with empty data");
        } else {
            data.remove(COLUMNS_NAMES);
            for (var line : data) {
                String[] splitString = line.split(ONLY_LETTERS_SEPARATOR);
                fruitTransactionList
                        .add(new FruitTransaction(OperationType.getByCode(splitString[TYPE]),
                                splitString[FRUIT], Integer.parseInt(splitString[QUANTITY])));

            }
            return fruitTransactionList;
        }
    }
}
