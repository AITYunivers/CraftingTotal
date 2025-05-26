package io.github.yunivers.crafting_total.mixin.screen.slot;

import io.github.yunivers.crafting_total.CraftingTotal;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin extends Slot
{
    @Shadow @Final private Inventory input;

    private CraftingResultSlotMixin(Inventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
    }

    @Unique private int total = 1;
    @Inject(
        method = "onTakeItem",
        at = @At("HEAD")
    )
    public void craftingtotal$onTakeItem_calcTotal(ItemStack stack, CallbackInfo ci)
    {
        total = -1;
        for (int i = 0; i < input.size(); i++)
        {
            ItemStack slot = input.getStack(i);
            if (slot == null || slot.count == 0)
                continue;

            if (total == -1)
                total = slot.count;

            if (total > slot.count)
                total = slot.count;
        }

        CraftingTotal.GetBaseCount = true;
        ItemStack output = CraftingRecipeManager.getInstance().craft((CraftingInventory)input);
        total = Math.min(total, 64 / output.count);
        CraftingTotal.GetBaseCount = false;
    }

    @ModifyConstant(
        method = "onTakeItem",
        constant = @Constant(
            intValue = 1,
            ordinal = 8
        )
    )
    public int craftingtotal$onTakeItem_removeTotal(int constant)
    {
        return total;
    }
}
