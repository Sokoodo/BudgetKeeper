import it.unicam.cs.pa.jbudget105371.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class LedgerTest {

    @Test
    public void testLedger() {
        List<IMovement> listMov = new ArrayList<>();
        HashSet<ITag> tags1 = new HashSet<>();
        HashSet<ITag> tags2 = new HashSet<>();
        tags1.add(new Tag("tag1"));
        tags2.add(new Tag("tag2"));
        tags2.add(new Tag("tag3"));
        Date date = new Date(System.currentTimeMillis() - 1);
        IAccount acc = new Account("Francesco", AccountType.ASSETS, "Ciao", 1200);
        IMovement mov = new Movement(acc.getId(), MovementType.CREDITS, date, "movimento", 200, tags1);
        listMov.add(mov);
        ITransaction transaction = new Transaction(date, tags2, listMov);
        ILedger ledger = new Ledger();
        ledger.addTransaction(date, tags2, listMov);
        ledger.addAccount(AccountType.ASSETS, "Francesco", "Ciao", 1200);

        assertEquals(ledger.getTransactions().get(0).getDate(), transaction.getDate());
        assertNotEquals(ledger.getTransactions().get(0).getId(), transaction.getId());
        assertEquals(ledger.getTransactions().get(0).getTags().size(), transaction.getTags().size());
        assertEquals(ledger.getAccounts().get(0).getName(), acc.getName());
    }

    @Test
    public void testLedgerTags(){
        HashSet<ITag> tags2 = new HashSet<>();
        tags2.add(new Tag("tag1"));
        tags2.add(new Tag("tag2"));
        ILedger ledger = new Ledger();
        ledger.addTag(new Tag("tag1"));
        ledger.addTag(new Tag("tag2"));
        assertEquals(tags2.size(), ledger.getTags().size());
        assertFalse(ledger.getTags().isEmpty());
    }

}
