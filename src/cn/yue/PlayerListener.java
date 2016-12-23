package cn.yue;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener
	  {
	    @EventHandler(priority=EventPriority.HIGHEST)
	    public void onPlayerJoin(PlayerJoinEvent e)
	    {
	      if (tpLogin.on == true){
	        e.getPlayer().teleport(tpLogin.tpl);}
	      else{
	        Bukkit.getLogger().info("插件未成功启动，请检查config.yml");
	    }}
	  }

