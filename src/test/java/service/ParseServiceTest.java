package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.ParseServiceImpl;
import service.impl.ReaderServiceImpl;

public class ParseServiceTest {
    private static ReaderService readerService;
    private static ParseService parseService;

    @BeforeEach
    void create() {
        parseService = new ParseServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    void parseCsvLine_withEmptyLine_throwsException() {
        String filePath = getResourcePath("serviceResource/emptyLine.csv");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Input line cannot be null or empty", exception.getMessage());
    }

    @Test
    void parseCsvLine_withInvalidQuantity_throwsException() {
        String filePath = getResourcePath("serviceResource/invalidQuantity.csv");
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                () -> parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Invalid quantity format in line", exception.getMessage());
    }

    @Test
    void parseCsvLine_withInvalidFieldCount_throwsException() {
        String filePath = getResourcePath("serviceResource/invalidFieldCount.csv");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Invalid CSV format: expected 3 fields, but got 2 in line: b,banana",
                illegalArgumentException.getMessage());
    }

    @Test
    void parseCsvLine_withEmptyFruit_throwsException() {
        String filePath = getResourcePath("serviceResource/emptyFruit.csv");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Fruit name cannot be empty in line: b, ,100",
                exception.getMessage());
    }

    @Test
    void parseCsvLine_withNegativeQuantity_throwsException() {
        String filePath = getResourcePath("serviceResource/negativeQuantity.csv");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Quantity cannot be negative or zero in line: b,banana,-200",
                exception.getMessage());
    }

    @Test
    void parseCsvLine_withZeroQuantity_throwsException() {
        String filePath = getResourcePath("serviceResource/zeroQuantity.csv");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Quantity cannot be negative or zero in line: b,banana,0",
                illegalArgumentException.getMessage());
    }

    public String getResourcePath(String fileName) {
        try {
            return Paths.get(this.getClass().getClassLoader()
                    .getResource(fileName)
                    .toURI())
                .toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
