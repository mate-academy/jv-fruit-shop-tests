package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    public static final String PATH_TO_REPORT_FILE_TEST = "src/test/resources/testReportFile.csv";
    private static WriterServiceImpl writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writerService_wrongPathToFile_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> writerService.writeToFile("test/resources/", "report")
        );
    }

    @Test
    void writerService_validInput_ok() {
        String excepted =
                "fruit,quantity "
                        + "banana,152 "
                        + "apple,120";
        writerService.writeToFile(PATH_TO_REPORT_FILE_TEST, excepted);
        String actual = readData(PATH_TO_REPORT_FILE_TEST);
        assertEquals(excepted, actual);
    }

    private String readData(String path) {
        try {
            List<String> strList = Files.readAllLines(Path.of(path));
            return strList.stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
