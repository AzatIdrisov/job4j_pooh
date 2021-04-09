package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("POST")) {
            queue.putIfAbsent(req.theme(), new ConcurrentHashMap<>());
            ConcurrentHashMap clientsMaps = queue.get(req.theme());
            clientsMaps.putIfAbsent(req.getId(), new ConcurrentLinkedQueue<>());
            queue.get(req.theme()).values().forEach(element -> element.add(req.text()));
            return new Resp(req.text(), 200);
        }
        if (req.method().equals("GET")) {
            ConcurrentHashMap<Integer, ConcurrentLinkedQueue<String>> themeQueue = queue.get(req.theme());
            if (themeQueue == null) {
                return null;
            }
            ConcurrentLinkedQueue<String> clientQueue = themeQueue.get(req.getId());
            if (clientQueue == null) {
                return null;
            }
            return new Resp(clientQueue.poll(), 200);
        }
        return null;
    }
}
