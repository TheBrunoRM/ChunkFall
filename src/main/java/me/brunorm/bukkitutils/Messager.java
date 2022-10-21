package me.brunorm.bukkitutils;

import org.bukkit.ChatColor;

public class Messager {

	public static final char ALT_COLOR_CHAR = '&';

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes(ALT_COLOR_CHAR, text);
	}

	public static String colorFormat(String text, Object... format) {
		return Messager.color(String.format(text, format));
	}

	public static String formatMessage(String msg, Object... format) {
		for (int i = 0; i < format.length; i++) {
			msg = msg.replaceAll(String.format("\\{%s\\}", i), String.valueOf(format[i]));
		}
		return Messager.color(msg);
	}

}
