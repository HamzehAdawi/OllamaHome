package com.HamzehAdawi.OllamaApp;

import com.HamzehAdawi.OllamaApp.entities.Messages;
import com.HamzehAdawi.OllamaApp.services.MessagesServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessagesServiceImplTest {

    @Test
    void addUserAndOllamaMessages_areStoredInOrder() {
        Messages messages = new Messages();
        MessagesServiceImpl service = new MessagesServiceImpl(messages);

        service.addUserMessage("hello");
        service.addOllamaResponse("hi there");

        List<String> conv = service.getConversation();

        assertEquals(2, conv.size());
        assertEquals("Me: hello\n", conv.get(0));
        assertEquals("Ollama: hi there\n", conv.get(1));
    }
}
