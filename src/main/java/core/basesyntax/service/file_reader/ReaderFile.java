package core.basesyntax.service.file_reader;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ReaderFile {
    List<FruitTransaction> readFile(String inputFileName);
}
