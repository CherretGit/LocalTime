package com.cherret.mixin.client;

import net.minecraft.client.ClientClockManager;
import net.minecraft.core.Holder;
import net.minecraft.world.clock.WorldClock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cherret.Time.getIsTimeSync;
import static com.cherret.Time.getTime;

@Mixin(ClientClockManager.class)
public abstract class ClientClockManagerMixin {
    @Inject(method = "getTotalTicks", at = @At("HEAD"), cancellable = true)
    private void onGetTotalTicks(final Holder<WorldClock> definition, CallbackInfoReturnable<Long> cir) {
        if (!getIsTimeSync()) {
            cir.setReturnValue(getTime());
        }
    }
}