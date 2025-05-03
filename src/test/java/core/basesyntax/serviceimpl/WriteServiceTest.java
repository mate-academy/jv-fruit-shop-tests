package core.basesyntax.serviceimpl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.WriteService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WriteServiceTest {
    private static WriteService writeService;

    @BeforeAll
    static void setUp() {
        writeService = new WriteServiceImpl();
    }

    @DisplayName("Valid input data")
    @Test
    void writeService_ValidData_Ok() {
        List<String> expected = List.of("apple,20");
        String expectedReportText = "fruit,quantity" + System.lineSeparator() + "apple,20";
        writeService.writeReport("src/test/resources/tests/outputTest.csv",
                expectedReportText);
        Assertions.assertEquals(readFile(
                "src/test/resources/tests/outputTest.csv"), expected);
    }

    @DisplayName("Null values tests")
    @Test
    void writeService_NullReportPath_NotOk() {
        Assert.assertThrows(InvalidDataException.class,
                () -> writeService.writeReport(null, "apple"));
    }

    @Test
    void writeService_NullTextReport() {
        Assert.assertThrows(InvalidDataException.class, () -> writeService.writeReport(
                "src/test/resources/tests/outputTest.csv", null));
    }

    @Test
    void writeService_NullValues() {
        Assert.assertThrows(InvalidDataException.class,
                () -> writeService.writeReport(null,null));
    }

    private List<String> readFile(String fileName) {
        if (fileName == null) {
            throw new InvalidDataException("File name can't be null");
        }
        File file = new File(fileName);
        try {
            return Files.readAllLines(file.toPath()).stream()
                    .skip(1)
                    .toList();
        } catch (IOException e) {
            throw new InvalidDataException("Can't create report from this file: " + fileName);
        }
    }
}
