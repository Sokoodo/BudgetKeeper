import it.unicam.cs.pa.jbudget105371.model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;

class MovemetTest {

    @Test
    public void testMovement() {
        HashSet<ITag> tags1 = new HashSet<>();
        tags1.add(new Tag("tag1"));
        Date date = new Date(System.currentTimeMillis());
        Account acc = new Account("Francesco", AccountType.ASSETS, "Ciao", 1200);
        IMovement mov = new Movement(acc.getId(), MovementType.CREDITS, date, "movimento", 200, tags1);
        assertTrue(mov.getTags().contains(new Tag("tag1")));
        assertNotNull(mov.getId());
        assertEquals(mov.getAmount(), 200);
    }
}
