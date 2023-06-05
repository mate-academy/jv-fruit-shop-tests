package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    public static final String PATH_TO_REPORT_FILE = "src/test/resources/testReportFile.csv";
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writerService_wrongPath_notOk() {
        Assertions.assertThrows(
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
        writerService.writeToFile(PATH_TO_REPORT_FILE, excepted);
        String actual = validData(PATH_TO_REPORT_FILE);
        Assertions.assertEquals(excepted, actual);
    }

    private String validData(String path) {
        try {
            List<String> strList = Files.readAllLines(Path.of(path));
            return strList.stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
