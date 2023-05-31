package core.basesyntax.service.impl;

import core.basesyntax.exception.WrongOperationLetterException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataParserServiceImpl implements DataParserService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int FRUIT_COUNT_INDEX = 2;
    private static final String DATA_SPLIT_SYMBOL = ",";

    @Override
    public List<FruitTransaction> parseData(List<String> data) {
        if (data.contains(null)) {
            return new ArrayList<>();
        }
        return data.stream()
                .map(transaction -> transaction.toLowerCase().split(DATA_SPLIT_SYMBOL))
                .map(transaction -> new FruitTransaction(
                        getOperationByLetter(transaction[OPERATION_INDEX]),
                        transaction[FRUIT_TYPE_INDEX],
                        Integer.parseInt(transaction[FRUIT_COUNT_INDEX]) < 0
                                ? Integer.parseInt(transaction[FRUIT_COUNT_INDEX]) * - 1
                                : Integer.parseInt(transaction[FRUIT_COUNT_INDEX])))
                .collect(Collectors.toList());
    }

    private FruitTransaction.Operation getOperationByLetter(String letter) {
        for (FruitTransaction.Operation value : FruitTransaction.Operation.values()) {
            if (value.getLetter().equals(letter)) {
                return value;
            }
        }
        throw new WrongOperationLetterException("Can`t add data with incorrect operation letter");
    }
}