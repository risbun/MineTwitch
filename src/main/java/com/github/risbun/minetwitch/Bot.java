package com.github.risbun.minetwitch;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.risbun.minetwitch.commands.CommandMinetwitch;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;

import static com.github.risbun.minetwitch.MainClass.*;

public class Bot {
    public static void load(TwitchClient twitchClient){
        Bot s = new Bot();
        s.run(twitchClient);
    }

    private void run(TwitchClient twitchClient){
        List<String> channels = p.getConfig().getStringList("bot.channels");
        for (String chl : channels) {
            twitchClient.getChat().joinChannel(chl);
        }

        EventManager eventManager = twitchClient.getEventManager();
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onMessage);
    }

    private final String[] permissions = { "BROADCASTER", "MODERATOR", "VIP", "SUBSCRIBER" };
    private final String[] tags = { ChatColor.RED + "Streamer", ChatColor.GREEN + "Mod", ChatColor.LIGHT_PURPLE + "VIP", ChatColor.DARK_PURPLE + "SUB"};

    private void onMessage(ChannelMessageEvent event) {
        if (!event.getUser().getName().contains(Objects.requireNonNull(p.getConfig().getString("bot.username")))) {
            switch (event.getMessage()) {
                case "1":
                case "2":
                case "3":
                    if (votenow) {
                        if (!globalVotes.contains(event.getUser().getId())) {
                            int vote = Integer.parseInt(event.getMessage()) - 1;

                            int val = Integer.parseInt(votes.get(vote));
                            val++;
                            votes.set(vote, String.valueOf(val));

                            if (!p.getConfig().getBoolean("ingame.hide")) {
                                CommandMinetwitch.update(vote, val);
                            }

                            globalVotes.add(event.getUser().getId());
                        }
                    }
                    break;
                default:
                    if (p.getConfig().getBoolean("ingame.chat")) {
                        String tag = "";
                        for (int i = 0; i < 4; i++) {
                            if (tag.equals("")) {
                                if (event.getPermissions().toString().contains(permissions[i])) {
                                    tag = tags[i] + " ";
                                }
                            }
                        }
                        announceAll(tag + event.getUser().getName() + ChatColor.WHITE + ": " + event.getMessage());
                    }
                    break;
            }
        }
    }

    public static void send(String text) {
        List<String> channels = p.getConfig().getStringList("bot.channels");
        for (String chl : channels) {
            twitchClient.getChat().sendMessage(chl, text);
        }
    }
}
