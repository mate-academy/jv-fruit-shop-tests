package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.FruitTransaction;

public class FruitTransactionConverterImpl implements FruitTransactionConverter {
    private static final String REGEX_TO_SPLIT_STRING = ",";
    private static final int OPERATION_SIGN_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int REQUIRED_ARRAY_SIZE = 3;

    @Override
    public List<FruitTransaction> convertToFruitTransaction(List<String> inputData) {
        if (inputData == null) {
            return new ArrayList<>();
        }
        return inputData.stream()
                .map(string -> string.split(REGEX_TO_SPLIT_STRING))
                .filter(array -> array.length == REQUIRED_ARRAY_SIZE)
                .map(array -> new FruitTransaction(array[OPERATION_SIGN_INDEX],
                        array[FRUIT_NAME_INDEX],
                        Integer.parseInt(array[AMOUNT_INDEX])))
                .collect(Collectors.toList());
    }
}
