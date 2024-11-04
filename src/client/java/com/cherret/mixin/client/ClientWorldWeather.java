package com.cherret.mixin.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;

import static com.cherret.Weather.*;

@Mixin(ClientWorld.class)
public abstract class ClientWorldWeather extends World {
    protected ClientWorldWeather(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Override
    public boolean isRaining() {
        if (!isWeatherSync()) {
            if (isRain()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getRainGradient(float delta) {
        if (!isWeatherSync()) {
            if (isClear()) {
                return 0;
            } else if (isRain()) {
                return 1.0f;
            }
        }
        return super.getRainGradient(1);
    }
}
