package apn.trunknotes.reader;

import apn.trunknotes.types.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TrunkNoteReader {
    public TrunkNote load(File noteFile) throws Exception {
        Path path = noteFile.toPath();
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        Title title = new Title(allLines.get(0).substring(7));
        Timestamp timestamp = new Timestamp(allLines.get(1).substring(11));
        CreatedTimestamp createdTimestamp = new CreatedTimestamp(allLines.get(2).substring(9));
        LastAccessedTimestamp lastAccessedTimestamp = new LastAccessedTimestamp(allLines.get(3).substring(15));
        return new TrunkNote(title, timestamp, createdTimestamp, lastAccessedTimestamp);
    }
}
