package core.basesyntax.service.impl;

import core.basesyntax.dao.ActionDao;
import core.basesyntax.dao.ActionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final String SEPARATOR = ",";
    private static final int START_DATA_LINE = 1;
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY = 2;
    private final ActionDao actionDao = new ActionDaoImpl();

    @Override
    public List<FruitTransaction> parseTransactions(List<String> stringList) {
        if (stringList == null) {
            throw new RuntimeException("There isn't any information for parsing");
        }
        for (int i = START_DATA_LINE; i < stringList.size(); i++) {
            actionDao.addFruitTransaction(createAction(stringList.get(i)));
        }
        return actionDao.getListTransactions();
    }

    private FruitTransaction createAction(String line) {
        String[] dataMassive = line.split(SEPARATOR);
        if (dataMassive.length != 3) {
            throw new RuntimeException("There're invalid data for parsing");
        }
        if (Operation.getByType(dataMassive[INDEX_TYPE]) == null
                || (dataMassive[INDEX_FRUIT].isEmpty() || dataMassive[INDEX_FRUIT].equals(" "))
                || (dataMassive[INDEX_QUANTITY].isEmpty()
                || dataMassive[INDEX_QUANTITY].equals(" "))) {
            throw new RuntimeException("All fields should be filled('type','fruit','quantity')");
        }
        for (char letter : dataMassive[INDEX_FRUIT].toCharArray()) {
            if (!Character.isLetter(letter)) {
                throw new RuntimeException("The name of the fruit must consist only of letters");
            }
        }
        for (char item : dataMassive[INDEX_QUANTITY].toCharArray()) {
            if (!Character.isDigit(item)) {
                throw new NumberFormatException("Number must contain digits");
            }
        }
        return new FruitTransaction(Operation.getByType(dataMassive[INDEX_TYPE]),
                dataMassive[INDEX_FRUIT], Integer.parseInt(dataMassive[INDEX_QUANTITY]));
    }
}
