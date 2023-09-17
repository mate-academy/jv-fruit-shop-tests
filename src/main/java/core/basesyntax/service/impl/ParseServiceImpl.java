package core.basesyntax.service.impl;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;

public class ParseServiceImpl implements ParseService {
    private static final String SEPARATOR = ",";
    private static final int START_POINT = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parseInputData(List<String> inputData) {
        if (inputData.size() == 0) {
            throw new FruitShopException("Input data list is empty");
        }
        List<FruitTransaction> parsedData = new ArrayList<>();
        for (int i = START_POINT; i < inputData.size(); i++) {
            parsedData.add(parseLine(inputData.get(i)));
        }
        return parsedData;
    }

    private FruitTransaction parseLine(String inputStr) {
        FruitTransaction transaction = new FruitTransaction();
        String[] lines = inputStr.split(SEPARATOR);
        transaction.setOperation(
                FruitTransaction.Operation.getOperationByCode(lines[OPERATION_INDEX]));
        transaction.setFruit(lines[FRUIT_INDEX]);
        transaction.setQuantity(Integer.parseInt(lines[QUANTITY_INDEX]));
        return transaction;
    }
}
