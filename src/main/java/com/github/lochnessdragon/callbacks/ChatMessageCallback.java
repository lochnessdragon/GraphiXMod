package com.github.lochnessdragon.callbacks;

import java.util.UUID;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

@Environment(EnvType.SERVER)
public interface ChatMessageCallback {
	Event<ChatMessageCallback> EVENT = EventFactory.createArrayBacked(ChatMessageCallback.class, (listeners) -> (text, type, senderUUID, manager) -> {
		for(ChatMessageCallback listener : listeners) {
			ActionResult result = listener.onBroadcastChat(text, type, senderUUID, manager);
			
			if(result != ActionResult.PASS) {
				return result;
			}
		}
		
		return ActionResult.PASS;
	});
	
	ActionResult onBroadcastChat(Text text, MessageType type, UUID senderUUID, PlayerManager manager);
}
