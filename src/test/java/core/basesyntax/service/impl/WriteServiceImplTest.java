package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/Report.csv";
    private static final String CONTENT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90";
    private static WriteServiceImpl writeService = new WriteServiceImpl();

    @BeforeEach
    public void init() {
        Path path = Path.of(FILE_PATH);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException("Cannot delete a file: " + FILE_PATH, e);
            }
        }
    }

    @Test
    public void implementsCorrectInterface_OK() {
        List<Class<?>> interfaces =
                Arrays.asList(writeService.getClass().getInterfaces());
        Assertions.assertEquals(1, interfaces.size());
        Assertions.assertTrue(interfaces.contains(WriterService.class));
    }

    @Test
    public void write_writeValidFile_OK() {
        writeService.write(CONTENT, FILE_PATH);
        Path filePath = Path.of(FILE_PATH);
        Assertions.assertTrue(Files.exists(filePath));
        List<String> readLines;
        try {
            readLines = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file: " + FILE_PATH, e);
        }
        String result = String.join(System.lineSeparator(), readLines);
        Assertions.assertEquals(CONTENT, result);
    }

    @Test
    public void write_wrongFilePath_notOK() {
        String filePath = "src/test/resources/";
        Assertions.assertThrows(RuntimeException.class,
                () -> writeService.write(CONTENT, filePath));
    }
}
