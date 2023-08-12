package jackiecrazy.caloricregulation.mixin;

import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodProperties.class)
public class NeverEdibleMixin {
    /**
     * @author Jackiecrazy
     * @reason eh, whatever
     */
    @Inject(method = "canAlwaysEat",
            at = @At(value = "TAIL", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true)
    public void canAlwaysEat(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        cir.cancel();
    }
}
