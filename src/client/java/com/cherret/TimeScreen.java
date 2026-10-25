package com.cherret;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import static com.cherret.Time.*;
import static com.cherret.Weather.*;
import static com.cherret.WeatherType.*;

public class TimeScreen extends Screen {
    public Screen parent;
    private static final Minecraft client = Minecraft.getInstance();
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 150;

    protected TimeScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = (this.width - PANEL_WIDTH) / 2;
        int y = (this.height - PANEL_HEIGHT) / 2;
        AbstractSliderButton sliderWidgetTime = new AbstractSliderButton(x + 130, y + 8, 140, 20, Component.translatable("localtime.current_local_time", getTime()), (double) getTime() / 24000) {
            @Override
            protected void updateMessage() {
                setTime((long) (this.value * 24000));
                this.setMessage(Component.translatable("localtime.current_local_time", getTime()));
            }

            @Override
            protected void applyValue() {
                setTime((long) (this.value * 24000));
                this.setMessage(Component.translatable("localtime.current_local_time", getTime()));
            }
        };
        Checkbox checkboxWidgetTime = Checkbox.builder(Component.translatable("localtime.server_time"), font)
                .selected(getIsTimeSync())
                .onValueChange((button, value) -> setIsTimeSync(value))
                .pos(x + 10, y + 10)
                .build();
        Checkbox checkboxWidgetWeather = Checkbox.builder(Component.translatable("localtime.sync_weather"), font)
                .selected(isWeatherSync())
                .onValueChange((button, value) -> setWeatherSync(value))
                .pos(x + 10, y + 50)
                .build();
        CycleButton<WeatherType> cycleButton = CycleButton
                .builder(
                        value -> Component.translatable(switch (value) {
                            case CLEAR -> "localtime.clear_weather";
                            case RAIN -> "localtime.rain_weather";
                            case SNOW -> "localtime.snow";
                        }),
                        getWeatherType()
                )
                .withValues(CLEAR, RAIN, SNOW)
                .create(x + 130, y + 48, 140, 20, Component.translatable("localtime.weather"),
                        (button, value) -> setWeatherType(value));
        Button buttonWidget = Button.builder(Component.translatable("localtime.done"), (btn) -> onClose()).bounds(x + 90, y + 125, 120, 20).build();
        this.addRenderableWidget(sliderWidgetTime);
        this.addRenderableWidget(checkboxWidgetTime);
        this.addRenderableWidget(checkboxWidgetWeather);
        this.addRenderableWidget(cycleButton);
        this.addRenderableWidget(buttonWidget);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        int x = (this.width - PANEL_WIDTH) / 2;
        int y = (this.height - PANEL_HEIGHT) / 2;
        graphics.fill(x, y, x + PANEL_WIDTH, y + PANEL_HEIGHT, 0x80000000);
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.text(this.font, Component.translatable("localtime.local_time_editor"), x, y - this.font.lineHeight - 10, 0xFFFFFFFF, false);
    }

    @Override
    public void onClose() {
        client.gui.setScreen(this.parent);
    }
}