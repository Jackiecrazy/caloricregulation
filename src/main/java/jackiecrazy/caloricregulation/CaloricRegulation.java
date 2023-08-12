package jackiecrazy.caloricregulation;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CaloricRegulation.MODID)
public class CaloricRegulation {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "caloricregulation";
    // Create a Deferred Register to hold Blocks which will all be registered under the "caloricregulation" namespace

    public CaloricRegulation() {
        IEventBus modEventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::mudamudamuda);
    }

    private void mudamudamuda(LivingEntityUseItemEvent e) {//applies to start, tick, stop, and finish
        ItemStack active = e.getItem();
        LivingEntity uke = e.getEntity();
        boolean cancel = false;
        if ((active.getItem().getUseAnimation(active) == UseAnim.EAT || active.getItem().getUseAnimation(active) == UseAnim.DRINK)) {
            //is food
            if (uke instanceof Player p) {
                int missing = 20 - p.getFoodData().getFoodLevel();
                if (active.getItem().isEdible()) {
                    FoodProperties fp = active.getItem().getFoodProperties(active, uke);// there are serious problems if this is null, but check anyway
                    if (fp != null && fp.getNutrition() > missing) {
                        cancel = true;
                        p.displayClientMessage(Component.translatable("caloricregulation.no"), true);
                    }
                }
            }
        }

        if (cancel) {
            if (e.isCancelable())
                e.setCanceled(true);
            e.setDuration(-1);
        }

    }
}
