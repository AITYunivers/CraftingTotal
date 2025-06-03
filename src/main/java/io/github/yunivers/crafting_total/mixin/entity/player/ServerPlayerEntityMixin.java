package io.github.yunivers.crafting_total.mixin.entity.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity
{
    public ServerPlayerEntityMixin(World world)
    {
        super(world);
    }

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    public void craftingtotal$tick_updateCrafting(CallbackInfo ci)
    {
        if (currentScreenHandler instanceof CraftingScreenHandler csh)
            csh.onSlotUpdate(null);
        else if (currentScreenHandler instanceof PlayerScreenHandler psh)
            psh.onSlotUpdate(null);
    }
}
