package com.github.alexthe666.alexsmobs;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.misc.CapsidRecipeManager;
import com.github.alexthe666.citadel.server.entity.pathfinding.raycoms.PathfindingConstants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import static com.github.alexthe666.alexsmobs.AlexsMobs.MODID;

@EventBusSubscriber(modid = MODID)
public class CommonProxy {

    protected CapsidRecipeManager capsidRecipeManager;

    public void init(IEventBus bus) {
    }

    public void clientInit() {
    }
    public Player getClientSidePlayer() {
        return null;
    }

    public void openBookGUI(ItemStack itemStackIn) {
    }

    public void openBookGUI(ItemStack itemStackIn, String page) {
    }

    public Object getArmorModel(int armorId, LivingEntity entity) {
        return null;
    }

    public void onEntityStatus(Entity entity, byte updateKind) {
    }

    public void updateBiomeVisuals(int x, int z) {
    }

    public void setRenderViewEntity(Entity entity) {

    }

    public void resetRenderViewEntity() {

    }

    public int getPreviousPOV(){
        return 0;
    }

    public boolean isFarFromCamera(double x, double y, double z) {
        return true;
    }

    public void resetVoidPortalCreation(Player player){}

    public Object getISTERProperties() {
        return null;
    }

    public Object getArmorRenderProperties() {
        return null;
    }

    public void spawnSpecialParticle(int i) {
    }

    public void processVisualFlag(Entity entity, int flag) {
    }

    public void setPupfishChunkForItem(int chunkX, int chunkZ) {
    }

    public CapsidRecipeManager initCapsidRecipeManager(RegistryAccess registryAccess) {
        capsidRecipeManager = new CapsidRecipeManager(registryAccess);
        return capsidRecipeManager;
    }

    public CapsidRecipeManager getCapsidRecipeManager(){
        if(capsidRecipeManager == null){
            throw new IllegalStateException("Capsid recipe manager loaded too early!");
        }

        return capsidRecipeManager;
    }

    public void setDisplayTransmuteResult(int slot, ItemStack stack) {

    }

    public ItemStack getDisplayTransmuteResult(int slot) {
        return ItemStack.EMPTY;
    }

    public int getSingingBlueJayId() {
        return -1;
    }

    public void initPathfinding() {
        //PathfindingConstants.isDebugMode = true;
        PathfindingConstants.pathfindingThreads = Math.max(PathfindingConstants.pathfindingThreads, AMConfig.pathfindingThreads);
    }
}
