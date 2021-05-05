package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.exception.IncorrectFruitTypeException;
import core.basesyntax.exception.IncorrectQuantityValueException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionDtoParserTest {
    private static final TransactionDtoParser parser = new TransactionDtoParser();

    @Before
    public void setUp() {
        Storage.getFruitDataBase().entrySet().clear();
    }

    @Test
    public void parse_withCorrectData_Ok() {
        List<String> list = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10");
        List<TransactionDto> expected = List.of(new TransactionDto(
                OperationType.BALANCE, new Fruit("banana"), 20),
                new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100),
                new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100),
                new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 13),
                new TransactionDto(OperationType.RETURN, new Fruit("apple"), 10));
        List<TransactionDto> actual = parser.parse(list);
        assertEquals(expected, actual);
    }

    @Test (expected = IncorrectFruitTypeException.class)
    public void parse_withIncorrectFruit_NotOk() {
        List<String> list = List.of("b,banana,20", "s,3423,20");
        List<TransactionDto> actual = parser.parse(list);
    }

    @Test (expected = IncorrectQuantityValueException.class)
    public void parse_withIncorrectQuantity_NotOk() {
        List<String> list = List.of("b,banana,20", "r,apple,2s");
        List<TransactionDto> actual = parser.parse(list);
    }

    @Test (expected = IncorrectQuantityValueException.class)
    public void parse_withNegativeNumber_NotOk() {
        List<String> list = List.of("b,banana,20", "r,apple,-20");
        List<TransactionDto> actual = parser.parse(list);
    }
}
