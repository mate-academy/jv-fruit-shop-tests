package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileServiceImplTest {
    public static final String PATH_TO_READ_FILE = "src/test/resources/input_file";
    public static final String PATH_TO_WRITE_FILE = "src/test/resources/report_file";
    public static final String REPORT_DATA = "fruits, quantity, banana,152, apple, 90";
    private FileService fileService;

    @Before
    public void setUp() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void parseDataFromFile_Ok() {
        List<String> expectedData = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
                "s,banana,50");
        List<String> actualData = fileService.parseDataFromFile(PATH_TO_READ_FILE);
        assertEquals(expectedData, actualData);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataFromFile_fileDoesNotExist_NotOk() {
        fileService.parseDataFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataFromFile_InvalidFileExtension_NotOk() {
        String pathName = "data.docx";
        fileService.parseDataFromFile(pathName);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataFromFile_EmptyFile_NotOk() {
        fileService.parseDataFromFile("");
    }

    @Test
    public void writeDataToFile_Ok() {
        fileService.writeDataToFile(REPORT_DATA, PATH_TO_WRITE_FILE);
        List<String> expectedData = List.of("fruits, quantity, banana,152, apple, 90");
        List<String> actualData;
        try {
            actualData = Files.readAllLines(Path.of(PATH_TO_WRITE_FILE));
        } catch (IOException e) {
            throw new RuntimeException("No such file or directory in: "
                    + PATH_TO_WRITE_FILE, e);
        }
        assertEquals(expectedData, actualData);
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_fileDoesNotExist_NotOk() {
        fileService.writeDataToFile(REPORT_DATA, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_nullPath_NotOk() {
        fileService.writeDataToFile(REPORT_DATA, null);
    }
}
