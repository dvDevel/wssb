package lab.john.wssb.operation;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lab.john.wssb.communication.WebSocketHandler;
import lab.john.wssb.data.GlobalDataQueue;

@Component
public class distribute {

    @Scheduled(fixedRate = 500)
    public void scheduleTask() throws Exception {
        // System.out.println("Schedule");
        // MyWebSocketHandler.broadcast("ws message");
        if(GlobalDataQueue.getInstance().size() > 0){

            try {
                WebSocketHandler.broadcast();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


    @Scheduled(fixedRate = 1000)
    public void scheduleEnqueue() throws Exception {
        LocalTime lt = LocalTime.now();
        GlobalDataQueue.getInstance().enqueue(lt.toString());

    }
}
