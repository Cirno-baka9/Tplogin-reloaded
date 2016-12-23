package cn.yue;
	import org.bukkit.Bukkit;
	import org.bukkit.ChatColor;
	import org.bukkit.Location;
	import org.bukkit.command.Command;
	import org.bukkit.command.CommandSender;
	import org.bukkit.entity.Player;
	import org.bukkit.plugin.java.JavaPlugin;
   
	public class tpLogin extends JavaPlugin
	{
	  public static JavaPlugin plugin;
	  public static Location tpl = new Location(null, 0.0D, 0.0D, 0.0D);
	  public static boolean on;

	  public void onEnable()
	  {
	    plugin = this;
	    lg("["+getDescription().getVersion()+"]"+"tpLogin加载中");
	    saveDefaultConfig();
	    reloadcnf();
	    lg("tpLogin读取配置中");
	    try {
	      tpl.setWorld(Bukkit.getWorld(plugin.getConfig().getString("location.world")));
	      tpl.setX(plugin.getConfig().getDouble("location.X"));
	      tpl.setY(plugin.getConfig().getDouble("location.Y"));
	      tpl.setZ(plugin.getConfig().getDouble("location.Z"));
	      tpl.setYaw((float)plugin.getConfig().getLong("location.Yaw"));
	      tpl.setPitch((float)plugin.getConfig().getLong("location.Pitch"));
	      on = true;
	    } catch (Exception ex) {
	      on = false;
	      for (int index = 0; index < 10; index++)
	        Bukkit.getLogger().warning(ChatColor.AQUA + "配置文件读取失败，请尝试删除后重新配置，指令/tplogin set");
	      try
	      {
	        Thread.sleep(5000L);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	    lg(ChatColor.RED + "注册监听器中");
	    Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	    lg("tplogin插件加载完毕");
	  }
	  public void lg(String s) {
	    getLogger().info(s);
	  }

	  public void onDisable() {
	    Bukkit.getLogger().info(getDescription().getName() + " v" + getDescription().getVersion() + "已卸载");
	  }
	  public void saveLocation(Location loc) {
	    plugin.getConfig().set("location.world", loc.getWorld().getName());
	    plugin.getConfig().set("location.X", Double.valueOf(loc.getX()));
	    plugin.getConfig().set("location.Y", Double.valueOf(loc.getY()));
	    plugin.getConfig().set("location.Z", Double.valueOf(loc.getZ()));
	    plugin.getConfig().set("location.Pitch", Float.valueOf(loc.getPitch()));
	    plugin.getConfig().set("location.Yaw", Float.valueOf(loc.getYaw()));
	    plugin.saveConfig();
	    reloadcnf();
	    Bukkit.getLogger().info("配置文件已生成");
	  }
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("tplogin")) {
	      if ((sender.isOp()) || (sender.hasPermission("tplogin.admin"))) {
	        if (args.length == 0) {
	          sender.sendMessage("");
	          sender.sendMessage(ChatColor.AQUA + "" + ChatColor.GREEN + ">>>tpLogin重置版<<<");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin " + ChatColor.GREEN + "- 查看插件帮助");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin set " + ChatColor.GREEN + "- 设置登入传送点");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin go " + ChatColor.GREEN+ "- 前往传送点");
	          sender.sendMessage(ChatColor.AQUA+"/tplogin now"+ChatColor.GREEN+"- 查看当前传送点坐标");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin reload " + ChatColor.GREEN + "- 重载插件");
	          return true;
	        }if (args.length == 1) {
	          if (args[0].equalsIgnoreCase("set")) {
	            saveLocation(((Player)sender).getLocation());
	            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.GREEN + "传送点设置成功");
	            return true;
	          }
	          if (args[0].equalsIgnoreCase("now")) {
		            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.GREEN + "当前传送点位置为：");
		            sender.sendMessage(ChatColor.YELLOW+"-X:"+ChatColor.GREEN+(Math.round(plugin.getConfig().getDouble("location.X")))+ChatColor.YELLOW+"  -Y:"+ChatColor.GREEN+(Math.round(plugin.getConfig().getDouble("location.Y")))+ChatColor.YELLOW+"  -Z:"+ChatColor.GREEN+(Math.round(plugin.getConfig().getDouble("location.Z")))+ChatColor.YELLOW+"  -世界："+ChatColor.GREEN+plugin.getConfig().getString("location.world"));
		          
		            return true;
		          }
	          if (args[0].equalsIgnoreCase("go")) {
	            if ((sender instanceof Player)) {
	              if (on == true) {
	                Player player = (Player)sender;
	                player.teleport(tpl);
	              }
	              else {
	                sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED +"插件发生错误，请检查config.yml");
	              }
	              return true;
	            }
	            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED + "后台禁用此指令");
	            return true;
	          }
	          if (args[0].equalsIgnoreCase("reload")) {
	            reloadcnf();
	            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.GREEN + "插件重载完毕");
	            return true;
	          }
	        }
	      }
	      else {
	        sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED + "权限不足");
	        return true;
	      }
	      sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED + "未知指令，请输入/tplogin查看帮助");
	      return true;
	    }
	    return false;
	  }
	  public void reloadcnf() {
	    reloadConfig();
	    try {
	      tpl.setWorld(Bukkit.getWorld(plugin.getConfig().getString("location.world")));
	      tpl.setX(plugin.getConfig().getDouble("location.X"));
	      tpl.setY(plugin.getConfig().getDouble("location.Y"));
	      tpl.setZ(plugin.getConfig().getDouble("location.Z"));
	      tpl.setYaw((float)plugin.getConfig().getLong("location.Yaw"));
	      tpl.setPitch((float)plugin.getConfig().getLong("location.Pitch"));
	      on = true;
	    } catch (Exception ex) {
	      on = false;
	      for (int index = 0; index < 10; index++)
	        Bukkit.getLogger().warning(ChatColor.AQUA + "config.yml读取失败，请输入/tplogin set设置传送点");
	    }
	  }
	}

