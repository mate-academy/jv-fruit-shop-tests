package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String TEXT_EXAMPLE = """
            JavaJavaJavaJavaJavaJava
            JavaJavaJavaJavaJavaJava
            JavaJavaJavaJavaJavaJava
            """;
    private static final FileWriter WRITER = new WriterServiceImpl();
    private static final String VALID_NEW_FILE_NAME = "src/test/resources/new-file";
    private static final String ALREADY_EXISTING_FILE_NAME
            = "src/test/resources/already-existing-file";

    @BeforeEach
    public void deleteValidFile() {
        if (Files.exists(Paths.get(VALID_NEW_FILE_NAME))) {
            try {
                Files.delete(Paths.get(VALID_NEW_FILE_NAME));
            } catch (IOException e) {
                throw new RuntimeException("Can not delete the file");
            }
        }
    }

    @Test
    public void writeValidDataToValidFile_ok() {
        WRITER.write(TEXT_EXAMPLE, VALID_NEW_FILE_NAME);
        String dataFromFile = null;
        try {
            dataFromFile = Files.readString(Paths.get(VALID_NEW_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can not write data to file");
        }
        Assertions.assertEquals(TEXT_EXAMPLE, dataFromFile);
    }

    @Test
    public void writeWithNullNewFileName_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> WRITER.write(TEXT_EXAMPLE, null));
        Assertions.assertEquals("Error: The new report name null or empty!",
                exception.getMessage());
    }

    @Test
    public void writeWithNullData_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> WRITER.write(null, VALID_NEW_FILE_NAME));
        Assertions.assertEquals("Error: The data is null or empty!", exception.getMessage());
    }

    @Test
    public void writeToFileThatAlreadyExist_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> WRITER.write(TEXT_EXAMPLE, ALREADY_EXISTING_FILE_NAME));
        Assertions.assertEquals("A file with this name already exists. Name: "
                + ALREADY_EXISTING_FILE_NAME, exception.getMessage());
    }
}
