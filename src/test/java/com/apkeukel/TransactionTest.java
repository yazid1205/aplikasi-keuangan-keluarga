package com.apkeukel;

import com.dieselpoint.norm.Database;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTest {

    @Mock private Account mockAccount = new Account();

    private static Database database;
    private Transaction testTransaction;


    //region Setups
    //==========================================================================

    @BeforeClass
    public static void databaseSetUp() {
        database = new Database();
        database.setJdbcUrl("jdbc:sqlite:test.db");
        database.sql("DROP TABLE IF EXISTS transaction_").execute();
        database.sql(Transaction.createTable).execute();
    }

    @AfterClass
    public static void databaseTearDown() {
        database.close();
    }

    @Before
    public void whenCreateAndSetProperties() {
        when(mockAccount.getId()).thenReturn(1);

        int accountId = mockAccount.getId();
        double amount = 500.0;
        LocalDate date = LocalDate.now();
        String description = "Initial income";

        /* When */
        testTransaction = new Transaction();
        testTransaction.setAccountId(accountId);
        testTransaction.setAmount(amount);
        testTransaction.setDate(date);
        testTransaction.setDescription(description);

        /* Then */
        Assert.assertEquals(accountId, testTransaction.getAccountId());
        Assert.assertEquals(amount, testTransaction.getAmount(), 0.0);
        Assert.assertEquals(date, testTransaction.getDate());
        Assert.assertEquals(description, testTransaction.getDescription());
    }
    //endregion


    @Test
    public void whenCreateWithParameterConstructor() {
        when(mockAccount.getId()).thenReturn(1);

        /* Given */
        int accountId = mockAccount.getId();
        double amount = 500.0;
        LocalDate date = LocalDate.now();
        String description = "Initial income";

        /* When */
        Transaction testTransaction1 = new Transaction(accountId, amount, date, description);
        Transaction testTransaction2 = new Transaction(mockAccount, amount, date, description);
        Transaction[] testTransactions = {testTransaction1, testTransaction2};

        /* Then */
        for (Transaction testTransaction: testTransactions) {
            Assert.assertEquals(accountId, testTransaction.getAccountId());
            Assert.assertEquals(amount, testTransaction.getAmount(), 0.0);
            Assert.assertEquals(date, testTransaction.getDate());
            Assert.assertEquals(description, testTransaction.getDescription());
        }
    }

    @Test
    public void whenSetAccount() {

        int accountId = mockAccount.getId();

        /* When */
        testTransaction.setAccount(mockAccount);

        /* Then */
        Assert.assertEquals(accountId, testTransaction.getAccountId());
    }
}
