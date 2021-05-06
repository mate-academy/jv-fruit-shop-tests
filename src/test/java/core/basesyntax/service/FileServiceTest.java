package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.file.FileService;
import core.basesyntax.service.file.FileServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceTest {
    private static FileService fileService;
    private static final String READING_TEST = "src/test/resources/testInput";
    private static final String WRITING_TEST = "src/test/resources/testOutput";
    private static final String INVALID_FILE_TEST = "src/test/resources/solidniyFile";

    @BeforeClass
    public static void setFileService() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFile_FileValid_isOk() {
        List<String> result = fileService.readFile(READING_TEST);
        List<String> expected = List.of("fruit,quantity", "apple,100", "orange,50", "banana,25");
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_FileInvalid_notOk() {
        fileService.readFile(INVALID_FILE_TEST);
    }

    @Test
    public void writeToFile_isOk() {
        String data = "fruit,quantity" + System.lineSeparator() + "apple,100"
                + System.lineSeparator() + "banana,150" + System.lineSeparator();
        fileService.writeToFile(WRITING_TEST, data);
        List<String> result = fileService.readFile(WRITING_TEST);
        List<String> expected = List.of("fruit,quantity", "apple,100", "banana,150");
        assertEquals(expected, result);
    }
}
