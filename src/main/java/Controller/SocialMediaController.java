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
import Service.MessageService;
import Model.Message;
import DAO.MessageDAO;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private final SocialMediaService socialMediaService;
    private final MessageService messageService;

    public SocialMediaController() {
        AccountDAO accountDAO = new AccountDAO();
        this.socialMediaService = new SocialMediaService(accountDAO);

        MessageDAO messageDAO = new MessageDAO();
        this.messageService = new MessageService(messageDAO);
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
        Account account = ctx.bodyAsClass(Account.class);

        Account createdAccount = socialMediaService.registerUser(account.getUsername(), account.getPassword());
    
        if (createdAccount != null) {
            createdAccount.setAccount_id(createdAccount.getAccount_id() + 1); // Set the account_id
            ctx.json(createdAccount).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) {

        try {
            Account account = ctx.bodyAsClass(Account.class);

            boolean loggedIn = socialMediaService.loginUser(account.getUsername(), account.getPassword());

            if (loggedIn) {
                ctx.status(200);
            } else {
                ctx.status(401);
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void postMessagesHandler(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);

        int accountId = message.getPosted_by();
        String messageText = message.getMessage_text();
        Long timePostedEpoch = message.getTime_posted_epoch();

        Message createdMessage = messageService.createMessage(accountId, messageText, timePostedEpoch);

        if (createdMessage != null) {
            ctx.json(createdMessage).status(200);
        } else {
            ctx.status(400);
        }
    }
    private void getAllMessagesHandler (Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);
    }
    private void getMessageByIdHandler (Context ctx) {
        int messageId = ctx.pathParamAsClass("message_id", Integer.class).get();

        Optional<Message> message = messageService.getMessageById(messageId);

        if(message.isPresent()) {
            ctx.json(message.get()).status(200);
        } else { 
            ctx.status(200);
        }

    }
    private void deleteMessagesByIdHandler (Context ctx) {
        int messageId = ctx.pathParamAsClass("message_id", Integer.class).get();

        boolean deleted = messageService.deleteMessageById(messageId);

        if(deleted) {
            ctx.status(200);
        } else { 
            ctx.status(404);
        }


    }
    private void updateMessagesByIdHandler (Context ctx) {

        int messageId = ctx.pathParamAsClass("message_id", Integer.class).get();
        String messageText = ctx.body();

        Optional<Message> updatedMessage = messageService.updateMessageById(messageId, messageText);

        if(updatedMessage.isPresent()) {
            ctx.json(updatedMessage.get()).status(200);
        } else { 
            ctx.status(404);
        }
    }
    private void getMessagesByAccountIdHandler (Context ctx) {
        int accountId = ctx.pathParamAsClass("account_id", Integer.class).get();

        List<Message> messages = messageService.getMessagesByAccountId(accountId);

        if (!messages.isEmpty()) {
            ctx.json(messages).status(200);
        } else {
            ctx.status(404);
        }
    }

}