package com.github.lochnessdragon.config;

import java.util.SortedMap;
import java.util.UUID;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.server.network.ServerPlayerEntity;

@Environment(EnvType.SERVER)
public class LuckPermsAPI {
	public static LuckPerms API = LuckPermsProvider.get();
	
	public static User getUserByUUID(UUID uuid) {
		return API.getUserManager().getUser(uuid);
	}
	
	public static User getUserByPlayer(ServerPlayerEntity player) {
		return API.getPlayerAdapter(ServerPlayerEntity.class).getUser(player);
	}

	public static String getPrefix(User user) {
		SortedMap<Integer, String> prefixes = user.getCachedData().getMetaData().getPrefixes();
		String fullPrefix = "";
		
		for(String prefix : prefixes.values()) {
			fullPrefix += prefix + " ";
		}
		
		fullPrefix = fullPrefix.replace("&", "\u00A7");
		if(fullPrefix.length() > 0)
			fullPrefix = fullPrefix.substring(0, fullPrefix.length() - 1);
		
		return fullPrefix;
	}

	public static String getSuffix(User user) {
		SortedMap<Integer, String> suffixes = user.getCachedData().getMetaData().getSuffixes();
		String fullSuffix = "";
		
		for(String suffix : suffixes.values()) {
			fullSuffix += suffix + " ";
		}
		
		fullSuffix = fullSuffix.replace("&", "\u00A7");
		if(fullSuffix.length() > 0)
			fullSuffix = fullSuffix.substring(0, fullSuffix.length() - 1);
		
		return fullSuffix;
	}

	public static String getGroup(User user) {
		return user.getPrimaryGroup();
	}
}
