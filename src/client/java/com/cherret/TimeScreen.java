package com.cherret;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import static com.cherret.Time.*;
import static com.cherret.Weather.*;

public class TimeScreen extends Screen {
    public Screen parent;
    private static final Minecraft client = Minecraft.getInstance();
    private Button buttonWidgetClear;
    private Button buttonWidgetRain;

    protected TimeScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        int windowWidth = this.width;
        int windowHeight = this.height;
        int x = windowWidth / 2;
        int y = windowHeight / 2;
        AbstractSliderButton sliderWidgetTime = new AbstractSliderButton(x-20, y-67, 140, 20, Component.translatable("localtime.current_local_time", getTime()), (double) getTime() / 24000) {
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
                .onValueChange((button, value) -> {
                    setIsTimeSync(value);
                })
                .pos(x-140, y-65)
                .build();
        Checkbox checkboxWidgetWeather = Checkbox.builder(Component.translatable("localtime.sync_weather"), font)
                .selected(isWeatherSync())
                .onValueChange((button, value) -> {
                    setWeatherSync(value);
                })
                .pos(x-140, y-25)
                .build();
        buttonWidgetClear = Button.builder(Component.translatable("localtime.clear_weather"), (btn) -> {
            setClear(true);
            btn.active = false;
            if (isRain()) {
                buttonWidgetRain.active = true;
                setRain(false);
            }
        }).bounds(x, y-27, 50, 20).build();
        buttonWidgetRain = Button.builder(Component.translatable("localtime.rain_weather"), (btn) -> {
            setRain(true);
            btn.active = false;
            if (isClear()) {
                buttonWidgetClear.active = true;
                setClear(false);
            }
        }).bounds(x+50, y-27, 50, 20).build();
        Checkbox checkboxWidgetSnow = Checkbox.builder(Component.translatable("localtime.snow"), font)
                .selected(isSnow())
                .onValueChange((button, value) -> {
                    setSnow(value);
                })
                .pos(x-140, y+10)
                .build();
        Button buttonWidget = Button.builder(Component.translatable("localtime.done"), (btn) -> {
            onClose();
        }).bounds(x-60, y+50, 120, 20).build();
        if (isClear()) {
            buttonWidgetClear.active = false;
        }
        else if (isRain()) {
            buttonWidgetRain.active = false;
        }
        this.addRenderableWidget(sliderWidgetTime);
        this.addRenderableWidget(checkboxWidgetTime);
        this.addRenderableWidget(checkboxWidgetWeather);
        this.addRenderableWidget(buttonWidgetClear);
        this.addRenderableWidget(buttonWidgetRain);
        this.addRenderableWidget(checkboxWidgetSnow);
        this.addRenderableWidget(buttonWidget);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        int windowWidth = this.width;
        int windowHeight = this.height;
        int x = (windowWidth - 300) / 2;
        int y = (windowHeight - 150) / 2;
        graphics.fill(x, y, x+300, y+150, 0x80000000);
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.text(this.font, Component.translatable("localtime.local_time_editor"), x, y - this.font.lineHeight - 10, 0xFFFFFFFF, false);
    }

    @Override
    public void onClose() {
        client.gui.setScreen(this.parent);
    }
}
