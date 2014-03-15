package apn.trunknotes.reader;

import apn.trunknotes.types.TrunkNote;

import java.nio.file.Path;
import java.util.List;

public class TrunkNotesLoader {
    private final TrunkNoteReader trunkNoteReader;

    public TrunkNotesLoader(TrunkNoteReader trunkNoteReader) {
        this.trunkNoteReader = trunkNoteReader;
    }

    public List<TrunkNote> load(Path notesDir) {
        return null;
    }
}
