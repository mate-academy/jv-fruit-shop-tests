package core.basesyntax.serviceimpl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;

public class DataProcessorServiceImpl implements DataProcessorService {
    private final HashMap<Operation, OperationStrategy> handlerMap;

    public DataProcessorServiceImpl(HashMap<Operation, OperationStrategy> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void processData(List<FruitTransaction> fruitTransactions) {
        fruitTransactionsValidation(fruitTransactions);
        fruitTransactions.forEach(t -> handlerMap.get(t.getOperation())
                .processTransaction(t));
    }

    private void fruitTransactionsValidation(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new InvalidDataException("TransactionsList can't be null");
        }
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            if ((fruitTransaction == null)
                    || (fruitTransaction.getFruit() == null)
                    || (fruitTransaction.getOperation() == null)
                    || (fruitTransaction.getQuantity() == null)) {
                throw new InvalidDataException("fruitTransaction fields can't be null");
            }
            if (fruitTransaction.getQuantity() < 0) {
                throw new InvalidDataException("fruitTransaction quantity can't be less then 0,"
                        + " but was: " + fruitTransaction.getQuantity());
            }
        }
    }
}
