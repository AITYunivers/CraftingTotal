package io.github.yunivers.crafting_total.mixin.recipe;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.yunivers.crafting_total.CraftingTotal;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeMixin implements CraftingRecipe
{
    @Shadow private ItemStack output;

    @WrapOperation(
        method = "craft",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/item/ItemStack;count:I"
        )
    )
    public int craftingtotal$craft_calcTotal(ItemStack instance, Operation<Integer> original, @Local(argsOnly = true) CraftingInventory craftingInventory)
    {
        if (CraftingTotal.GetBaseCount)
            return original.call(instance);

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
        count = Math.min(count, 64 / output.count) * output.count;
        return count;
    }
}
