package core.basesyntax.serviceimpl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionsServiceImpl implements TransactionService {
    private static final int OPERATION_INDEX = 0;
    private static final int NAME_OF_FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> getlistFruitTransaction(List<String> dataFromFile) {
        return dataFromFile.stream()
                .skip(1)
                .map(s -> getFruitTransaction(s))
                .collect(Collectors.toList());
    }

    private FruitTransaction getFruitTransaction(String data) {
        String[] strings = data.split(",");
        return new FruitTransaction(
                FruitTransaction.Operation.getOperationByActivity(strings[OPERATION_INDEX]),
                strings[NAME_OF_FRUIT_INDEX], Integer.parseInt(strings[2]));
    }
}

