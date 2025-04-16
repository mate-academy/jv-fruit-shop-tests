package model;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ParseService;
import service.ReaderService;
import service.impl.ParseServiceImpl;
import service.impl.ReaderServiceImpl;

public class FruitTransactionTest {
    private static ParseService parseService;
    private static ReaderService readerService;

    @BeforeEach
    void create() {
        parseService = new ParseServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    void parseList_withInvalidOperation_throwsException() {
        String filePath = getResourcePath("modelResource/InvalidOperation.csv");
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                parseService.parseList(readerService.readFromFile(filePath)));
        Assertions.assertEquals("Unsupported operation code: w", exception.getMessage());
    }

    @Test
    void parseList_withNullOperation_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,100", "s,banana,60",
                "p,banana,10", "r,banana,2", ",apple,80", "s,apple,20", "p,apple,10");
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> parseService.parseList(list));
        Assertions.assertEquals("Unsupported operation code: ", exception.getMessage());
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
