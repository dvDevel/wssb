package lab.john.wssb.communication;


import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lab.john.wssb.data.GlobalDataQueue;


public class WebSocketHandler  extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(@SuppressWarnings("null") WebSocketSession session) throws Exception {
        sessions.add(session);
    }


    @SuppressWarnings("null")
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received: " + message.getPayload());
    }


    @Override
    public void afterConnectionClosed(@SuppressWarnings("null") WebSocketSession session, @SuppressWarnings("null") CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }


    // Broadcast method
    public static void broadcast() throws InterruptedException {
        String v = GlobalDataQueue.getInstance().dequeue();

        if(v == null) return;

        
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(v));
                } catch (Exception e) {
                    // Handle exception
                }
            }
        }
    }

}

