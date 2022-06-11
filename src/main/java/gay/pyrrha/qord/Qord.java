package gay.pyrrha.qord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import config.ConfigManager;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Qord implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(Qord.class);

	@Override
	public void onInitializeClient(ModContainer mod) {
//		ConfigManager.instance.init();
		try(IPCClient client = new IPCClient(933511436754370620L)) {
			client.setListener(new IPCListener() {
				@Override
				public void onReady(IPCClient client) {
					client.sendRichPresence(
							new RichPresence.Builder()
									.setState("Happy Pride")
									.setButtons(new RichPresence.Button("Test", "https://github.com/JustPyrrha"))
									.build());
				}
			});
			client.connect(DiscordBuild.STABLE);
		} catch (NoDiscordClientException e) {
			LOGGER.warn("Unable to connect to Discord's IPC socket, is Discord running?");
			if(QuiltLoader.isDevelopmentEnvironment()) {
				LOGGER.error("Error connecting to Discord.", e);
			}
		}
	}
}
