package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDataParserImplTest {

    private static TransactionDto transactionDto;

    @BeforeClass
    public static void setUp() {
        transactionDto = new TransactionDto(OperationType.BALANCE,
                new Fruit("apple"), 20);
    }

    @Test
    public void parse_ValidData_Ok() {
        List<String> readFile = new ArrayList<>();
        readFile.add("b,apple,20");
        List<TransactionDto> actual = new FruitShopDataParserImpl().parse(readFile);
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(transactionDto);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidFruitName_NotOk() {
        List<String> readFile = new ArrayList<>();
        readFile.add("b,20");
        new FruitShopDataParserImpl().parse(readFile);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidOperation_NotOk() {
        List<String> readFile = new ArrayList<>();
        readFile.add(",apple,20");
        new FruitShopDataParserImpl().parse(readFile);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidAmount_NotOk() {
        List<String> readFile = new ArrayList<>();
        readFile.add("p,apple,-10");
        new FruitShopDataParserImpl().parse(readFile);
    }

    @Test (expected = RuntimeException.class)
    public void parse_NullCheck_NotOk() {
        new FruitShopDataParserImpl().parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parse_CheckEmptyFile_NotOk() {
        List<String> readFile = new ArrayList<>();
        new FruitShopDataParserImpl().parse(readFile);
    }

    @Test
    public void getFromCsvRow_ValidData_Ok() {
        String line = "b,apple,20";
        TransactionDto actual = new FruitShopDataParserImpl().getFromCsvRow(line);
        TransactionDto expected = transactionDto;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getFromCsvRow_InvalidData_NotOk() {
        new FruitShopDataParserImpl().getFromCsvRow("");
    }

    @Test (expected = RuntimeException.class)
    public void getFromCsvRow_NullCheck_NotOk() {
        new FruitShopDataParserImpl().getFromCsvRow(null);
    }
}
