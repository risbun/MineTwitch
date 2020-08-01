package se.laxmine.minetwitch;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static se.laxmine.minetwitch.Main.*;

public class VotingSocket extends WebSocketServer {
    private static final int TCP_PORT = 69;

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

    public VotingSocket() {
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
        System.out.println("Votingsocket running");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        InfoSocket.sendAll("Connection from " + conn.getRemoteSocketAddress());
        conns.add(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        InfoSocket.sendAll("Connection lost " + conn.getRemoteSocketAddress());
        conns.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if(users.contains(conn.getRemoteSocketAddress())){
            conn.send("lock()");
        }else {
            switch (message) {
                case "0":
                case "1":
                case "2":
                    conn.send("lock()");
                    int msg = Integer.parseInt(message);
                    votes.set(msg, votes.get(msg)+1);
                    users.add(conn.getRemoteSocketAddress());
                    break;
            }
        }
        InfoSocket.sendAll("Message from " + conn.getRemoteSocketAddress() + " text: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            conns.remove(conn);
            System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
            InfoSocket.sendAll("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        }
    }
}