package core.basesyntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import service.FileReaderImpl;

public class FileReaderTest {
    private static final String fileNameWithData = "src/test/resources/readTestFileWithData.csv";
    private static final String emptyDataFile = "src/test/resources/readEmptyFile.csv";
    private static final String wrongFileName = "src/test/resources/wrong.csv";
    private static final FileReaderImpl fileReader = new FileReaderImpl();
    private static final List<String> correctData = new ArrayList<>(Arrays.asList("b,banana,20",
            "b,apple,100", "s,banana,100"));
    private static final List<String> emptyData = new ArrayList<>();

    @Test
    public void testIoException() {
        Assert.assertThrows(RuntimeException.class, () -> fileReader.read(wrongFileName));
    }

    @Test
    public void testReadFileWithData() {
        List<String> strings = fileReader.read(fileNameWithData);
        Assert.assertEquals(correctData, strings);
    }

    @Test
    public void testFileWithNoCommands() {
        List<String> strings = fileReader.read(emptyDataFile);
        Assert.assertEquals(emptyData, strings);
    }
}
