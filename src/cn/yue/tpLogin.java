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
	    lg("["+getDescription().getVersion()+"]"+"tpLogin������");
	    saveDefaultConfig();
	    reloadcnf();
	    lg("tpLogin��ȡ������");
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
	        Bukkit.getLogger().warning(ChatColor.AQUA + "�����ļ���ȡʧ�ܣ��볢��ɾ�����������ã�ָ��/tplogin set");
	      try
	      {
	        Thread.sleep(5000L);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	    lg(ChatColor.RED + "ע���������");
	    Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	    lg("tplogin����������");
	  }
	  public void lg(String s) {
	    getLogger().info(s);
	  }

	  public void onDisable() {
	    Bukkit.getLogger().info(getDescription().getName() + " v" + getDescription().getVersion() + "��ж��");
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
	    Bukkit.getLogger().info("�����ļ�������");
	  }
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("tplogin")) {
	      if ((sender.isOp()) || (sender.hasPermission("tplogin.admin"))) {
	        if (args.length == 0) {
	          sender.sendMessage("");
	          sender.sendMessage(ChatColor.AQUA + "" + ChatColor.GREEN + ">>>tpLogin���ð�<<<");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin " + ChatColor.GREEN + "- �鿴�������");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin set " + ChatColor.GREEN + "- ���õ��봫�͵�");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin go " + ChatColor.GREEN+ "- ǰ�����͵�");
	          sender.sendMessage(ChatColor.AQUA+"/tplogin now"+ChatColor.GREEN+"- �鿴��ǰ���͵�����");
	          sender.sendMessage(ChatColor.AQUA + "/tplogin reload " + ChatColor.GREEN + "- ���ز��");
	          return true;
	        }if (args.length == 1) {
	          if (args[0].equalsIgnoreCase("set")) {
	            saveLocation(((Player)sender).getLocation());
	            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.GREEN + "���͵����óɹ�");
	            return true;
	          }
	          if (args[0].equalsIgnoreCase("now")) {
		            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.GREEN + "��ǰ���͵�λ��Ϊ��");
		            sender.sendMessage(ChatColor.YELLOW+"-X:"+ChatColor.GREEN+(Math.round(plugin.getConfig().getDouble("location.X")))+ChatColor.YELLOW+"  -Y:"+ChatColor.GREEN+(Math.round(plugin.getConfig().getDouble("location.Y")))+ChatColor.YELLOW+"  -Z:"+ChatColor.GREEN+(Math.round(plugin.getConfig().getDouble("location.Z")))+ChatColor.YELLOW+"  -���磺"+ChatColor.GREEN+plugin.getConfig().getString("location.world"));
		          
		            return true;
		          }
	          if (args[0].equalsIgnoreCase("go")) {
	            if ((sender instanceof Player)) {
	              if (on == true) {
	                Player player = (Player)sender;
	                player.teleport(tpl);
	              }
	              else {
	                sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED +"���������������config.yml");
	              }
	              return true;
	            }
	            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED + "��̨���ô�ָ��");
	            return true;
	          }
	          if (args[0].equalsIgnoreCase("reload")) {
	            reloadcnf();
	            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.GREEN + "����������");
	            return true;
	          }
	        }
	      }
	      else {
	        sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED + "Ȩ�޲���");
	        return true;
	      }
	      sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "tpLogin" + ChatColor.AQUA + "] " + ChatColor.RED + "δָ֪�������/tplogin�鿴����");
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
	        Bukkit.getLogger().warning(ChatColor.AQUA + "config.yml��ȡʧ�ܣ�������/tplogin set���ô��͵�");
	    }
	  }
	}

