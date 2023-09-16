package core.basesyntax;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.WriteService;
import core.basesyntax.serviceimpl.FileReaderServiceImpl;
import core.basesyntax.serviceimpl.WriteServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WriteServiceTest {
    private static WriteService writeService;
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void setUp() {
        writeService = new WriteServiceImpl();
        fileReaderService = new FileReaderServiceImpl();
    }

    @DisplayName("Valid input data")
    @Test
    void writeService_ValidData_Ok() {
        List<String> expected = List.of("apple,20");
        String expectedReportText = "fruit,quantity" + System.lineSeparator() + "apple,20";
        writeService.writeReport("src/main/resources/tests/outputTest.csv",
                expectedReportText);
        Assertions.assertEquals(fileReaderService.getInputData(
                "src/main/resources/tests/outputTest.csv"), expected);
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
                "src/main/resources/reports/outputReport.csv", null));
    }

    @Test
    void writeService_NullValues() {
        Assert.assertThrows(InvalidDataException.class,
                () -> writeService.writeReport(null,null));
    }
}
