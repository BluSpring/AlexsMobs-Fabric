package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.entity.EntityVoidWorm;
import com.github.alexthe666.alexsmobs.misc.AMAdvancementTriggerRegistry;
import com.github.alexthe666.citadel.forge.extensions.IClientItemExtensions;
import com.github.alexthe666.citadel.forge.extensions.ItemRenderExtension;
import io.github.fabricators_of_create.porting_lib.item.EntityTickListenerItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;
import java.util.UUID;

public class ItemMysteriousWorm extends Item implements ItemRenderExtension, EntityTickListenerItem {
    public ItemMysteriousWorm(Properties props) {
        super(props);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) AlexsMobs.PROXY.getISTERProperties());
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(AMConfig.voidWormSummonable){
            String dim = entity.level.dimension().location().toString();
            if(AMConfig.voidWormSpawnDimensions.contains(dim) && entity.getY() < -60 && !entity.isRemoved()){
                entity.kill();
                EntityVoidWorm worm = AMEntityRegistry.VOID_WORM.get().create(entity.level);
                worm.setPos(entity.getX(), 0, entity.getZ());
                worm.setSegmentCount(25 + new Random().nextInt(15));
                worm.setXRot(-90.0F);
                worm.updatePostSummon = true;

                if(!entity.level.isClientSide){
                    if(entity.getThrower() != null){
                        UUID uuid = entity.getThrower();
                        if(entity.level.getPlayerByUUID(uuid) instanceof ServerPlayer){
                            AMAdvancementTriggerRegistry.VOID_WORM_SUMMON.trigger((ServerPlayer)entity.level.getPlayerByUUID(uuid));
                        }
                    }
                    entity.level.addFreshEntity(worm);
                }
            }
        }
        return false;
    }
}
