package com.cherret.mixin.client;

import static com.cherret.Time.*;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Shadow @Final private ClientWorld.Properties clientWorldProperties;
    @Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
    private void onTickTime(CallbackInfo ci) {
        if (!getIsTimeSync())
            ci.cancel();
    }
    @Inject(method = "setTime", at = @At("HEAD"), cancellable = true)
    private void onSetTime(long time, long timeOfDay, boolean shouldTickTimeOfDay, CallbackInfo ci) {
        if (!getIsTimeSync()) {
            ci.cancel();
            this.clientWorldProperties.setTime(getTime());
            this.clientWorldProperties.setTimeOfDay(getTime());
        }
    }
}
