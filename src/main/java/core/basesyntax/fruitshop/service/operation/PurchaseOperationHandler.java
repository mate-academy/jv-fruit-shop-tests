package core.basesyntax.fruitshop.service.operation;

import core.basesyntax.fruitshop.model.TransactionDto;
import core.basesyntax.fruitshop.storage.Storage;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public void applyOperation(TransactionDto transactionDto) {
        if (Storage.fruitBalance.get(transactionDto.getFruit().getName())
                > transactionDto.getAmount()) {
            Storage.fruitBalance.put(transactionDto.getFruit().getName(),
                    (Storage.fruitBalance.get(transactionDto.getFruit().getName())
                            - transactionDto.getAmount()));
        } else {
            throw new RuntimeException("There are not so many of these fruits in storage!");
        }
    }
}
