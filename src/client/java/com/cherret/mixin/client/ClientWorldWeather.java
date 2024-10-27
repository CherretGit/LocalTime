package com.cherret.mixin.client;

import static com.cherret.Time.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientWorld.class)
public abstract class ClientWorldWeather extends World{
    protected ClientWorldWeather(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Override
    public float getRainGradient(float delta) {
        if (getIsClear()) {
            return 0;
        } else {
            return super.getRainGradient(1);
        }
    }

    @Override
    public float getThunderGradient(float delta) {
        if (getIsClear()) {
            return 0;
        } else {
            return super.getThunderGradient(delta);
        }
    }
}
