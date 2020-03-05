package java.se.laxmine.challonge;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.commands.CommandExampleBase;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import org.bukkit.ChatColor;

public class Bot extends CommandExampleBase {
    private final static String patternA = "1";
    private final static String patternB = "2";
    private final static String patternC = "3";

    private final Twirk twirk;

    public Bot(Twirk twirk) {
        super(CommandType.PREFIX_COMMAND);
        this.twirk = twirk;
    }

    @Override
    protected String getCommandWords() {
        return patternA + "|" + patternB + "|" + patternC;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.DEFAULT;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
        if (Main.votenow && Main.globalVotes.contains(sender.getUserName()) == false) {
            Main.globalVotes.add(sender.getUserName());
            if (command.equals(patternA)) {
                Main.oneVotes.add(sender.getUserName());
                updateStats(1);
            } else if (command.equals(patternB)) {
                Main.twoVotes.add(sender.getUserName());
                updateStats(2);
            } else if (command.equals(patternC)) {
                Main.threeVotes.add(sender.getUserName());
                updateStats(3);
            }
        }
    }

    private void updateStats(Integer i) {
        if (i == 1) {
            Main.one.setPrefix(ChatColor.WHITE + " 1. " + Main.chosen.get(0) + " - " + Main.oneVotes.size());
        } else if (i == 2) {
            Main.two.setPrefix(ChatColor.WHITE + " 2. " + Main.chosen.get(1) + " - " + Main.twoVotes.size());
        } else if (i == 3) {
            Main.three.setPrefix(ChatColor.WHITE + " 3. " + Main.chosen.get(2) + " - " + Main.threeVotes.size());
        }
    }
}
