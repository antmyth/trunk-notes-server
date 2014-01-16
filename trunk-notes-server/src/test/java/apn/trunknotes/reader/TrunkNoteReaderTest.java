package apn.trunknotes.reader;

import apn.trunknotes.types.Title;
import apn.trunknotes.types.TrunkNote;
import org.junit.Test;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static basecamp.datafixtures.PrimitiveDataFixtures.someString;
import static java.nio.file.Files.newBufferedWriter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrunkNoteReaderTest {

    @Test
    public void readsTheFileIntoATrunkNote() throws Exception {
        Path tempFile = Files.createTempFile(someString(), "markdown");
        Title title = new Title(someString());

        assertThat(true, is(tempFile.toFile().exists()));

        try (BufferedWriter writer =
                     newBufferedWriter(tempFile, StandardCharsets.UTF_8,
                             StandardOpenOption.WRITE)) {
            writer.write(String.format("Title: %s\n", title.asString()));
        }


        TrunkNoteReader reader = new TrunkNoteReader();
        TrunkNote expected = new TrunkNote(title);

        assertThat(expected, is(reader.load(tempFile.toFile())));
    }
}
