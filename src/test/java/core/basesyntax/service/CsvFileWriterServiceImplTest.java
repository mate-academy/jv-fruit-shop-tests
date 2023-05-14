package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitShopException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileReaderServiceImpl fileReader;
    private static CsvFileWriterServiceImpl fileWriter;
    private static final String DATA_FOR_WRITTEN_TEST_1 = "Something";
    private static final String DATA_FOR_WRITTEN_TEST_2 = "written";
    private static final String MESSAGE_FOR_WRITING = "Something"
            + System.lineSeparator() + "written";
    private static final String PATH_TO_WRITE_TEST_FILE =
            "src/test/java/core/basesyntax/sourceFile/fileToWriteInWriterTest.csv";
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new CsvFileReaderServiceImpl();
        fileWriter = new CsvFileWriterServiceImpl();
    }

    @Test(expected = FruitShopException.class)
    public void writeInFile_writeInNullFile_notOk() {
        fileWriter.writeInFile(null, MESSAGE_FOR_WRITING);
    }

    @Test(expected = FruitShopException.class)
    public void writeInFile_writeNullData_notOk() {
        fileWriter.writeInFile(PATH_TO_WRITE_TEST_FILE, null);
    }

    @Test
    public void writeInFile_correctDataAndFile_ok() {
        actual = fileReader.readFromFile(PATH_TO_WRITE_TEST_FILE);
        fileWriter.writeInFile(PATH_TO_WRITE_TEST_FILE, MESSAGE_FOR_WRITING);
        expected = new ArrayList<>();
        expected.add(DATA_FOR_WRITTEN_TEST_1);
        expected.add(DATA_FOR_WRITTEN_TEST_2);
        assertEquals(actual, expected);

    }
}
