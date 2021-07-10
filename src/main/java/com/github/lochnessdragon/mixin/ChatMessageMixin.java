package com.github.lochnessdragon.mixin;

import java.util.UUID;
import java.util.function.Function;

import net.minecraft.server.network.ServerPlayerEntity;
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
    @Inject(at = @At(value = "HEAD"), method = "broadcast", cancellable = false)
    private void onBroadcastChat(Text serverMessage, Function<ServerPlayerEntity, Text> playerMessageFactory, MessageType playerMessageType, UUID sender, CallbackInfo ci) {
        ActionResult result = ChatMessageCallback.EVENT.invoker().onBroadcastChat(serverMessage, playerMessageType, sender, (PlayerManager) (Object) this);
    }
}
