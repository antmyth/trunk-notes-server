package apn.trunknotes.reader;

import apn.trunknotes.types.Timestamp;
import apn.trunknotes.types.Title;
import apn.trunknotes.types.TrunkNote;

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
        return new TrunkNote(title, timestamp);
    }
}
