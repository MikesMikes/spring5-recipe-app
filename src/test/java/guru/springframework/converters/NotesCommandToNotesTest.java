package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    final Long ID = 1L;
    final String DESCRIPTION = "NOTES TEST";

    NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    void nullObj() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    void emptyObj() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        //where
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(DESCRIPTION);

        //when
        Notes notes = notesCommandToNotes.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(DESCRIPTION, notes.getRecipeNotes());


    }
}