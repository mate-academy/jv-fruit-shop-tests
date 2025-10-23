package core.basesyntax.service;

import core.basesyntax.model.FruitDto;
import java.util.List;

public interface FilerReader {
    List<FruitDto> readData(String fileName);
}
