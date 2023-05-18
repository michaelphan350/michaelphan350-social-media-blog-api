package Service;
import DAO.MessageDAO;
import java.util.Optional;
import java.util.List;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage (int accountId, String messageText, long timePostedEpoch) {
        //Validation Check:
        if (messageText.isBlank() || messageText.length() > 254) {
            return null;
        }
        Message message = new Message(accountId, messageText, timePostedEpoch);
        return messageDAO.createMessage(message);
    }


    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Optional<Message> deleteMessageById(int messageId) {
        Optional<Message> message = messageDAO.getMessageById(messageId);
    
        if (message.isPresent()) {
            boolean deleted = messageDAO.deleteMessageById(messageId);
            if (deleted) {
                return message;
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<Message> updateMessageById(int messageId, String messageText) {
        return messageDAO.updateMessageById(messageId, messageText);
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }

    public void closeDAOConnection() {
        messageDAO.closeConnection();
    }
}
