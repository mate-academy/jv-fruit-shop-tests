package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static final String CORRECT_DATA_FROM_FILE = "b,banana,20 b,apple,100 "
            + "s,banana,100 p,banana,13 r,apple,10";
    private static Parser parser;

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parseFruitTransactions_nullDataFromFile_notOk() {
        Assert.assertThrows(NullPointerException.class, () ->
                parser.parseFruitTransactions(null));
    }

    @Test
    void parseFruitTransactions_correctParsingFromFile_ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(20);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(100);

        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setQuantity(100);

        FruitTransaction fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction4.setFruit("banana");
        fruitTransaction4.setQuantity(13);

        FruitTransaction fruitTransaction5 = new FruitTransaction();
        fruitTransaction5.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction5.setFruit("apple");
        fruitTransaction5.setQuantity(10);

        List<FruitTransaction> expected = List.of(fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4, fruitTransaction5);
        List<FruitTransaction> actual = parser.parseFruitTransactions(CORRECT_DATA_FROM_FILE);
        Assertions.assertEquals(expected.toString(), actual.toString());
    }
}
