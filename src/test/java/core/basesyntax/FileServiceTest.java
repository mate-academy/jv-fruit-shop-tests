package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import core.basesyntax.service.impl.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileServiceTest {
    private static final String INPUT_FILE_TEST =
            "src/test/java/core/basesyntax/resources/inputTestFile.csv";
    private static final String OUTPUT_FILE_TEST =
            "src/test/java/core/basesyntax/resources/outputFileTest.csv";

    private static FileService fileService;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileServiceImpl();
    }

    @Test
    void readFromFile_wrongPath_NotOk() {
        assertThrows(RuntimeException.class,() -> fileService.readFromFile("wronhPath.csv"));
    }

    @Test
    void readFromFile_correctPath_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,10");
        assertEquals(expected, fileService.readFromFile(INPUT_FILE_TEST));
    }

    @Test
    void writeToFile_wrongPath_NotOk() {
        assertThrows(RuntimeException.class,() -> fileService
                .writeToFile("", "report"));

    }

    @Test
    void writeToFile_correctData_Ok() {
        String report = "type,fruit,quantity";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        fileService.writeToFile(OUTPUT_FILE_TEST, report);
        assertEquals(expected, fileService.readFromFile(OUTPUT_FILE_TEST));
    }
}
