package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.List;
import java.util.Optional;
import Service.SocialMediaService;
import Model.Account;
import Service.AccountRegistrationException;
import DAO.AccountDAO;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    public SocialMediaController() {
        AccountDAO accountDAO = new AccountDAO();
        this.socialMediaService = new SocialMediaService(accountDAO);
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) {
        try {
            Account account = ctx.bodyAsClass(Account.class);
            Account createdAccount = socialMediaService.registerAccount(account);
            createdAccount.setAccount_id(createdAccount.getAccount_id() + 1); // Set the account_id
            ctx.json(createdAccount).status(200);
        } catch (AccountRegistrationException e) {
            ctx.status(400);
        }
    }
    private void loginHandler (Context context) {

    }
    private void postMessagesHandler(Context context) {

    }
    private void getAllMessagesHandler (Context context) {

    }
    private void getMessageByIdHandler (Context context) {

    }
    private void deleteMessagesByIdHandler (Context context) {

    }
    private void updateMessagesByIdHandler (Context context) {

    }
    private void getMessagesByAccountIdHandler (Context context) {

    }

}