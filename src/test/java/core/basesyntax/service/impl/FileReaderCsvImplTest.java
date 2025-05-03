package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.Reader;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class FileReaderCsvImplTest {
    private static final Reader FILE_READER_CSV = new FileReaderCsvImpl();
    private static final String correctCsvFile1 = "src/test/resources/reader/correct-file1.csv";
    private static final String correctCsvFile2 = "src/test/resources/reader/correct-file2.csv";
    private static final int EXPECTED_LINES_FROM_FILE1 = 7;
    private static final int EXPECTED_LINES_FROM_FILE2 = 8;
    private static final String INCORRECT_FILE_TYPE_MESSAGE = "Incorrect file type";
    private static final String FILE_PATH_IS_NULL_MESSAGE = "File path is null";
    private static final String FILE_DOESNT_EXIST_MESSAGE = "File doesn't exist in directory";
    private static final String FILE_IS_EMPTY_MESSAGE = "File is empty";
    private static final String WRONG_FIRST_LINE_FORMAT_MESSAGE =
            "First line format doesn't match with: 'type,fruit,quantity'";

    @ParameterizedTest
    @MethodSource
    void read_correctCsvFile_ok(String path, int expectedLineNumber) {
        List<String> linesFromFile = FILE_READER_CSV.read(path);
        assertEquals(expectedLineNumber, linesFromFile.size());
    }

    static Stream<Arguments> read_correctCsvFile_ok() {
        return Stream.of(
          Arguments.of(correctCsvFile1, EXPECTED_LINES_FROM_FILE1),
          Arguments.of(correctCsvFile2, EXPECTED_LINES_FROM_FILE2)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/reader/incorrect-file-type.xml",
            "src/test/resources/reader/incorrect-file-type.jpg",
            "src/test/resources/reader/incorrect-file-type.doc",
            "src/test/resources/reader/incorrect-file-type.txt"
    })
    void read_notCsvFileExtension_notOk(String incorrectFileExtension) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(incorrectFileExtension));
        assertEquals(INCORRECT_FILE_TYPE_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    void read_filePathIsNull_notOk(String path) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                FILE_READER_CSV.read(path));
        assertEquals(FILE_PATH_IS_NULL_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/reader/not-existing-file1.csv",
            "src/test/resources/reader/not-existing-file2.csv",
            "src/test/resources/reader/not-existing-file3.csv",
            "src/test/resources/reader/not-existing-file4.csv"
    })
    void read_fileDoesntExist_notOk(String notExistedFile) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(notExistedFile));
        assertEquals(FILE_DOESNT_EXIST_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/reader/empty-file1.csv",
            "src/test/resources/reader/empty-file2.csv"
    })
    void read_emptyFile_notOk(String emptyFile) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(emptyFile));
        assertEquals(FILE_IS_EMPTY_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/reader/wrong-first-line-file1.csv",
            "src/test/resources/reader/wrong-first-line-file2.csv"
    })
    void read_wrongFirstLineFormat_notOk(String wrongFirstLineFile) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(wrongFirstLineFile));
        assertEquals(WRONG_FIRST_LINE_FORMAT_MESSAGE, exception.getMessage());
    }
}
