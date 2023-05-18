package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final List<Account> accounts;

    public AccountDAO() {
        this.accounts = new ArrayList<>();
    }

    public Account createAccount(Account account) {
        int accountId = accounts.size() + 1;
        account.setAccount_id(accountId);
        accounts.add(account);
        return account;
    }

    public boolean doesUsernameExist(String username) {
        return accounts.stream().anyMatch(acc -> acc.getUsername().equals(username));
    }
}
