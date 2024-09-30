package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exceptions.InvalidOperationException;

/**
 * This class provides balance from transaction input file
 * @author Bartek
 */
public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getOperation() != FruitTransaction.Operation.BALANCE) {
            throw new InvalidOperationException("Invalid operation. "
                    + "Should be: 'BALANCE', but was: "
                    + transaction.getOperation() + ".");
        }
        if (FruitTransaction.Operation.BALANCE.equals(transaction.getOperation())) {
            Storage.put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
