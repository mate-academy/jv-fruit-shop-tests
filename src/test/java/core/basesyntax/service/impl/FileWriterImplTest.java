package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILE_NOT_EXIST = "src/main/resourses/input_fake_file.csv";
    private static final String FILE_READ_FOR_TEST = "src/test/resourses/file_read_test.csv";
    private static final String FILE_WRITE_FOR_TEST = "src/test/resourses/file_write_test.csv";
    private FileReader fileReaderTest;
    private FileWriter fileWriterTest;
    private StringBuilder testReport;

    @Before
    public void setUp() {
        fileReaderTest = new FileReaderImpl();
        fileWriterTest = new FileWriterImpl();
        testReport = new StringBuilder();
        testReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,132")
                .append(System.lineSeparator())
                .append("apple,90");
    }

    @Test
    public void writeToWrongFile_Not_Ok() {
        fileWriterTest.writeDataToFile(testReport.toString(), FILE_NOT_EXIST);
    }

    @Test
    public void writeToFile_Ok() {
        fileWriterTest.writeDataToFile(testReport.toString(), FILE_WRITE_FOR_TEST);
        List<String> expected = getList(FILE_READ_FOR_TEST);
        List<String> actual = getList(FILE_WRITE_FOR_TEST);
        Assert.assertEquals(expected, actual);
    }

    private List<String> getList(String fileReadForTest) {
        try {
            return Files.readAllLines(Path.of(fileReadForTest));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file " + fileReadForTest);
        }
    }
}
