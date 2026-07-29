package com.cherret.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cherret.Weather.*;

@Mixin(ClientLevel.class)
public class ClientLevelWeatherMixin {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void onGetPrecipitationAt(BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (!isWeatherSync()) {
            if (isSnow()) {
                cir.setReturnValue(Biome.Precipitation.SNOW);
            } else if (isRain()) {
                cir.setReturnValue(Biome.Precipitation.RAIN);
            }
        }
    }
}