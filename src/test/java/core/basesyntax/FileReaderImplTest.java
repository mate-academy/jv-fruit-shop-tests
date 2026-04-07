package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private FileReaderImpl fileReaderImpl = new FileReaderImpl();

    @Test
    void read_validFile_ok() throws Exception {
        URL resource = getClass().getClassLoader().getResource("reportToRead.csv");
        String path = Paths.get(Objects.requireNonNull(resource).toURI()).toString();

        List<String> read = fileReaderImpl.read(path);

        assertFalse(read.isEmpty());
        assertEquals("operation,fruit,quantity", read.get(0));
    }

    @Test
    void read_emptyFile_notOK() throws Exception {
        Path emptyFile = Files.createTempFile("empty",".csv");
        emptyFile.toFile().deleteOnExit();
        assertThrows(RuntimeException.class, () -> fileReaderImpl.read(emptyFile.toString()));
    }

    @Test
    void read_fileNotExist_notOk() {
        assertThrows(IOException.class, () -> fileReaderImpl.read("notExist.csv"));
    }
}
