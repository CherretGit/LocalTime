package com.cherret.mixin.client;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cherret.Weather.isSnow;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "getPrecipitation", at = @At("HEAD"), cancellable = true)
    private void getPrecipitationMixin(BlockPos pos, int seaLevel, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (isSnow()) {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        }
    }
}
