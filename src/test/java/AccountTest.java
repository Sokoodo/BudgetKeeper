import it.unicam.cs.pa.jbudget105371.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;

public class AccountTest {

    @Test
    public void testEqualsAccount(){
        Account acc1 = new Account("Francesco", AccountType.ASSETS, "Ciao", 1000.0);
        Account acc2 = new Account("Michele", AccountType.LIABILITIES, "Ciao", 1000.0);
        assertEquals(acc1.getBalance(), acc2.getBalance());
        assertNotEquals(acc1.getName(), acc2.getName());
        assertNotEquals(acc1.getType(), acc2.getType());
        assertEquals(acc1.getDescription(), acc2.getDescription());
        assertNotEquals(acc1.getId(), acc2.getId());
    }

    @Test
    public void testAccountAddCreditsDebits() {
        Account acc1 = new Account("Francesco", AccountType.ASSETS, "Ciao", 1000.0);
        Account acc2 = new Account("Michele", AccountType.LIABILITIES, "Ciao2", 1000.0);
        acc1.addCredit(2000.0);
        acc1.addDebit(-1000.0);
        assertEquals(2000.0, acc1.getBalance());
        acc2.addDebit(-2000.0);
        assertEquals(3000.0, acc2.getBalance());
    }

    @Test
    public void testAddMovement() {
        HashSet<ITag> tags1 = new HashSet<>();
        tags1.add(new Tag("tag1"));
        Date date = new Date(System.currentTimeMillis());
        Account acc = new Account("Francesco", AccountType.ASSETS, "Ciao", 1200);
        IMovement mov = new Movement(acc.getId(), MovementType.CREDITS, date, "movimento", 200, tags1);
        acc.addMovement(mov);
        assertEquals(mov, acc.getMovements().get(0));
        acc.removeMovement(mov);
        assertFalse(acc.getMovements().contains(mov));
    }
}
