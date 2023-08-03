package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.interfaces.TransactionParser;
import core.basesyntax.service.interfaces.TransactionValidator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionsParserImplTest {
    private static final String COMMA_DIVIDER = ",";
    private static final String REPORT_HEADING = "type,fruit,quantity\r\n";

    private TransactionValidator validator;
    private TransactionParser<List<FruitTransaction>, String> transactionParser;

    @BeforeEach
    void setUp() {
        validator = new TransactionValidatorImpl();
        transactionParser = new TransactionsParserImpl(validator);
    }

    @Test
    void parseTransaction_OK() {
        String input = "type,fruit,quantity\r\nb,banana,100\r\nb,apple,50\r\ns,banana,10";
        List<FruitTransaction> fruitTransactions = transactionParser.parse(input);
        FruitTransaction balanceBanana = new FruitTransaction(
                Operation.BALANCE, Fruit.BANANA, 100);
        FruitTransaction balanceApple = new FruitTransaction(
                Operation.BALANCE, Fruit.APPLE, 50);
        FruitTransaction supplyBanana = new FruitTransaction(
                Operation.SUPPLY, Fruit.BANANA, 10);
        Assertions.assertEquals(balanceBanana, fruitTransactions.get(0));
        Assertions.assertEquals(balanceApple, fruitTransactions.get(1));
        Assertions.assertEquals(supplyBanana, fruitTransactions.get(2));
    }
}
