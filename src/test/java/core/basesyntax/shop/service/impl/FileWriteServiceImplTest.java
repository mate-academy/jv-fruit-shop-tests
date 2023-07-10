package core.basesyntax.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.shop.service.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriteServiceImplTest {
    public static final String WRONG_PATH_TO_FILE = "";
    private static FileWriteService fileWriteService;
    private static final String PATH_TO_FILE = "src/test/resources/write.csv";
    private static final String REPORT = "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "r,banana,10";

    @BeforeAll
    public static void beforeAll() {
        fileWriteService = new FileWriteServiceImpl();
    }

    @Test
    void writeReportToFile_ok() {
        fileWriteService.write(PATH_TO_FILE, REPORT);
        String actualReport = readDataFromFile(PATH_TO_FILE);
        assertEquals(REPORT, actualReport);
    }

    @Test
    void writeReportToFileWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriteService.write(WRONG_PATH_TO_FILE, REPORT));
    }

    private String readDataFromFile(String path) {
        try {
            List<String> strList = Files.readAllLines(Path.of(path));
            return strList.stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

}
