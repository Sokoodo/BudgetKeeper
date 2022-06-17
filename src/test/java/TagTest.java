import it.unicam.cs.pa.jbudget105371.model.ITag;
import it.unicam.cs.pa.jbudget105371.model.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {

    @Test
    public void testTagsEquals() {
        ITag tag1 = new Tag("Tag12");
        ITag tag2 = new Tag("Tag12");
        assertEquals(tag1, tag2);
        assertNotEquals(tag1.getId(), tag2.getId());
    }

}
