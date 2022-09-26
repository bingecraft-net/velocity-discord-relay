package net.bingecraft.bloodmoon;

import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bloodmoon extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        getCommand("bloodmoon").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        getServer().getWorld("world").setDifficulty(Difficulty.HARD);
        sender.sendMessage("set difficulty to hard");
        return true;
    }

}
