package core.basesyntax.services.impl;

import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.CreateTaskService;
import core.basesyntax.util.ConstantsForCsvParse;
import java.util.ArrayList;
import java.util.List;

public class CreateTaskServiceImpl implements CreateTaskService {
    private static final int NUM_ZERO = 0;

    @Override
    public List<FruitTransaction> createTasks(List<String[]> parseData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        validateParseData(parseData);
        for (String[] readLine : parseData) {
            if (readLine.length != ConstantsForCsvParse.MAX_SIZE_LINE) {
                throw new ValidationDataException("Create task is faild! "
                        + "ReadLine from parse data less than "
                        + ConstantsForCsvParse.MAX_SIZE_LINE + " elements");
            }
            FruitTransaction.ActionType typeOfTask = null;
            for (FruitTransaction.ActionType type : FruitTransaction.ActionType.values()) {
                if (type.getType().equals(readLine[ConstantsForCsvParse.INDEX_OF_ACTION])) {
                    typeOfTask = type;
                    break;
                }
            }
            String nameOfProduct = readLine[ConstantsForCsvParse.INDEX_OF_NAME];
            Integer valueOfLine = Integer.parseInt(readLine[ConstantsForCsvParse.INDEX_OF_VALUE]);
            validateData(typeOfTask, nameOfProduct, valueOfLine);
            fruitTransactions.add(new FruitTransaction(typeOfTask, nameOfProduct, valueOfLine));
        }
        return fruitTransactions;
    }

    private void validateData(FruitTransaction.ActionType typeOfTask,
                              String nameOfProduct, Integer valueOfLine) {
        if (typeOfTask == null) {
            throw new ValidationDataException("Type is not exist");
        }
        if (nameOfProduct == null) {
            throw new ValidationDataException("Name of Product can't be null");
        }
        if (nameOfProduct.isEmpty()) {
            throw new ValidationDataException("Name of Product can't be empty");
        }
        if (valueOfLine == null) {
            throw new ValidationDataException("Value can't be null");
        }
        if (valueOfLine < NUM_ZERO) {
            throw new ValidationDataException("Value can't be less than " + NUM_ZERO);
        }
    }

    private void validateParseData(List<String[]> parseData) {
        if (parseData == null) {
            throw new ValidationDataException("Create task is faild. Parse data can't be null!");
        }
        if (parseData.isEmpty()) {
            throw new ValidationDataException("Create task is faild. Parse data can't be empty!");
        }
    }
}
