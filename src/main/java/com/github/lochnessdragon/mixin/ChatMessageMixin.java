package com.github.lochnessdragon.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.lochnessdragon.callbacks.ChatMessageCallback;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.MessageType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

@Environment(EnvType.SERVER)
@Mixin(PlayerManager.class)
public class ChatMessageMixin {
    @Inject(at = @At(value = "INVOKE"), method = "broadcastChatMessage", cancellable = true)
    private void onBroadcastChat(Text message, MessageType type, UUID senderUuid, CallbackInfo info) {
        ActionResult result = ChatMessageCallback.EVENT.invoker().onBroadcastChat(message, type, senderUuid, (PlayerManager) (Object) this);
 
        if(result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
