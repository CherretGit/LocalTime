package com.cherret.mixin.client;

import com.cherret.WeatherType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;

import static com.cherret.Weather.*;

@Mixin(ClientLevel.class)
public abstract class ClientWorldWeather extends Level {
    protected ClientWorldWeather(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<net.minecraft.world.level.dimension.DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Override
    public boolean isRaining() {
        if (!isWeatherSync()) {
            return hasPrecipitation();
        }
        return super.isRaining();
    }

    @Override
    public float getRainLevel(float delta) {
        if (!isWeatherSync()) {
            return (hasPrecipitation()) ? 1.0f : 0.0f;
        }
        return super.getRainLevel(delta);
    }

    private boolean hasPrecipitation() {
        return getWeatherType() == WeatherType.RAIN || getWeatherType() == WeatherType.SNOW;
    }
}