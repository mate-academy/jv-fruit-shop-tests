package core.basesyntax.service;

import core.basesyntax.exceptions.PathDoesNotExistException;
import java.util.List;

public interface CsvFileReaderService {
    List<String[]> readFromFile(String path) throws PathDoesNotExistException;
}
