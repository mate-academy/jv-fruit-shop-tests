package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parse_Ok() {
        List<FruitTransaction> actual = transactionParser.parseFile(List.of("skip Line",
                                                                           "b,banana,20",
                                                                           "b,apple,100",
                                                                           "s,banana,100"));
        List<FruitTransaction> expected = List.of(new FruitTransaction("b", "banana", 20),
                                                  new FruitTransaction("b", "apple", 100),
                                                  new FruitTransaction("s", "banana", 100));
        Assert.assertEquals(actual, expected);
    }

    @Test
    void anotherTypeOperation_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.parseFile(List.of("skip Line","f,banana,20"));
        });
    }

    @Test
    void negativeQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.parseFile(List.of("skip Line","p,banana,-20"));
        });
    }

    @Test
    public void nullLine_notOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.parseFile(null);
        });
    }

    @Test
    void fruitIsBlank_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.parseFile(List.of("skip Line", "p, ,20"));
        });
    }
}
