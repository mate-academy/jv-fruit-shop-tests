package service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileReaderService;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private static List<String> expected;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void fileReader_wrongWay_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("can`t read in file");
        fileReaderService.read("src/test/resources/input.csv");
    }

    @Test
    public void filerWriter_nullWay_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("the path cannot be null");
        fileReaderService.read(null);
    }

    @Test
    public void fileReader_validWork_Ok() {
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        Assert.assertEquals(expected, fileReaderService.read("src\\test\\resources\\valid.csv"));
    }
}
