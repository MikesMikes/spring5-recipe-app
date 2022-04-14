package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    final Long ID = 1L;
    final String DESCRIPTION = "NOTES TEST";

    NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void nullObj() {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    void emptyObj() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    void convert() {
        //where
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(DESCRIPTION);

        //when
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);

        //then
        assertNotNull(notesCommand);
        assertEquals(ID, notesCommand.getId());
        assertEquals(DESCRIPTION, notesCommand.getRecipeNotes());
    }
}