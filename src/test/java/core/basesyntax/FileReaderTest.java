package core.basesyntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import service.FileReaderImpl;

public class FileReaderTest {
    private static final String FILE_NAME_WITH_DATA = "src/test/resources/readTestFileWithData.csv";
    private static final String EMPTY_DATA_TYPE = "src/test/resources/readEmptyFile.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources/wrong.csv";
    private static final List<String> CORRECT_DATA = new ArrayList<>(Arrays.asList("b,banana,20",
            "b,apple,100", "s,banana,100"));
    private static final List<String> EMPTY_DATA = new ArrayList<>();
    private FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    public void throwsIoException() {
        Assert.assertThrows(RuntimeException.class, () -> fileReader.read(WRONG_FILE_NAME));
    }

    @Test
    public void readAndGetOk() {
        List<String> strings = fileReader.read(FILE_NAME_WITH_DATA);
        Assert.assertEquals(CORRECT_DATA, strings);
    }

    @Test
    public void readAndGetOkEmptyFile() {
        List<String> strings = fileReader.read(EMPTY_DATA_TYPE);
        Assert.assertEquals(EMPTY_DATA, strings);
    }
}
