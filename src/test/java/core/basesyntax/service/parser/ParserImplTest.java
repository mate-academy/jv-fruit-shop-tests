package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static Parser parser;
    private static final String HEADER = "type,fruit,quantity";
    private static final String VALID_TRANSACTION = "b,banana,20";

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parseValidList_Ok() {
        List<FruitTransaction> fruitTransactionList =
                parser.parseStringToFruitTransaction(List.of(HEADER, VALID_TRANSACTION));
        FruitTransaction actual = fruitTransactionList.get(0);
        String expectedFruit = "banana";
        final int expectedQuantity = 20;
        assertEquals(FruitTransaction.Operation.BALANCE, actual.getOperation());
        assertEquals(expectedFruit, actual.getFruit());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void parseHeader_NotOk() {
        List<FruitTransaction> fruitTransactionList =
                parser.parseStringToFruitTransaction(
                        List.of(HEADER, VALID_TRANSACTION, VALID_TRANSACTION));
        FruitTransaction firstFruit = fruitTransactionList.get(0);
        FruitTransaction secondFruit = fruitTransactionList.get(1);
        final String expectedFruit = "banana";
        final int expectedQuantity = 20;
        assertEquals(2, fruitTransactionList.size());
        assertEquals(FruitTransaction.Operation.BALANCE, firstFruit.getOperation());
        assertEquals(FruitTransaction.Operation.BALANCE, secondFruit.getOperation());
        assertEquals(expectedFruit, firstFruit.getFruit());
        assertEquals(expectedFruit, secondFruit.getFruit());
        assertEquals(expectedQuantity, firstFruit.getQuantity());
        assertEquals(expectedQuantity, secondFruit.getQuantity());
    }

    @Test
    void parseNull_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseStringToFruitTransaction(null));
    }

    @Test
    void parseEmptyList_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseStringToFruitTransaction(new ArrayList<>()));
    }
}
