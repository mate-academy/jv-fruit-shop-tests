package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import org.junit.jupiter.api.Test;

class TransactionStrategyTest {
    private final TransactionStrategy transactionStrategy = new TransactionStrategy();

    @Test
    void correctClassReturns_isOk() {
        OperationHandler operationHandlerReturn = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN);

        assertSame(ReturnService.class, operationHandlerReturn.getClass());

        OperationHandler operationHandlerBalance = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE);

        assertSame(operationHandlerBalance.getClass(), BalanceService.class);

        OperationHandler operationHandlerPurchase = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE);

        assertSame(operationHandlerPurchase.getClass(), PurchaseService.class);

        OperationHandler operationHandlerSupply = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);

        assertSame(operationHandlerSupply.getClass(), SupplyService.class);

    }

    @Test
    void incorrectOperation_notOk() {
        assertThrows(RuntimeException.class, () -> transactionStrategy.getOperationHandler(null));
    }
}
