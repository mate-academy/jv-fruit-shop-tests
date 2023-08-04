package core.basesyntax.service.impl;

import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionsParserImplTest {
    private static final String COMMA_DIVIDER = ",";
    private static final String REPORT_HEADING = "type,fruit,quantity\r\n";
    private TransactionParser<List<FruitTransaction>, String> transactionParser;

    @BeforeEach
    void setUp() {
        transactionParser = new TransactionsParserImpl();
    }

    @Test
    void parseTransaction_OK() {
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,100" + System.lineSeparator()
                + "b,apple,50" + System.lineSeparator()
                + "s,banana,10";
        List<FruitTransaction> fruitTransactions = transactionParser.parse(input);
        FruitTransaction balanceBanana = new FruitTransaction(
                FruitShopOperation.BALANCE, "banana", 100);
        FruitTransaction balanceApple = new FruitTransaction(
                FruitShopOperation.BALANCE, "apple", 50);
        FruitTransaction supplyBanana = new FruitTransaction(
                FruitShopOperation.SUPPLY, "banana", 10);
        Assertions.assertEquals(balanceBanana, fruitTransactions.get(0));
        Assertions.assertEquals(balanceApple, fruitTransactions.get(1));
        Assertions.assertEquals(supplyBanana, fruitTransactions.get(2));
    }
}
