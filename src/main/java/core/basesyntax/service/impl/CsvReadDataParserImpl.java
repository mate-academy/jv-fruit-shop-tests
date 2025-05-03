package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.OperationType;
import core.basesyntax.service.ReadDataParser;
import java.util.ArrayList;
import java.util.List;

public class CsvReadDataParserImpl implements ReadDataParser {
    private static final String SPLIT_REGEX = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String EXCEPTION_MESSAGE = "Can't parse data. Ensure your CSV is valid";

    @Override
    public ArrayList<FruitTransaction> convertToFruitTransactionList(List<String> fileLines) {
        ArrayList<FruitTransaction> operationsList = new ArrayList<>();
        for (int i = 1; i < fileLines.size(); i++) {
            String[] splitedLine = fileLines.get(i).split(SPLIT_REGEX);
            try {
                String operationTypeCode = splitedLine[OPERATION_TYPE_INDEX];
                String fruitType = splitedLine[FRUIT_NAME_INDEX];
                int amount = Integer.parseInt(splitedLine[AMOUNT_INDEX]);
                OperationType operationType =
                                OperationType.getOperationTypeByCode(operationTypeCode);
                operationsList.add(new FruitTransaction(operationType, fruitType, amount));
            } catch (RuntimeException e) {
                throw new RuntimeException(EXCEPTION_MESSAGE, e);
            }
        }
        return operationsList;
    }
}
