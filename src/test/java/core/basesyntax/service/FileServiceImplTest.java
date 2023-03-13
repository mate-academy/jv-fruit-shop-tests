package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test //(expected = RuntimeException.class)
    public void readFromFileInputNullPath_NotOk() {
        String path = null;

        try {
            fileService.readFromFile(path);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is null");
    }

    @Test
    public void readFromFileInputIncorrectPath_NotOk() {
        String emptyLinePath = "";

        try {
            fileService.readFromFile(emptyLinePath);
        } catch (RuntimeException e) {
            return;
        }

        String incorrectPath = "-";

        try {
            fileService.readFromFile(incorrectPath);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is null");
    }

    @Test
    public void readFromFileInputIncorrectLinePath_NotOk() {
        String path = "-";

        try {
            fileService.readFromFile(path);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is null");
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

        String path = "src/test/inputdatatest.csv";

        List<String> actual = fileService.readFromFile(path);

        assertEquals(expected, actual);
    }

    @Test
    public void writeToFileNullInputDataLine_NotOk() {
        String data = null;
        String fileName = "testresult.csv";

        try {
            fileService.writeToFile(data, fileName);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is null");
    }

    @Test
    public void writeToFileEmptyInputDataLine_NotOk() {
        String data = "";
        String fileName = "testresult.csv";

        try {
            fileService.writeToFile(data, fileName);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown "
                + "for empty line input data");
    }

    @Test
    public void writeToFileNullInputFileName_NotOk() {
        String data = "Roses are read, violets are blue";
        String fileName = null;

        try {
            fileService.writeToFile(data, fileName);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is null");
    }

    @Test
    public void writeToFileEmptyLineInputFileName_NotOk() {
        String data = "Roses are read, violets are blue";
        String fileName = "";

        try {
            fileService.writeToFile(data, fileName);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown"
                + " if file name is empty line");
    }

    @Test
    public void writeToFileInputData_Ok() {
        String data = "Roses are read, violets are blue";
        String fileName = "testresult.csv";

        try {
            fileService.writeToFile(data, fileName);
        } catch (RuntimeException e) {
            fail();
        }

        List<String> actual = null;
        try {
            actual = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(List.of(data), actual);
    }
}
