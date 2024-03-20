package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionMapper;
import java.util.ArrayList;
import java.util.List;

public class CsvDataParser implements FruitTransactionMapper {
    private static final String LINE_SPLIT_REGEX = ",";
    private static final int OPERATION_TYPE_CODE_INDEX = 0;
    private static final int PRODUCT_TYPE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int FIRS_DATA_LINE_INDEX = 1;
    private static final String HEADER_PATTERN = "[a-z]+,[a-z]+,[a-z]+";
    private static final String DATA_LINE_PATTERN = "[a-z],[a-z]+,[0-9]+";

    @Override
    public List<FruitTransaction> map(List<String> data) {
        validateInputData(data);
        List<FruitTransaction> resultList = new ArrayList<>();

        for (int i = FIRS_DATA_LINE_INDEX; i < data.size(); i++) {
            String[] splitLine = data.get(i).split(LINE_SPLIT_REGEX);
            Operation operationType = parseOperationType(splitLine);
            String productType = parseProductType(splitLine);
            int amount = parseAmount(splitLine);
            FruitTransaction newTransaction = new FruitTransaction(operationType, productType,
                    amount);
            resultList.add(newTransaction);
        }
        return resultList;
    }

    private int parseAmount(String[] splitLine) {
        return Integer.parseInt(splitLine[AMOUNT_INDEX]);
    }

    private String parseProductType(String[] splitLine) {
        return splitLine[PRODUCT_TYPE_INDEX];
    }

    private Operation parseOperationType(String[] splitLine) {
        return Operation.fromCode(splitLine[OPERATION_TYPE_CODE_INDEX].trim());
    }

    private boolean isDataLinesPattern(List<String> data) {
        for (int i = FIRS_DATA_LINE_INDEX; i < data.size(); i++) {
            if (!data.get(i).matches(DATA_LINE_PATTERN)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInvalidHeaderPattern(List<String> data) throws InvalidInputDataException {
        return !data.get(0).matches(HEADER_PATTERN);
    }

    private void validateInputData(List<String> data) {
        if (data == null || data.size() < 2 || isInvalidHeaderPattern(data)
                || isDataLinesPattern(data)) {
            throw new InvalidInputDataException("Input data is invalid or has a wrong pattern");
        }
    }
}
