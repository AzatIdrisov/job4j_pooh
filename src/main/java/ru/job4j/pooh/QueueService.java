package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("POST")) {
            queue.putIfAbsent(req.theme(), new ConcurrentLinkedQueue<>());
            queue.get(req.theme()).add(req.text());
            return new Resp(req.text(), 200);
        }
        if (req.method().equals("GET")) {
            ConcurrentLinkedQueue<String> themeQueue = queue.get(req.theme());
            if (themeQueue == null) {
                return new Resp("Theme not found", 404);
            }
            return new Resp(themeQueue.poll(), 200);
        }
        return new Resp("Wrong request", 400);
    }
}
