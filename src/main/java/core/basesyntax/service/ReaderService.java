package core.basesyntax.service;

import java.io.File;
import java.util.List;

public interface ReaderService {
    public List<String> read(File inputFileName);
}
