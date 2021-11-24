package core.basesyntax.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.operation.OperationType;
import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static List<FruitRecordDto> fruitList;
    private static String path;
    private static List<FruitRecordDto> expected;
    private static List<FruitRecordDto> actual;

    @BeforeClass
    public static void initialize() {
        fruitList = new ArrayList<>();
        fruitList.add(FruitRecordDto.builder()
                .type(OperationType.BALANCE)
                .fruit(Fruit.builder().name("banana").build())
                .amount(20).build());
        fruitList.add(FruitRecordDto.builder()
                .type(OperationType.SUPPLY)
                .fruit(Fruit.builder().name("banana").build())
                .amount(100).build());
        fruitList.add(FruitRecordDto.builder()
                .type(OperationType.PURCHASE)
                .fruit(Fruit.builder().name("banana").build())
                .amount(13).build());
        readerService = new ReaderServiceImpl();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
    }

    @After
    public void clear() {
        expected.clear();
        actual.clear();
    }

    @Test
    public void read_rightInput_Ok() {
        path = "src/test/resources/rightInput.csv";
        expected = fruitList;
        actual = readerService.read(path);
        Assert.assertEquals("Failed with right data, expected: "
                + expected + ", but was:" + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_nullPath_NotOK() {
        path = null;
        actual = readerService.read(path);
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyColumnName_NotOk() {
        path = "src/test/resources/emptyColumnName.csv";
        actual = readerService.read(path);
        System.out.println(actual);
    }

    @Test
    public void read_EmptyFile_Ok() {
        path = "src/test/resources/emptyFile.csv";
        actual = readerService.read(path);
        Assert.assertEquals("Failed with empty file, expected: " + expected
                + ", but was: " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_EmptyColumnAmount_NotOk() {
        path = "src/test/resources/emptyColumnAmount";
        actual = readerService.read(path);
    }

    @Test(expected = RuntimeException.class)
    public void read_negativeAmount_NotOk() {
        path = "src/test/resources/negativeAmount";
        actual = readerService.read(path);
    }
}
