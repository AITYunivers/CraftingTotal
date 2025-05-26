package io.github.yunivers.crafting_total.mixin.client.gui.screen.ingame;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen
{
    @Shadow public ScreenHandler container;

    @Inject(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/Screen;tick()V",
            shift = At.Shift.AFTER
        )
    )
    public void craftingtotal$tick_updateCrafting(CallbackInfo ci)
    {
        if (container instanceof CraftingScreenHandler csh)
            csh.onSlotUpdate(null);
        else if (container instanceof PlayerScreenHandler psh)
            psh.onSlotUpdate(null);
    }
}
