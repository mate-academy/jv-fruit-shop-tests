package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static Parser parser;

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parse_validDB_ok() {
        final List<String> data = List.of("type,fruit,quantity",
                "b,banana,20",
                "r,banana,10",
                "s,banana,5",
                "p,banana,13");
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.RETURN);
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(10);
        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(5);
        FruitTransaction fourthTransaction = new FruitTransaction();
        fourthTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fourthTransaction.setFruit("banana");
        fourthTransaction.setQuantity(13);
        List<FruitTransaction> expected = List.of(firstTransaction,
                secondTransaction,
                thirdTransaction,
                fourthTransaction);
        List<FruitTransaction> actual = parser.parse(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parse_nullOperation_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                ",banana,20");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }

    @Test
    void parse_nullFruit_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                "b,,20");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }

    @Test
    void parse_nullQuantity_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                "b,banana,");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }

    @Test
    void parse_inValidParametersAmount_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                "1,b,banana,20");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }

    @Test
    void parse_inValidOperationParameter_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                "inValidOperation,banana,20");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }

    @Test
    void parse_inValidFruitNameParameter_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                "b,1231,20");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }

    @Test
    void parse_inValidQuantityParameter_notOk() {
        List<String> data = List.of("type,fruit,quantity",
                "b,banana,word");
        assertThrows(RuntimeException.class, () -> parser.parse(data));
    }
}
