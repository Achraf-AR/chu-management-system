package mediator;

import models.ChefService;
import models.Message;
import java.util.List;

public interface ChefServiceMediator {
    void sendMessage(ChefService sender, ChefService receiver, String content);
    List<Message> getMessages(ChefService chef);
    void addChef(ChefService chef);
    void removeChef(ChefService chef);
}