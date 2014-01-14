package apn.trunknotes.reader;

import apn.trunknotes.types.Title;
import apn.trunknotes.types.TrunkNote;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static basecamp.datafixtures.PrimitiveDataFixtures.someString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrunkNoteReaderTest {

    @Test
    public void readsTheFileIntoATrunkNote() throws Exception {
        Path tempFile = Files.createTempFile(someString(), "markdown");
        Title title = new Title(someString());

        File noteFile = tempFile.toFile();
        assertThat(true,is(noteFile.exists()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(noteFile));
        writer.write(String.format("Title: %s\n",title.asString()));
        writer.close();

        TrunkNoteReader reader = new TrunkNoteReader();
        TrunkNote expected = new TrunkNote(title);

        assertThat(expected, is(reader.load(noteFile)));
    }
}
