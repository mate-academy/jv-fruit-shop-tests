package core.basesyntax.service;

import java.io.IOException;

public interface FileReaderService {
    String read(String fileName) throws IOException;
}
