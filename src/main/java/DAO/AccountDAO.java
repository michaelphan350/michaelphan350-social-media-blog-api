package DAO;

import Model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
    private Map<String, Account> accounts;
    private int nextAccountId;

    public AccountDAO() {
        this.accounts = new HashMap<>();
        this.nextAccountId = 1;
    }

    public int createAccount(Account account) {
        // Set the account ID for the account object
        int accountId = generateAccountId();
        account.setAccount_id(accountId);

        // Add the account to the accounts map
        accounts.put(account.getUsername(), account);

        return accountId;
    }

    public Account getAccountByUsername(String username) {
        return accounts.get(username);
    }

    private int generateAccountId() {
        int accountId = nextAccountId;
        nextAccountId++;
        return accountId;
    }
}