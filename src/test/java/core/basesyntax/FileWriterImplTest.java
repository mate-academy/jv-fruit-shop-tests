package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    @Test
    void write_validData_createsFile() {
        FileWriter fileWriter = new FileWriterImpl();
        String data = "b,banana,20\n"
                + "b,apple,50\n"
                + "s,banana,100\n"
                + "p,banana,5";
        fileWriter.write(data, "test.csv");
        File file = new File("test.csv");
        Assertions.assertTrue(file.exists());
    }

    @Test
    void write_emptyString_createsEmptyFile() {
        FileWriter fileWriter = new FileWriterImpl();
        String data = "";
        fileWriter.write(data, "test.csv");
        try {
            Assertions.assertEquals(0, Files.size(Path.of("test.csv")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_toNewFile_createsFile() {
        FileWriter fileWriter = new FileWriterImpl();
        String data = "b,banana,20\n"
                + "b,apple,50\n"
                + "s,banana,100\n"
                + "p,banana,5";
        fileWriter.write(data, "test.csv");
        fileWriter.write(data, "test2.csv");
        File secondFile = new File("test2.csv");
        Assertions.assertTrue(secondFile.exists());
    }

    @Test
    void write_nullData_throwsException() {
        FileWriter writer = new FileWriterImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> writer.write(null, "test.csv"));
    }

    @AfterEach
    void tearDown() {
        File firstFile = new File("test.csv");
        File secondFile = new File("test2.csv");
        if (firstFile.exists()) {
            firstFile.delete();
        }
        if (secondFile.exists()) {
            secondFile.delete();
        }
    }
}
