package core.basesyntax.serviceimpl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @DisplayName("Valid report path")
    @Test
    void fileReader_ValidFile_Ok() {
        List<String> expected = List.of("b,Apple,20");
        Assertions.assertEquals(fileReaderService.getInputData(
                "src/test/resources/tests/inputTest.csv"), expected);
    }

    @DisplayName("Not existing report path")
    @Test
    void fileReader_InvalidFileName_NotOk() {
        Assert.assertThrows(InvalidDataException.class,
                () -> fileReaderService.getInputData("notExistingFileName"));
    }

    @DisplayName("Null file path")
    @Test
    void fileReader_NullFileName_NotOk() {
        Assert.assertThrows(InvalidDataException.class,
                () -> fileReaderService.getInputData(null));
    }
}
