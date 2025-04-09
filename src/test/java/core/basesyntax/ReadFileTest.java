package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReaderService;
import service.impl.ParseServiceImpl;
import service.impl.ReaderServiceImpl;

public class ReadFileTest {
    private static ReaderService readerService;

    @BeforeEach
    void create() {
        ParseServiceImpl parseService = new ParseServiceImpl();
        readerService = new ReaderServiceImpl(parseService);
    }

    @Test
    void readFromFile_andWriteFile() throws IOException {
        String fileException = getResourcePath("FileException.csv");
        String[] args = new String[] {getResourcePath("validData.csv"),
                getResourcePath("validDataActual.csv")};
        RunArgument.processArgumentsAndRun(args);
        assertEquals(Files.readString(Path.of(fileException)).trim(),
                Files.readString(Path.of(args[1])).trim());
    }

    @Test
    void readFromFile() {
        String filePath = "Example.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(filePath));
    }

    @Test
    void readFromFile_withEmptyFile_throwsException() {
        String filePath = getResourcePath("emptyFile.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
    }

    @Test
    void readFromFile_withEmptyLine_throwsException() {
        String filePath = getResourcePath("emptyLine.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
    }

    @Test
    void readFromFile_withSpaceFile_throwsException() {
        String filePath = getResourcePath("SpaceFile.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService.readFromFile(filePath));
    }

    @Test
    void readFromFile_withInvalidQuantity_throwsException() {
        String filePath = getResourcePath("invalidQuantity.csv");
        assertThrows(NumberFormatException.class, () -> readerService.readFromFile(filePath));
    }

    @Test
    void readFromFile_withInvalidFieldCount_throwsException() {
        String filePath = getResourcePath("invalidFieldCount.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
    }

    @Test
    void readFromFile_withInvalidOperationCode_throwsException() {
        String filePath = getResourcePath("invalidOperationCode.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
    }

    @Test
    void readFromFile_withEmptyFruit_throwsException() {
        String filePath = getResourcePath("emptyFruit.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
    }

    @Test
    void readFromFile_withNegativeQuantity_throwsException() {
        String filePath = getResourcePath("negativeQuantity.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
    }

    @Test
    void readFromFile_withZeroQuantity_throwsException() throws IOException {
        String filePath = getResourcePath("zeroQuantity.csv");
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(filePath));
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
