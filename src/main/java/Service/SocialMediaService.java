package Service;

import DAO.AccountDAO;
import Model.Account;

public class SocialMediaService {

    private final AccountDAO accountDAO;

    public SocialMediaService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) throws AccountRegistrationException {
        validateAccountRegistration(account);
        return accountDAO.createAccount(account);
    }

    private void validateAccountRegistration(Account account) throws AccountRegistrationException {
        if (account.getUsername() == null || account.getUsername().isEmpty()
                || account.getPassword() == null || account.getPassword().length() < 4) {
            throw new AccountRegistrationException("");
        }
        if (accountDAO.doesUsernameExist(account.getUsername())) {
            throw new AccountRegistrationException("");
        }
    }
}