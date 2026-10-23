package com.github.alexthe666.alexsmobs.entity.util;

import com.github.alexthe666.alexsmobs.attachment.AMAttachmentRegistry;
import com.github.alexthe666.alexsmobs.attachment.SquidGrappleData;
import com.github.alexthe666.alexsmobs.entity.EntitySquidGrapple;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SquidGrappleUtil {

    public static int onFireHook(LivingEntity entity, EntitySquidGrapple newHookUUID) {
        SquidGrappleData data = entity.getAttachedOrElse(AMAttachmentRegistry.SQUID_GRAPPLE_DATA, SquidGrappleData.EMPTY);
        int index = getFirstAvailableHookIndex(entity);
        if(data.hookExists(index, entity.level())){
            Entity hookEntity = data.hooks().get(index).getEntity(entity.level());
            if(hookEntity instanceof EntitySquidGrapple hook && !hook.isRemoved()){
                hook.setWithdrawing(true);
            }
        }
        entity.setAttached(AMAttachmentRegistry.SQUID_GRAPPLE_DATA, data.withNewHook(index, newHookUUID));
        return index;
    }

    public static int getFirstAvailableHookIndex(LivingEntity entity){
        SquidGrappleData data = entity.getAttachedOrElse(AMAttachmentRegistry.SQUID_GRAPPLE_DATA, SquidGrappleData.EMPTY);
        int nulls = data.getAnyNullHooks(entity.level());
        if(nulls != -1){
            return nulls;
        }
        int i = data.getHookCount(entity.level());
        if(i < 4){
            return i;
        }else{
            int j = data.lastReplacedHook();
            entity.setAttached(AMAttachmentRegistry.SQUID_GRAPPLE_DATA, data.withLastReplacedHook((j + 1) % 4));
            return j;
        }
    }
}
