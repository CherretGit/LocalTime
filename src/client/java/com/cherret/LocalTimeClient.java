package com.cherret;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class LocalTimeClient implements ClientModInitializer {
	private static KeyBinding keyBinding;
	@Override
	public void onInitializeClient() {
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"localtime.open.gui",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"localtime.category"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				Screen currentScreen = MinecraftClient.getInstance().currentScreen;
				MinecraftClient.getInstance().setScreen(new TimeScreen(Text.of("LocalTimeEditor"), currentScreen));
			}
		});
	}
}