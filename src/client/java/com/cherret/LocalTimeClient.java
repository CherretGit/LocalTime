package com.cherret;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class LocalTimeClient implements ClientModInitializer {
  private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
		  Identifier.fromNamespaceAndPath("localtime", "keybindings")
  );
	@Override
	public void onInitializeClient() {
		KeyMapping keyMapping = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"localtime.open.gui",
				InputConstants.Type.KEYSYM,
				InputConstants.KEY_R,
				CATEGORY
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyMapping.consumeClick()) {
				Screen currentScreen = Minecraft.getInstance().gui.screen();
				Minecraft.getInstance().gui.setScreen(new TimeScreen(Component.translatable("LocalTimeEditor"), currentScreen));
			}
		});
	}
}