package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.DataReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderImplTest {
    private static DataReader<List<String>> dataReader;
    private static final String VALID_CSV_FILE_PATH = "src/test/resources/validData.csv";
    private static final String INVALID_CSV_FILE_PATH = "src/main/resources/iNvAlIdFilE.exe";
    private static final String INVALID_PATH_EXCEPTION_MESSAGE =
            "Unable to get data from file by path: ";
    private static final String NULL_PATH_EXCEPTION_MESSAGE = "File path can`t be null";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void readData_actualReturnEqualsValid_ok() {
        List<String> actualData = new FileReaderImpl(VALID_CSV_FILE_PATH).readData();
        List<String> expectedData;
        try {
            expectedData = Files.readAllLines(Path.of(VALID_CSV_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(actualData, expectedData);
    }

    @Test
    public void readData_nullFilePathThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        dataReader = new FileReaderImpl(null);
        dataReader.readData();
    }

    @Test
    public void readData_invalidDataFileThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        dataReader = new FileReaderImpl(INVALID_CSV_FILE_PATH);
        dataReader.readData();
    }

    @Test
    public void readData_ExceptionMessageWithInvalidPath_ok() {
        expectedException.expectMessage(INVALID_PATH_EXCEPTION_MESSAGE + INVALID_CSV_FILE_PATH);
        dataReader = new FileReaderImpl(INVALID_CSV_FILE_PATH);
        dataReader.readData();
    }

    @Test
    public void readData_ExceptionMessageWithNullPath_ok() {
        expectedException.expectMessage(NULL_PATH_EXCEPTION_MESSAGE);
        dataReader = new FileReaderImpl(null);
        dataReader.readData();
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
