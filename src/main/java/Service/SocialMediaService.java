package Service;

import DAO.AccountDAO;
import Model.Account;

public class SocialMediaService {
    private AccountDAO accountDAO;

    public SocialMediaService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerUser(String username, String password) {

        // Checks if username is blank
        if (username == null || username.isBlank()) {
            return null;
        }
        // Check if the username already exists
        if (accountDAO.getAccountByUsername(username) != null) {
            return null; // Username already exists, registration failed
        }

        // Checks if password is less than 4 characters
        if (password == null || password.length() < 4) {
            return null;
        }

        // Create a new Account object
        Account account = new Account(username, password);

        // Save the account in the database
        int accountId = accountDAO.createAccount(account);
        account.setAccount_id(accountId);

        return account;
    }

    public boolean loginUser(String username, String password) {
        // Retrieve the account from the database based on the username
        Account account = accountDAO.getAccountByUsername(username);
        // Check if the account exists and the password matches
        if (account != null && account.getPassword().equals(password)) {
            return true; // Login successful
        }
    
        return false; // Login failed
    }
    
    
}