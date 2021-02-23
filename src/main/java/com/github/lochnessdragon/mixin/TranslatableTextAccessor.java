package com.github.lochnessdragon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.SERVER)
@Mixin(TranslatableText.class)
public interface TranslatableTextAccessor {
	@Accessor("key")
	public void setKey(String key);
}
