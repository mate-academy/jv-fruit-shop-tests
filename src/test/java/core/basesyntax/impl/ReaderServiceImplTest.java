package core.basesyntax.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.operation.OperationType;
import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static List<FruitRecordDto> fruitList;

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
    }

    @Test
    public void read_rightInput_Ok() {
        String path = "src/test/resources/rightInput.csv";
        List<FruitRecordDto> expected = fruitList;
        List<FruitRecordDto> actual = readerService.read(path);
        Assert.assertEquals("Failed with right data, expected: "
                + expected + ", but was:" + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_nullPath_NotOK() {
        String path = null;
        List<FruitRecordDto> actual = readerService.read(path);
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyColumnName_NotOk() {
        String path = "src/test/resources/emptyColumnName.csv.csv";
        List<FruitRecordDto> actual = readerService.read(path);
    }

    @Test
    public void read_EmptyFile_Ok() {
        String path = "src/test/resources/emptyFile.csv";
        List<FruitRecordDto> expected = new ArrayList<>();
        List<FruitRecordDto> actual = readerService.read(path);
        Assert.assertEquals("Failed with empty file, expected: " + expected
                + ", but was: " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_EmptyColumnAmount() {
        String path = "src/test/resources/emptyColumnAmount";
        List<FruitRecordDto> actual = readerService.read(path);
    }

    @Test(expected = RuntimeException.class)
    public void read_negativeAmount() {
        String path = "src/test/resources/negativeAmount";
        List<FruitRecordDto> actual = readerService.read(path);
    }
}
