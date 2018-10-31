package l2s.gameserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

import l2s.gameserver.utils.Memory;
import l2s.gameserver.utils.Util;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.World;

/**
 * Server status
 * @author Nefer
 * @version 1.0
 */
public class ServerStatus
{
	protected static final Logger LOGGER = Logger.getLogger("Loader");
	protected ScheduledFuture<?> _scheduledTask;
	
	protected ServerStatus()
	{
		_scheduledTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new ServerStatusTask(), 1800000, 3600000);
	}
	
	protected class ServerStatusTask implements Runnable
	{
		protected final SimpleDateFormat fmt = new SimpleDateFormat("H:mm.");
		
		@Override
		public void run()
		{
			int ActivePlayers = 0;
			int OfflinePlayers = 0;
			
			for (final Player player : World.getInstance().getAllPlayers())
			{
				if (player.isInOfflineMode())
					OfflinePlayers++;
				else
					ActivePlayers++;
			}
			
			Util.printSection("Server Status");
			LOGGER.info("Server Time: " + fmt.format(new Date(System.currentTimeMillis())));
			LOGGER.info("Active Players Online: " + ActivePlayers);
			LOGGER.info("Offline Players Online: " + OfflinePlayers);
			LOGGER.info("Threads: " + Thread.activeCount());
			LOGGER.info("Free Memory: " + Memory.getFreeMemory() + " MB");
			LOGGER.info("Used memory: " + Memory.getUsedMemory() + " MB");
			Util.printSection("Server Status");
		}
	}
	
	public static ServerStatus getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final ServerStatus _instance = new ServerStatus();
	}
}