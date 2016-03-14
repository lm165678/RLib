package rlib.logging;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rlib.util.array.Array;
import rlib.util.array.ArrayFactory;

/**
 * Менеджер для создания игровых логгеров.
 *
 * @author Ronn
 */
public abstract class GameLoggers {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    /**
     * директория лога
     */
    private static String directory;
    /**
     * списоксозданных логеров
     */
    private static Array<GameLogger> loggers;

    /**
     * Завершение работы всех логгеров.
     */
    public static final void finish() {
        for (final GameLogger logger : loggers) {
            logger.finish();
        }
    }

    /**
     * Создает индивидуальный логгер с указаным именем.
     *
     * @param name имя объекта который запрашивает логгер
     * @return новый индивидуальный логгер
     */
    public static final ByteGameLogger getByteLogger(final String name) {

        final File dir = new File(directory, name);

        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("incorrect directory for game logger " + name);
        }

        final File outFile = new File(dir.getAbsolutePath() + "/" + TIME_FORMAT.format(new Date()) + ".gamelog");

        try {
            final ByteGameLogger logger = new ByteGameLogger(outFile);
            loggers.add(logger);
            return logger;
        } catch (final IOException e) {
            throw new IllegalArgumentException("incorrect create log file for game logger " + name);
        }
    }

    /**
     * Создает индивидуальный логгер с указаным именем.
     *
     * @param name имя объекта который запрашивает логгер
     * @return новый индивидуальный логгер
     */
    public static final StringGameLogger getLogger(final String name) {

        final File dir = new File(directory + "/" + name);

        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("incorrect directory for game logger " + name);
        }

        final File outFile = new File(dir.getAbsolutePath() + "/" + TIME_FORMAT.format(new Date()) + ".gamelog");

        try {
            final StringGameLogger logger = new StringGameLogger(outFile);
            loggers.add(logger);
            return logger;
        } catch (final IOException e) {
            throw new IllegalArgumentException("incorrect create log file for game logger " + name);
        }
    }

    /**
     * @param directory адресс директори лог папки.
     */
    public static final void setDirectory(final String directory) {
        GameLoggers.directory = directory;
        GameLoggers.loggers = ArrayFactory.newConcurrentArray(GameLogger.class);
    }
}
