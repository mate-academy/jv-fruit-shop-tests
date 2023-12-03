package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parseTransactions_validInputInfoFile_ok() {
        List<String> actualStringList = new ArrayList<>();
        actualStringList.add("type,fruit,quantity");
        actualStringList.add("   b,banana,20");
        actualStringList.add("   b,apple,100");
        actualStringList.add("   s,banana,100");
        actualStringList.add("   p,banana,13");
        actualStringList.add("   r,apple,10");
        actualStringList.add("   p,apple,20");
        actualStringList.add("   p,banana,5");
        actualStringList.add("   s,banana,50");
        List<FruitTransaction> actualFruitTransactions =
                transactionParser.parseTransactions(actualStringList);
        List<FruitTransaction> expectedFruitTransactions =
                List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                        new FruitTransaction(Operation.BALANCE, "apple", 100),
                        new FruitTransaction(Operation.SUPPLY, "banana", 100),
                        new FruitTransaction(Operation.PURCHASE, "banana", 13),
                        new FruitTransaction(Operation.RETURN, "apple", 10),
                        new FruitTransaction(Operation.PURCHASE, "apple", 20),
                        new FruitTransaction(Operation.PURCHASE, "banana", 5),
                        new FruitTransaction(Operation.SUPPLY, "banana", 50)
                );
        assertEquals(expectedFruitTransactions, actualFruitTransactions);
    }

    @Test
    void parseTransactions_notValidInfo_notOk() {
        List<String> notValidInfo1 = List.of("", "b,banana,20", "x,apple,100");
        assertThrows(RuntimeException.class,
                () -> transactionParser.parseTransactions(notValidInfo1));
        List<String> notValidInfo2 = List.of("abc", "banana,20");
        assertThrows(RuntimeException.class,
                () -> transactionParser.parseTransactions(notValidInfo2));
        List<String> notValidInfo3 = List.of("", "abc");
        assertThrows(RuntimeException.class,
                () -> transactionParser.parseTransactions(notValidInfo3));
        List<String> notValidInfo4 = List.of("");
        assertThrows(RuntimeException.class,
                () -> transactionParser.parseTransactions(notValidInfo4));
        List<String> notValidInfo5 = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> transactionParser.parseTransactions(notValidInfo5));
    }
}
