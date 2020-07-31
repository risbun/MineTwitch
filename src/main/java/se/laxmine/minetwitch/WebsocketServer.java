package se.laxmine.minetwitch;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static se.laxmine.minetwitch.Main.chosen;
import static se.laxmine.minetwitch.Main.votes;

public class WebsocketServer extends WebSocketServer {
    private static final int TCP_PORT = 8080;

    public static Set<WebSocket> conns;
    public static List<InetSocketAddress> users = new ArrayList<>();

    public static void unlock(){
        for(WebSocket c : conns) {
            c.send("unlock()");
        }
    }

    public static void reset(){
        if(users != null)
            users.clear();
    }

    public WebsocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    public static void update() {
        for(WebSocket c : conns) {
            for(int i = 0; i < 3; i++){
                c.send("update("+i+",'"+chosen.get(i)+"')");
            }
        }
    }

    public static void lock() {
        for(WebSocket c : conns) {
            c.send("lock()");
        }
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket Server Running");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        for(WebSocket c : conns) {
            if(users.contains(c.getRemoteSocketAddress())){
                c.send("lock()");
            }else {
                switch (message) {
                    case "0":
                    case "1":
                    case "2":
                        c.send("lock()");
                        votes.set(Integer.parseInt(message), votes.get(Integer.parseInt(message))+1);
                        users.add(c.getRemoteSocketAddress());
                        break;
                }
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            conns.remove(conn);
            System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        }
    }
}