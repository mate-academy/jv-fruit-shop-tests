package core.basesyntax.services.readfromfile;

import core.basesyntax.model.TransactionDto;
import java.util.List;

public interface ReadingFromFile {
    List<TransactionDto> readingFromFile(String filePath);
}
