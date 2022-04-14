package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    final Long ID = 1L;
    final String DESCRIPTION = "DESCRIPTION TEST";

    CategoryToCategoryCommand categoryToCategoryCommand;

    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void nullObj() {
        assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    void emptyObj() {
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    void convert() {
        //where
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        assertNotNull(categoryToCategoryCommand.convert(category));
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
        
    }
}