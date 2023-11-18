package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteDataToFileServiceImplTest {
    private static WriteDataToFileServiceImpl writeData;
    private static ReadDataFromFileServiceImpl readDataFromFileService;

    private static final String PATH_REPORT = "src/main/resources/report.csv";
    private static final String PATH_REPORT_INCORRECT = "src/main/resources/report1.csv";
    private String report;

    @BeforeAll
    static void beforeAll() {
        writeData = new WriteDataToFileServiceImpl();
    }

    @BeforeEach
    void setUp() {
        report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,150"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator();
    }

    @Test
    void writeData_correctWrite_Ok() {
        String after;
        writeData.writeData(PATH_REPORT, report);
        try {
            after = Files.readString(Paths.get(PATH_REPORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(report, after);
    }

    @Test
    void writeData_incorrectPathToFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writeData.writeData(PATH_REPORT_INCORRECT, report));
    }

}
