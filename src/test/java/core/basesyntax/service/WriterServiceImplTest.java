package core.basesyntax.service;

import core.basesyntax.impl.WriteServiceImpl;
import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static WriterService writeService;

    @BeforeAll
    public static void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    void writeService_writeToFile_Ok() {
        String filePath = "src/test/java/resources/report.csv";
        String data = Arrays.asList("b,banana,20", "b,apple,100", "b,apple,5").toString();
        writeService.writeToFile(filePath, data);
        File outputFile = new File(filePath);
        Assertions.assertTrue(outputFile.exists());
    }

    @Test
    void writeService_writeToFile_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> writeService.writeToFile("test/src/test.csv", "content")
        );
    }
}
