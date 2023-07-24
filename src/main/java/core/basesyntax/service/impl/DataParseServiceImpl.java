package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NegativeTransactionException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.exceptions.WrongOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParseService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParseServiceImpl implements DataParseService {
    private static final String COMMA_SEPARATOR = ",";
    private static final int HEADER_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parseData(List<String> data) {
        if (data == null) {
            throw new NullDataException("Incoming data is null!");
        }
        data.remove(HEADER_INDEX);
        try {
            return data.stream()
                    .map(s -> {
                        String[] values = s.split(COMMA_SEPARATOR);
                        if (Integer.parseInt(values[QUANTITY_INDEX]) < 0) {
                            throw new NegativeTransactionException("Quantity is a negative number");
                        }
                        return new FruitTransaction(getOperation(values[OPERATION_INDEX]),
                                values[FRUIT_INDEX], Integer.parseInt(values[QUANTITY_INDEX]));
                    })
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new TransactionQuantityException("Quantity should be a number, not a string!");
        }

    }

    private FruitTransaction.Operation getOperation(String operation) {
        return Arrays.stream(FruitTransaction.Operation.values())
                .filter(o -> o.getCode().equals(operation))
                .findFirst()
                .orElseThrow(() -> new WrongOperationException("No such operation exists!"));
    }
}
