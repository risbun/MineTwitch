package com.github.risbun.minetwitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.risbun.minetwitch.MainClass.*;
import static com.github.risbun.minetwitch.commands.CMinetwitch.voteActive;

public class TwitchBot {

    public static TwitchClient client;
    private static final List<String> globalVotes = new ArrayList<>();

    public static void load(){
        TwitchBot s = new TwitchBot();

        String oauthString = Objects.requireNonNull(config.getString("bot.oauth"));

        TwitchBot.client = TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withChatAccount(new OAuth2Credential("twitch", oauthString))
                .withEnableChat(true)
                .build();

        s.run(client);
    }

    private void run(TwitchClient twitchClient){
        List<String> channels = config.getStringList("bot.channels");
        for (String chl : channels) {
            twitchClient.getChat().joinChannel(chl);
        }

        EventManager eventManager = twitchClient.getEventManager();
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onMessage);
    }

    private final String[] permissions = { "BROADCASTER", "MODERATOR", "VIP", "SUBSCRIBER" };
    private final String[] tags = { ChatColor.RED + "Streamer", ChatColor.GREEN + "Mod", ChatColor.LIGHT_PURPLE + "VIP", ChatColor.DARK_PURPLE + "SUB"};

    private void onMessage(ChannelMessageEvent event) {
        if (event.getUser().getName().contains(Objects.requireNonNull(plugin.getConfig().getString("bot.username")))) {
            return;
        }

        switch (event.getMessage()) {
            case "1":
            case "2":
            case "3":
                if (!voteActive) break;
                if (globalVotes.contains(event.getUser().getId())) break;

                int vote = Integer.parseInt(event.getMessage()) - 1;

                votes[vote] += 1;

                if (!config.getBoolean("ingame.hide")) {
                    plugin.cMinetwitch.update(vote, votes[vote]);
                }

                globalVotes.add(event.getUser().getId());

                break;
            default:
                if (config.getBoolean("ingame.chat")) {
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

    public static void send(String text) {
        List<String> channels = config.getStringList("bot.channels");
        for (String chl : channels) {
            client.getChat().sendMessage(chl, text);
        }
    }

    public static void ClearList(){
        globalVotes.clear();
    }
}
