package core.basesyntax.service.impl;

import core.basesyntax.model.FruitModel;
import core.basesyntax.model.OperationModel;
import core.basesyntax.service.FileParserService;
import java.util.ArrayList;
import java.util.List;

public class FileParserImpl implements FileParserService {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int OFFSET = 1;

    @Override
    public List<OperationModel> parse(List<String> listFromFile) {
        List<OperationModel> transactions = new ArrayList<>();
        for (int i = OFFSET; i < listFromFile.size(); i++) {
            String[] splitData = listFromFile.get(i).split(SEPARATOR);
            if (splitData.length < 3) {
                throw new IllegalArgumentException("Unexpected data type");
            }
            OperationModel.Operation operation
                    = OperationModel.Operation.getOperationFromCode(splitData[OPERATION_INDEX]);
            FruitModel fruitType = FruitModel.valueOf(splitData[FRUIT_TYPE_INDEX].toUpperCase());
            OperationModel transaction = getTransaction(splitData, operation, fruitType);
            transactions.add(transaction);
        }
        return transactions;
    }

    private static OperationModel getTransaction(String[] splitData,
                                                 OperationModel.Operation operation,
                                                 FruitModel fruitType) {
        int amount;
        try {
            amount = Integer.parseInt(splitData[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("For input string: \""
                    + splitData[QUANTITY_INDEX] + "\"");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can`t be less then 0. " + amount);
        }
        return new OperationModel(operation, fruitType, amount);
    }
}
