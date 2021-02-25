package com.github.lochnessdragon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.lochnessdragon.callbacks.ChatMessageCallback;
import com.github.lochnessdragon.chat.ChatFormatter;
import com.github.lochnessdragon.config.ChatConfig;
import com.github.lochnessdragon.config.ConfigLoader;
import com.github.lochnessdragon.mixin.TranslatableTextAccessor;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;

@Environment(EnvType.SERVER)
public class GraphiXMod implements DedicatedServerModInitializer {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private ChatFormatter formatter;
	private ConfigLoader configLoader;
	
	@Override
	public void onInitializeServer() {
		LOGGER.info("");
		LOGGER.info("  \\ /   |  GraphiX Mod: Version 0.0.1");
		LOGGER.info("   X    |   - Adds custom color to your chats");
		LOGGER.info("  / \\   |   - Support can be found at: https://www.lochnessdragon.github.io/graphixmod");
		LOGGER.info("");
		
		// Config
		configLoader = ConfigLoader.of("graphix").provider(this::configProvider).request();
		
		ChatConfig.CHAT_FMT = configLoader.getOrDefault("graphix.chat.fmt", ChatConfig.CHAT_FMT);
		
		formatter = new ChatFormatter(ChatConfig.CHAT_FMT);
		
		// Register chat stuff
		ChatMessageCallback.EVENT.register((text, type, senderUUID, manager) -> {
			//System.out.println(senderUUID + ": " + text.getString());
			if(type == MessageType.CHAT) { // We only want to scan player messages, not systemwide ones
				if(text instanceof TranslatableText) {
					TranslatableText original = (TranslatableText) text;
					if(original.getKey().equals("chat.type.text")) {
						ServerPlayerEntity player = manager.getPlayer(senderUUID);
						if(player != null) {
							// safe to continue
							((TranslatableTextAccessor) original).setKey(formatter.format(player));
							//LOGGER.info(text.getString());
						}
					}
				}
			}

			return ActionResult.PASS;
		});
	}
	
	private String configProvider(String filename) {
		return "# GraphiX Config\n\n# graphix.chat.fmt : The format string to use for custom chat formatting, uses EssentialsX Chat formatting rules.\ngraphix.chat.fmt={6}{8}{7}: %s\n";
	}

}
