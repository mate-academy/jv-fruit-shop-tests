package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileServiceImpl fileService;
    private static final String INPUT_FILE_PATH = "src/test/resources/input_test.csv";
    private static final String OUTPUT_FILE_PATH = "src/test/resources/report_test.csv";
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + "banana,152";
    }

    @Test
    public void write_validFilePathAndReport_Ok() {
        fileService.write(report, OUTPUT_FILE_PATH);
        String expected = report;
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Files.readString method can't read file "
                + "inside writeToFile_validFilePathAndReport_Ok method");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        String wrongOutputFilePath = "src/test/java/resources/2/output_database.csv";
        fileService.write(report, wrongOutputFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyFilePath_notOk() {
        String wrongOutputFilePath = "";
        fileService.write(report, wrongOutputFilePath);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullFilePath_notOk() {
        String wrongOutputFilePath = null;
        fileService.write(report, wrongOutputFilePath);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        String report = "";
        fileService.write(report, OUTPUT_FILE_PATH);
        String expected = "";
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Files.readString method can't read file inside "
                + "writeToFile_validFilePathAndReport_Ok method");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullReport_notOk() {
        String report = null;
        fileService.write(report, OUTPUT_FILE_PATH);
    }

    @Test
    public void readFile_validFilePath_Ok() {
        List<String> expected =
                List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileService.read(INPUT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_invalidFilePath_notOk() {
        String wrongInputFilePath = "src/";
        fileService.read(wrongInputFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nullFilePath_notOk() {
        String nullPath = null;
        fileService.read(nullPath);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_emptyPath_notOk() {
        String emptyPath = "";
        fileService.read(emptyPath);
    }
}
