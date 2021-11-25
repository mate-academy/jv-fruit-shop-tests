package core.basesyntax.service.file.impl;

import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.service.file.ReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderCsvTest {
    private static ReaderService readerService;
    private static String validDataPath;
    private static String invalidDataPath;
    private static String emptyDataPath;
    private static String wrongHeadValidDataPath;
    private static String invalidDataWrongHeadPath;
    private static String invalidDataValidHeadPath;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderCsv();
        validDataPath = "src/test/resources/validData.csv";
        emptyDataPath = "src/test/resources/emptyData.csv";
        invalidDataPath = "src/test/resources/file.csv";
        wrongHeadValidDataPath = "src/test/resources/validDataWrongHead.csv";
        invalidDataWrongHeadPath = "src/test/resources/invalidDataWrongHead.csv";
        invalidDataValidHeadPath = "src/test/resources/invalidDataValidHead.csv";
    }

    @Test
    public void readFile_ValidData_Ok() {
        String[] expected = {
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "s,ginger,25",
                "p,apple,20",
                "p,ginger,8",
                "p,banana,5",
                "s,banana,50"
        };
        String[] actual = readerService.readFile(validDataPath);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void readFile_InvalidDataValidHead_Ok() {
        String[] expected = {
                "Honolulu,Hand,China",
                "Ship,Tea,Sword",
                "1522,Wilhelm,(0)^(0)"
        };
        String[] actual = readerService.readFile(invalidDataValidHeadPath);
        assertArrayEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_EmptyData_NotOk() {
        readerService.readFile(emptyDataPath);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_InvalidDataWrongHead_NotOk() {
        readerService.readFile(invalidDataWrongHeadPath);
    }

    @Test(expected = RuntimeException.class)
    public void radFile_ValidDataWrongHead_NotOk() {
        readerService.readFile(wrongHeadValidDataPath);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_InvalidPath() {
        readerService.readFile(invalidDataPath);
    }
}
