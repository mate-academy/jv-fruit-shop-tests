package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderCsvImplTest {
    private static final Reader FILE_READER_CSV = new FileReaderCsvImpl();
    private static final String INCORRECT_FILE_TYPE_MESSAGE = "Incorrect file type";
    private static final String FILE_PATH_IS_NULL_MESSAGE = "File path is null";
    private static final String FILE_DOESNT_EXIST_MESSAGE = "File doesn't exist in directory";
    private static final String FILE_IS_EMPTY_MESSAGE = "File is empty";
    private static final String WRONG_FIRST_LINE_FORMAT_MESSAGE =
            "First line format doesn't match with: 'type,fruit,quantity'";

    @Test
    void read_correctCsvFile_Ok() {
        String correctCsvFile1 = "src/test/resources/reader/correct-file1.csv";
        String correctCsvFile2 = "src/test/resources/reader/correct-file2.csv";
        String expectedString = "line";
        List<String> linesFrom1 = FILE_READER_CSV.read(correctCsvFile1);
        for (String currentLine : linesFrom1) {
            assertEquals(expectedString, currentLine);
        }
        List<String> linesFrom2 = FILE_READER_CSV.read(correctCsvFile2);
        for (String currentLine : linesFrom2) {
            assertEquals(expectedString, currentLine);
        }
    }

    @Test
    void read_notCsvFileExtension_NotOk() {
        String incorrectFileType1 = "src/test/resources/reader/incorrect-file-type1.xml";
        String incorrectFileType2 = "src/test/resources/reader/incorrect-file-type2.txt";
        Throwable exception1 = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(incorrectFileType1));
        assertEquals(INCORRECT_FILE_TYPE_MESSAGE, exception1.getMessage());
        Throwable exception2 = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(incorrectFileType2));
        assertEquals(INCORRECT_FILE_TYPE_MESSAGE, exception2.getMessage());
    }

    @Test
    void read_filePathIsNull_NotOk() {
        String pathIsNull = null;
        Throwable exception = assertThrows(NullPointerException.class, () ->
                FILE_READER_CSV.read(pathIsNull));
        assertEquals(FILE_PATH_IS_NULL_MESSAGE, exception.getMessage());
    }

    @Test
    void read_fileDoesntExist_notOk() {
        String notExistedFile = "src/test/resources/reader/not-existing-file.csv";
        Throwable exception = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(notExistedFile));
        assertEquals(FILE_DOESNT_EXIST_MESSAGE, exception.getMessage());
    }

    @Test
    void read_emptyFile_NotOk() {
        String emptyFile1 = "src/test/resources/reader/empty-file2.csv";
        String emptyFile2 = "src/test/resources/reader/empty-file2.csv";
        Throwable exception1 = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(emptyFile1));
        assertEquals(FILE_IS_EMPTY_MESSAGE, exception1.getMessage());
        Throwable exception2 = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(emptyFile2));
        assertEquals(FILE_IS_EMPTY_MESSAGE, exception2.getMessage());
    }

    @Test
    void read_wrongFirstLineFormat_notOk() {
        String wrongFirstLineFile1 = "src/test/resources/reader/wrong-first-line-file1.csv";
        String wrongFirstLineFile2 = "src/test/resources/reader/wrong-first-line-file1.csv";
        Throwable exception1 = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(wrongFirstLineFile1));
        assertEquals(WRONG_FIRST_LINE_FORMAT_MESSAGE, exception1.getMessage());
        Throwable exception2 = assertThrows(RuntimeException.class, () ->
                FILE_READER_CSV.read(wrongFirstLineFile2));
        assertEquals(WRONG_FIRST_LINE_FORMAT_MESSAGE, exception2.getMessage());
    }
}
