package service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import model.FruitOperationType;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import service.impl.FruitOperationTypeParserImpl;
import service.impl.FruitTransactionParserImpl;

class FruitTransactionParserImplTest {
    private static final FruitOperationTypeParser fruitOperationTypeParser
            = new FruitOperationTypeParserImpl();
    private static final FruitTransactionParser fruitTransactionParser
            = new FruitTransactionParserImpl(fruitOperationTypeParser);

    @Test
    void parseFruitTransaction_Ok() {
        List<String> data = List.of("b,banana,100", "s,banana,100", "p,banana,10", "r,banana,5");
        List<FruitTransaction> actual = fruitTransactionParser.parseTransaction(data);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitOperationType.BALANCE, "banana", 100),
                new FruitTransaction(FruitOperationType.SUPPLY, "banana", 100),
                new FruitTransaction(FruitOperationType.PURCHASE, "banana", 10),
                new FruitTransaction(FruitOperationType.RETURN, "banana", 5));

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void parseFruitTransaction_NotOk() {
        List<String> data = List.of("ABV,banana,100");
        assertThrows(NoSuchElementException.class,
                () -> fruitTransactionParser.parseTransaction(data));
    }

    @Test
    void parseNullValue_NotOk() {
        List<String> data = List.of("b,banana,0");
        assertThrows(IllegalArgumentException.class,
                () -> fruitTransactionParser.parseTransaction(data));
    }

    @Test
    void parseIncorrectLength_NotOk() {
        List<String> data = List.of("b,0");
        assertThrows(IllegalArgumentException.class,
                () -> fruitTransactionParser.parseTransaction(data));
    }

    @Test
    void parseIncorrectValue_NotOk() {
        List<String> data = List.of("b,b,0");
        assertThrows(IllegalArgumentException.class,
                () -> fruitTransactionParser.parseTransaction(data));
    }
}
