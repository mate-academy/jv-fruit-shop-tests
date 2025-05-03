package core.basesyntax.filedata;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String[] VALID_DATA_OUTPUT = {"b,banana,20", "b,apple,100",
            "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple,20", "p,banana,5", "s,banana,50"};
    private static final String[] EMPTY_LINE_OUTPUT = new String[]{""};
    private static final String[] NUNBERS_OUTPUT = {"1234567890", "1234567890", "1234567890"};
    private static final String[] SYMBOLS_OUTPUT = {"!@#$%^&*()_?|:;<>"};
    private static final String UNEXISTING_FILE_PATH = "src/test/resources/validData.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/validDataInput.csv";
    private static final String EMPTY_LINE_FILE_PATH = "src/test/resources/emptyInput.csv";
    private static final String NUMBERS_INPUT_FILE_PATH = "src/test/resources/numbersInput.csv";
    private static final String SYMBOLS_INPUT_FILE_PATH = "src/test/resources/symbolsInput.csv";
    private static final String VALID_EXCEPTION_MESSAGE =
            "Can't read file " + new File(UNEXISTING_FILE_PATH);
    private static final FileReader fileReader = new FileReaderImpl();

    @Test
    void readFromFile_validFileData_isOk() {
        String[] actual = fileReader.readFromFile(new File(VALID_FILE_PATH));
        assertArrayEquals(VALID_DATA_OUTPUT, actual);
    }

    @Test
    void readFromFile_invalidFilePath_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(new File(UNEXISTING_FILE_PATH));
        });
        assertEquals(VALID_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void readFromFile_emptyLineFileData_isOk() {
        String[] actual = fileReader.readFromFile(new File(EMPTY_LINE_FILE_PATH));
        assertArrayEquals(EMPTY_LINE_OUTPUT, actual);
    }

    @Test
    void readFromFile_numbersFileData_isOk() {
        String[] actual = fileReader.readFromFile(new File(NUMBERS_INPUT_FILE_PATH));
        assertArrayEquals(NUNBERS_OUTPUT, actual);
    }

    @Test
    void readFromFile_symbolsFileData_isOk() {
        String[] actual = fileReader.readFromFile(new File(SYMBOLS_INPUT_FILE_PATH));
        assertArrayEquals(SYMBOLS_OUTPUT, actual);
    }
}
