package io.github.yunivers.crafting_total.mixin.stationapi.impl.recipe;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.yunivers.crafting_total.CraftingTotal;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.modificationstation.stationapi.impl.recipe.StationShapelessRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StationShapelessRecipe.class)
public abstract class StationShapelessRecipeMixin implements CraftingRecipe
{
    @Shadow @Final public ItemStack output;

    @WrapOperation(
        method = "craft",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"
        )
    )
    public ItemStack craftingtotal$craft_calcTotal(ItemStack instance, Operation<ItemStack> original, @Local(argsOnly = true) CraftingInventory craftingInventory)
    {
        ItemStack stack = original.call(instance);
        if (CraftingTotal.GetBaseCount)
            return stack;

        int count = -1;
        for (int i = 0; i < craftingInventory.size(); i++)
        {
            ItemStack slot = craftingInventory.getStack(i);
            if (slot == null || slot.count == 0)
                continue;

            if (count == -1)
                count = slot.count;

            if (count > slot.count)
                count = slot.count;
        }
        stack.count = Math.min(count, 64 / output.count) * output.count;
        return stack;
    }
}
