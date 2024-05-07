package core.basesyntax.testclasses;

import core.basesyntax.servise.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileReaderServiceForTest implements ReaderService {
    @Override
    public List<String> readFromFile(String pathInnFile) {
        try (Stream<String> streamFromFile = Files.lines(Paths.get(pathInnFile))) {
            return streamFromFile.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
