package com.github.lochnessdragon.chat;

import com.github.lochnessdragon.config.LuckPermsAPI;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.luckperms.api.model.user.User;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;

@Environment(EnvType.SERVER)
public class ChatFormatter {
	
	public static ChatFormatter INSTANCE;
	
	private String format;
	
	public ChatFormatter(String fmt) {
		this.format = fmt;
		INSTANCE = this;
	}
	
	public String format(PlayerEntity player) {
		Team team = (Team) player.getScoreboardTeam();
		
		User user = LuckPermsAPI.getUserByPlayer((ServerPlayerEntity) player);
		
		String prefix = LuckPermsAPI.getPrefix(user);
		String suffix = LuckPermsAPI.getSuffix(user);
		String group = LuckPermsAPI.getGroup(user);
		
		// What follows is essetialsX like chat formatting, {1} and {2} are unimplemented because I didn't want to do them
		String returnValue = format;
		returnValue = returnValue.replace("{0}", group);
		//returnValue = returnValue.replace("{1}", player.getEntityWorld().get); 
		//returnValue = returnValue.replace("{2}", world.substring(0, 1).toUpperCase(Locale.ENGLISH));
		returnValue = returnValue.replace("{3}", team == null ? "" : team.getPrefix().asString());
		returnValue = returnValue.replace("{4}", team == null ? "" : team.getSuffix().asString());
		returnValue = returnValue.replace("{5}", team == null ? "" : team.getDisplayName().asString());
		returnValue = returnValue.replace("{6}", prefix);
		returnValue = returnValue.replace("{7}", suffix);
		returnValue = returnValue.replace("{8}", "%s"); // chat formatting already passes the player name in, no need to replace it.
		
		return returnValue;
	}

}
