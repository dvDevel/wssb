package lab.john.wssb.data;


import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;


@Component
public class GlobalDataQueue {
    // Change the queue to store JSON strings
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();


    //## constructor
    private static final GlobalDataQueue instance  = new GlobalDataQueue();

    public GlobalDataQueue() {}

    public static GlobalDataQueue getInstance() {
        return instance;
    }

    // Accept a JSON string
    public void enqueue(String dv) {
        queue.offer(dv);


        System.out.println("[ANDT]EnQueue size(enqueue): " + queue.size() + "/10(" + LocalDateTime.now() +")\n" + dv);

        if(queue.size() > 10) {
            System.out.println("Queue size exceeded 10, removing oldest element.");
            try {
                queue.take(); // Remove the oldest element if size exceeds 10
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error while removing oldest element from queue: " + e.getMessage());
            }
        }

        // Send to all WebSocket clients
        // MyWebSocketHandler.broadcast(payload);
    }

    // Return a JSON string
    public String dequeue() throws InterruptedException {
        String j = queue.take();
        System.out.println("DeQueue size(dequeue): " + queue.size() + "/10(" + LocalDateTime.now() +")\n");

        return j;
    }

    public int size() throws Exception {
        return queue.size();
    }

}
