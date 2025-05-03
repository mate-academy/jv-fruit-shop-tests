package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static TransactionParser parser;
    private List<String> lines;
    private List<FruitTransaction> transactions;

    @BeforeAll
    public static void setUpAll() {
        parser = new FruitTransactionParser();
    }

    @BeforeEach
    public void setUp() {
        lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,apple,10");
        lines.add("p,apple,20");
        lines.add("p,banana,5");
        lines.add("s,banana,50");

        transactions = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(13);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(20);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(5);
        transactions.add(fruitTransaction);

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);
        transactions.add(fruitTransaction);
    }

    @Test
    public void parseAll_emptyList_notOk() {
        lines = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
    }

    @Test
    public void parseAll_emptyLines_ok() {
        lines.add(1, " ");
        lines.add(5, "   ");
        lines.add("");
        List<FruitTransaction> expected = transactions;
        List<FruitTransaction> actual = parser.parseAll(lines);
        assertEquals(expected, actual);
    }

    @Test
    public void parseAll_moreOrLessThan3ValuesInLine_notOk() {
        lines.add(3, "b,banana,apple,100");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
        lines.remove(3);
        lines.add(6, "apple,80");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
    }

    @Test
    public void parseAll_invalidOperationValue_notOk() {
        lines.add(7, "c,apple,50");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
        lines.remove(7);
        lines.add(7, ",apple,50");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
    }

    @Test
    public void parseAll_invalidFruitNameValue_notOk() {
        lines.add(2, "s,,70");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
        lines.remove(2);
        lines.add(8, "p,   ,40");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
    }

    @Test
    public void parseAll_invalidQuantityValue_notOk() {
        lines.add("r,banana,5OO");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
        lines.remove("r,banana,5OO");
        lines.add("r,banana,-5");
        assertThrows(RuntimeException.class, () -> parser.parseAll(lines));
    }

    @Test
    public void parseAll_whitespacesAndUppercaseValues_ok() {
        lines.set(1, "  B  , BaNaNa  ,20 ");
        lines.set(2, "    b,APPLE , 100");
        List<FruitTransaction> expected = transactions;
        List<FruitTransaction> actual = parser.parseAll(lines);
        assertEquals(expected, actual);
    }
}
