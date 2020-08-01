package se.laxmine.minetwitch;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class InfoSocket extends WebSocketServer {
    private static final int TCP_PORT = 6969;

    public static Set<WebSocket> conns;

    public InfoSocket() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    public static void sendAll(String text){
        for(WebSocket c : conns){
            c.send(text);
        }
    }

    @Override
    public void onStart() {
        System.out.println("Infosocket Running");
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
        System.out.println(conn.getRemoteSocketAddress()+": "+ message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            conns.remove(conn);
            System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        }
    }
}