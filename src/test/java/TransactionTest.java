import it.unicam.cs.pa.jbudget105371.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TransactionTest {

    @Test
    public void testTransaction() {
        List<IMovement> listMov = new ArrayList<>();
        HashSet<ITag> tags1 = new HashSet<>();
        HashSet<ITag> tags2 = new HashSet<>();
        tags1.add(new Tag("tag1"));
        tags2.add(new Tag("tag2"));
        tags2.add(new Tag("tag3"));
        Date date = new Date(System.currentTimeMillis());
        Account acc = new Account("Francesco", AccountType.ASSETS, "Ciao", 1200);
        IMovement mov = new Movement(acc.getId(), MovementType.CREDITS, date, "movimento", 200, tags1);
        listMov.add(mov);
        Transaction tran = new Transaction(date, tags2, listMov);
        assertEquals(mov.getId(), tran.getMovements().get(0).getId());
    }

}
