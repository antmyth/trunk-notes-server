package apn.trunknotes.reader;

import apn.trunknotes.types.TrunkNote;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrunkNotesLoaderTest {

    @Test
    public void readsAllTheNoteFilesOnThePathAndReturnsAListOfTrunkNotes() throws Exception {
        TrunkNoteReader trunkNoteReader = mock(TrunkNoteReader.class);
        Path notesDir = mock(Path.class);
        File file1 = mock(File.class);
        File file2 = mock(File.class);
        TrunkNote expectedNote1 = mock(TrunkNote.class);
        TrunkNote expectedNote2 = mock(TrunkNote.class);
        when(trunkNoteReader.load(file1)).thenReturn(expectedNote1);
        when(trunkNoteReader.load(file2)).thenReturn(expectedNote2);

        TrunkNotesLoader loader = new TrunkNotesLoader(trunkNoteReader);

        List<TrunkNote> notes = loader.load(notesDir);
        List<TrunkNote> expectedNotes = newArrayList(expectedNote1, expectedNote2);

        assertThat(notes,is(expectedNotes));
    }
}
