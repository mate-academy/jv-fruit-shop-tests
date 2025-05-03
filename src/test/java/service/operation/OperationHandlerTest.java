package service.operation;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;

class OperationHandlerTest {
    static final String DEFAULT_FRUIT = "banana";
    static final String NO_EXIST_FRUIT = "cherry";
    static final int ZERO_QUANTITY = 0;
    static final int NEGATIVE_QUANTITY = -50;
    static final int DEFAULT_QUANTITY = 50;
    static final FruitTransaction defaultTransaction = new FruitTransaction();
    static final FruitTransaction noExistFruitTransaction = new FruitTransaction();
    static final FruitTransaction negativeQuantityTransaction = new FruitTransaction();
    static final FruitTransaction nullFruitTransaction = new FruitTransaction();
    static final FruitTransaction zeroQuantityTransaction = new FruitTransaction();

    @BeforeAll
    static void createFruitTransactions() {
        defaultTransaction.setFruit(DEFAULT_FRUIT);
        defaultTransaction.setQuantity(DEFAULT_QUANTITY);

        negativeQuantityTransaction.setFruit(DEFAULT_FRUIT);
        negativeQuantityTransaction.setQuantity(NEGATIVE_QUANTITY);

        nullFruitTransaction.setQuantity(DEFAULT_QUANTITY);

        zeroQuantityTransaction.setFruit(DEFAULT_FRUIT);
        zeroQuantityTransaction.setQuantity(ZERO_QUANTITY);

        noExistFruitTransaction.setFruit(NO_EXIST_FRUIT);
        noExistFruitTransaction.setQuantity(DEFAULT_QUANTITY);

        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.handle(defaultTransaction);
    }
}
