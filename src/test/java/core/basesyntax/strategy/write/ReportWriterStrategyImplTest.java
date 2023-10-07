package core.basesyntax.strategy.write;

import static core.basesyntax.model.WriterType.CONSOLE;
import static core.basesyntax.model.WriterType.FILE;
import static core.basesyntax.model.WriterType.UNKNOWN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.ReportWriterServiceConsole;
import core.basesyntax.service.impl.ReportWriterServiceFile;
import org.junit.jupiter.api.Test;

class ReportWriterStrategyImplTest {
    private final ReportWriterStrategy reportWriterStrategy = new ReportWriterStrategyImpl();

    @Test
    void get_isOk() {
        final ReportWriterService consoleWriter = reportWriterStrategy.get(CONSOLE);
        final ReportWriterService fileWriter = reportWriterStrategy.get(FILE);
        assertTrue(consoleWriter instanceof ReportWriterServiceConsole,
                "consoleWriter must be instance of "
                        + ReportWriterServiceConsole.class.getSimpleName());
        assertTrue(fileWriter instanceof ReportWriterServiceFile,
                "fileWriter must be instance of "
                        + ReportWriterServiceFile.class.getSimpleName());

    }

    @Test
    void get_wrongWriterType_notOk() {
        ReportWriterStrategy reportWriterStrategy = new ReportWriterStrategyImpl();
        assertThrows(RuntimeException.class, () -> reportWriterStrategy.get(UNKNOWN),
                "Must throws RuntimeException.");
        assertThrows(RuntimeException.class, () -> reportWriterStrategy.get(null),
                "Must throws RuntimeException.");
    }
}
