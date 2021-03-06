package io.gitlab.gustisyahputera.apkeukel.entitymodel;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @Mock
    private FamilyMember familyMember = new FamilyMember();

    private Account testAccount;


    //region Setups
    //==========================================================================

    @Before
    public void setUp() {
        when(familyMember.getId()).thenReturn(1);

        /* Create test instance. Note that this process depend on
         * the result of whenCreateWithParameterConstructor() */
        String accountName = "General account";
        testAccount = new Account(accountName, familyMember);
    }
    //endregion


    //region General tests
    //==========================================================================

    @Test
    public void whenCreateAndSetProperties() {

        /* Given */
        String accountName = "General account";

        /* When */
        testAccount = new Account();
        testAccount.setAccountName(accountName);
        testAccount.setOwnerId(familyMember.getId());

        /* Then */
        Assert.assertEquals(accountName, testAccount.getAccountName());
        Assert.assertEquals(familyMember.getId(), testAccount.getOwnerId());
    }

    @Test
    public void whenCreateWithParameterConstructor() {

        /* Given */
        String accountName = "General account";

        /* When */
        Account newAccount1 = new Account(accountName, familyMember);
        Account newAccount2 = new Account(accountName, familyMember.getId());
        Account[] newAccounts = {newAccount1, newAccount2};

        /* Then */
        for (Account newAccount: newAccounts) {
            Assert.assertEquals(accountName, newAccount.getAccountName());
            Assert.assertEquals(familyMember.getId(), newAccount.getOwnerId());
        }
    }

    @Test
    public void whenSetOwner() {

        /* When */
        testAccount.setOwner(familyMember);

        /* Then */
        Assert.assertEquals(familyMember.getId(), testAccount.getOwnerId());
    }
    //endregion


    //region Comparation tests
    //==========================================================================
    @Test
    public void givenSelf_whenIsEquals_thenTrue() {
        Assert.assertTrue(testAccount.equals(testAccount));  // see [ASSERTEQUALS]
    }

    @Test
    public void givenSameAccountButDifferentObjects_whenIsEquals_thenTrue() {
        String accountName = "Test Account";
        FamilyMember owner = familyMember;

        /* Given */
        Account account1 = new Account(accountName, owner);
        account1.setId(1);
        Account account2 = new Account(accountName, owner);
        account2.setId(1);

        /* Then */
        Assert.assertTrue(account1.equals(account2));  // see [ASSERTEQUALS]
        Assert.assertTrue(account2.equals(account1));  // see [ASSERTEQUALS]
    }

    @Test
    public void givenNull_whenIsEquals_thenFalse() {
        Assert.assertFalse(testAccount.equals(null));  // see [ASSERTEQUALS]
    }

    @Test
    public void givenStrangeObject_whenIsEquals_thenFalse() {
        Assert.assertFalse(testAccount.equals(""));  // see [ASSERTEQUALS]
    }

    @Test
    public void givenOutdatedAccount_whenIsEquals_thenFalse() {

        /* Given an instance with same ID but has different data */
        Account account1 = new Account("Test Account", 1);
        account1.setId(1);
        Account account2 = new Account("Test Account", 2);
        account2.setId(1);

        /* Then */
        Assert.assertFalse(account1.equals(account2));  // see [ASSERTEQUALS]
        Assert.assertFalse(account2.equals(account1));  // see [ASSERTEQUALS]
    }

    /* Notes
     *
     * [ASSERTEQUALS] Manual invocation of the equals() methods
     * is used as the assertEquals() and assertNotEquals() from
     * Assert do not invoke the equals() which is the object of
     * test.
     */
    //endregion

}
