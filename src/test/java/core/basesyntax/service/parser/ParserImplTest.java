package core.basesyntax.service.parser;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static Parser parser;
    private static String HEADER = "type,fruit,quantity";
    private static String VALID_TRANSACTION = "b,banana,20";

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
        int expectedQuantity = 20;
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, actual.getOperation());
        Assertions.assertEquals(expectedFruit, actual.getFruit());
        Assertions.assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void parseHeader_NotOk() {
        List<FruitTransaction> fruitTransactionList =
                parser.parseStringToFruitTransaction(
                        List.of(HEADER, VALID_TRANSACTION, VALID_TRANSACTION));
        String expectedFruit = "banana";
        int expectedQuantity = 20;
        FruitTransaction firstFruit = fruitTransactionList.get(0);
        FruitTransaction secondFruit = fruitTransactionList.get(1);
        Assertions.assertEquals(2, fruitTransactionList.size());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, firstFruit.getOperation());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, secondFruit.getOperation());
        Assertions.assertEquals(expectedFruit, firstFruit.getFruit());
        Assertions.assertEquals(expectedFruit, secondFruit.getFruit());
        Assertions.assertEquals(expectedQuantity, firstFruit.getQuantity());
        Assertions.assertEquals(expectedQuantity, secondFruit.getQuantity());
    }

    @Test
    void parseNull_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parseStringToFruitTransaction(null));
    }

    @Test
    void parseEmptyList_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parseStringToFruitTransaction(new ArrayList<>()));
    }
}
