package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exceptions.FileException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderToolTest {
    private static final String CORRECT_FILE_AND_PATH = "src/test/resources/input/correctInput.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/input/emptyFile.csv";
    private static final String EMPTY_FILE_EXCEPTION_TEXT = "There is no path to read ";
    private static final String INCORRECT_FILE_NAME_TO_READ =
            "src/test/resources/input/incorrectInput.csv";
    private static final String INCORRECT_FILE_NAME_EXCEPTION_TEXT =
            String.format("Can't find file \"" + INCORRECT_FILE_NAME_TO_READ + "\" to read ");
    private static final String INCORRECT_FILE_TYPE_TO_READ = "src/test/resources/correctInput.txt";
    private static final String INCORRECT_FILE_TYPE_EXCEPTION_TEXT =
            String.format("Can't find file \"" + INCORRECT_FILE_TYPE_TO_READ + "\" to read ");
    private static final String INCORRECT_PATH_TO_READ = "src/correctInput.csv";
    private static final String INCORRECT_PATH_TO_READ_EXCEPTION_TEXT =
            String.format("Can't find file \"" + INCORRECT_PATH_TO_READ + "\" to read ");
    private static final FileReaderToolImpl fileReader = new FileReaderToolImpl();

    @Test
    public void fileReaderTool_correctData_Ok() {
        List<String> expected = formCorrectDataList();

        List<String> actual = fileReader.getData(CORRECT_FILE_AND_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void fileReaderTool_emptyFile_Ok() {
        List<String> expected = new ArrayList<>();

        List<String> actual = fileReader.getData(EMPTY_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void fileReaderTool_IncorrectFileName_notOk() {
        Throwable exception = assertThrows(FileException.class,
                () -> fileReader.getData(INCORRECT_FILE_NAME_TO_READ));

        assertEquals(INCORRECT_FILE_NAME_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void fileReaderTool_IncorrectFileType_notOk() {
        Throwable exception = assertThrows(FileException.class,
                () -> fileReader.getData(INCORRECT_FILE_TYPE_TO_READ));

        assertEquals(INCORRECT_FILE_TYPE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void fileReaderTool_IncorrectPath_notOk() {
        Throwable exception = assertThrows(FileException.class,
                () -> fileReader.getData(INCORRECT_PATH_TO_READ));

        assertEquals(INCORRECT_PATH_TO_READ_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void fileReaderTool_EmptyPath_notOk() {
        Throwable exception = assertThrows(FileException.class,
                () -> fileReader.getData(null));

        assertEquals(EMPTY_FILE_EXCEPTION_TEXT, exception.getMessage());
    }

    private List<String> formCorrectDataList() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        return expected;
    }
}
