package com.cherret;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;


import static com.cherret.Time.*;
import static com.cherret.Weather.*;

public class TimeScreen extends Screen {
    public Screen parent;
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static ButtonWidget buttonWidgetClear;
    private static ButtonWidget buttonWidgetRain;

    protected TimeScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }
    @Override
    protected void init() {
        int windowWidth = client.getWindow().getScaledWidth();
        int windowHeight = client.getWindow().getScaledHeight();
        int x = windowWidth / 2;
        int y = windowHeight / 2;
        SliderWidget sliderWidgetTime = new SliderWidget(x-20, y-67, 140, 20, Text.translatable("localtime.current_local_time", getTime()), (double) getTime() / 24000) {
            @Override
            protected void updateMessage() {
                setTime((long) (this.value * 24000));
                this.setMessage(Text.translatable("localtime.current_local_time", getTime()));
            }

            @Override
            protected void applyValue() {
                setTime((long) (this.value * 24000));
                this.setMessage(Text.translatable("localtime.current_local_time", getTime()));
            }
        };
        CheckboxWidget checkboxWidgetTime = CheckboxWidget.builder(Text.translatable("localtime.server_time"), textRenderer)
                .option(SimpleOption.ofBoolean(this.toString(), getIsTimeSync()))
                .callback((checkbox, checked) -> setIsTimeSync(checked))
                .pos(x-140, y-65)
                .build();
        CheckboxWidget checkboxWidgetWeather = CheckboxWidget.builder(Text.translatable("localtime.sync_weather"), textRenderer)
                .option(SimpleOption.ofBoolean(this.toString(), isWeatherSync()))
                .callback((checkbox, checked) -> setWeatherSync(checked))
                .pos(x-140, y-25)
                .build();
        buttonWidgetClear = ButtonWidget.builder(Text.translatable("localtime.clear_weather"), (btn) -> {
            setClear(true);
            btn.active = false;
            if (isRain()) {
                buttonWidgetRain.active = true;
                setRain(false);
            }
        }).dimensions(x, y-27, 50, 20).build();
        buttonWidgetRain = ButtonWidget.builder(Text.translatable("localtime.rain_weather"), (btn) -> {
            setRain(true);
            btn.active = false;
            if (isClear()) {
                buttonWidgetClear.active = true;
                setClear(false);
            }
        }).dimensions(x+50, y-27, 50, 20).build();
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.translatable("localtime.done"), (btn) -> {
            close();
        }).dimensions(x-60, y+50, 120, 20).build();
        if (isClear()) {
            buttonWidgetClear.active = false;
        }
        else if (isRain()) {
            buttonWidgetRain.active = false;
        }
        this.addDrawableChild(sliderWidgetTime);
        this.addDrawableChild(checkboxWidgetTime);
        this.addDrawableChild(checkboxWidgetWeather);
        this.addDrawableChild(buttonWidgetClear);
        this.addDrawableChild(buttonWidgetRain);
        this.addDrawableChild(buttonWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int windowWidth = client.getWindow().getScaledWidth();
        int windowHeight = client.getWindow().getScaledHeight();
        int x = (windowWidth - 300) / 2;
        int y = (windowHeight - 150) / 2;
        context.fill(x, y, x+300, y+150, 0x80000000);
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, Text.translatable("localtime.local_time_editor"), x, y - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, false);
    }
    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
