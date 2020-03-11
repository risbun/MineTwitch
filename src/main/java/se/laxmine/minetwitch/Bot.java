package se.laxmine.minetwitch;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;

import static se.laxmine.minetwitch.Main.*;

class Bot {
    static void load(TwitchClient twitchClient){
        Bot s = new Bot();
        s.run(twitchClient);
    }

    private void run(TwitchClient twitchClient){
        String channel = config.getString("channel");
        if (channel != null) {
            twitchClient.getChat().joinChannel(channel);
        }

        EventManager eventManager = twitchClient.getEventManager();
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onMessage);
    }
    private String[] permissions = { "BROADCASTER", "MODERATOR", "VIP", "SUBSCRIBER" };
    private String[] tags = {ChatColor.RED+"Streamer", ChatColor.GREEN+"Mod", ChatColor.LIGHT_PURPLE+"VIP", ChatColor.DARK_PURPLE+"SUB"};

    //SUBSCRIBER, BROADCASTER, EVERYONE, MODERATOR
    private void onMessage(ChannelMessageEvent event) {
        if(!event.getUser().getName().contains(Objects.requireNonNull(config.getString("username")))){
            if(event.getMessage().equals("1") || event.getMessage().equals("2") || event.getMessage().equals("3")){
                if(votenow){
                    if(!globalVotes.contains(event.getUser().getId())) {
                        int vote = Integer.parseInt(event.getMessage()) - 1;

                        int val = Integer.parseInt(votes.get(vote));
                        val++;
                        votes.set(vote, String.valueOf(val));

                        CommandMinetwitch.update(vote, val);

                        globalVotes.add(event.getUser().getId());
                    }
                }
            }else{
                String tag = "";
                for(int i = 0; i<4;i++){
                    if(tag.equals("")){
                        if(event.getPermissions().toString().contains(permissions[i])){
                            tag = tags[i]+" ";
                        }
                    }
                }
                Bukkit.broadcastMessage(tag+event.getUser().getName()+ ChatColor.WHITE+": "+event.getMessage());
            }
        }
    }

    static void send(String text) {
        twitchClient.getChat().sendMessage(Objects.requireNonNull(config.getString("channel")), text);
    }
}
