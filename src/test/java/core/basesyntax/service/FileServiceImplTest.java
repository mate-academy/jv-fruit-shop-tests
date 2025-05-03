package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileInputNullPath_NotOk() {
        String path = null;
        fileService.readFromFile(path);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileInputIncorrectPath_NotOk() {
        String emptyLinePath = "";
        fileService.readFromFile(emptyLinePath);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileInputIncorrectLinePath_NotOk() {
        String path = "-";
        fileService.readFromFile(path);
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");

        String path = "src/test/resources/test-input-data.csv";

        List<String> actual = fileService.readFromFile(path);

        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFileNullInputDataLine_NotOk() {
        String data = null;
        String fileName = "src/test/resources/test-result.csv";
        fileService.writeToFile(data, fileName);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFileEmptyLineInputDataLine_NotOk() {
        String data = "";
        String fileName = "src/test/resources/test-result.csv";
        fileService.writeToFile(data, fileName);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFileNullInputFileName_NotOk() {
        String data = "Roses are read, violets are blue";
        String fileName = null;
        fileService.writeToFile(data, fileName);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFileEmptyLineInputFileName_NotOk() {
        String data = "Roses are read, violets are blue";
        String fileName = "";
        fileService.writeToFile(data, fileName);
    }

    @Test
    public void writeToFileInputData_Ok() {
        String data = "Roses are read, violets are blue";
        String fileName = "src/test/resources/test-result.csv";
        try {
            fileService.writeToFile(data, fileName);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't write file to file " + data);
        }
        List<String> actual = null;
        try {
            actual = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file"
                    + fileName);
        }

        assertEquals(List.of(data), actual);
    }
}
