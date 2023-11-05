package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private List<String> fileData;
    private List<FruitTransaction> expected;
    private Parser fruitTransaction;

    @BeforeEach
    public void beforeEach() {
        fruitTransaction = new FruitTransactionParser();
        fileData = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    public void fruitTransactionParser_OkCase() {
        expected = List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = fruitTransaction.parse(fileData);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitTransactionParser_EmptyInput_NotOk() {
        fileData = List.of();
        List<FruitTransaction> actual = fruitTransaction.parse(fileData);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void fruitTransactionParser_MalformedInput_NotOk() {
        fileData = List.of("b,banana,20", "invalid_line", "s,apple,30");
        assertThrows(IllegalArgumentException.class, () -> fruitTransaction.parse(fileData));
    }

    @Test
    public void fruitTransactionParser_CaseInsensitiveOperationCode_Ok() {
        fileData = List.of("B,banana,20", "S,apple,30");
        expected = List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "apple", 30));
        List<FruitTransaction> actual = fruitTransaction.parse(fileData);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitTransactionParser_InvalidQuantity_NotOk() {
        fileData = List.of("b,banana,-20", "s,apple,invalid");
        assertThrows(IllegalArgumentException.class, () -> fruitTransaction.parse(fileData));
    }
}
