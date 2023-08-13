package com.github.alexthe666.alexsmobs.client.model.layered;

import com.github.alexthe666.alexsmobs.client.model.ModelWanderingVillagerRider;
import com.github.alexthe666.alexsmobs.client.render.item.CustomArmorRenderer;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRendererRegistry;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class AMModelLayers {

    public static final ModelLayerLocation AM_ELYTRA = createLocation("am_elytra", "main");
    public static final ModelLayerLocation SITTING_WANDERING_VILLAGER = createLocation("sitting_wandering_villager", "main");
    public static final ModelLayerLocation ROADRUNNER_BOOTS = createLocation("roadrunner_boots", "main");
    public static final ModelLayerLocation MOOSE_HEADGEAR = createLocation("moose_headgear", "main");
    public static final ModelLayerLocation FRONTIER_CAP = createLocation("frontier_cap", "main");
    public static final ModelLayerLocation SPIKED_TURTLE_SHELL = createLocation("spiked_turtle_shell", "main");
    public static final ModelLayerLocation FEDORA = createLocation("fedora", "main");
    public static final ModelLayerLocation SOMBRERO = createLocation("sombrero", "main");
    public static final ModelLayerLocation SOMBRERO_GOOFY_FASHION = createLocation("sombrero_goofy_fashion", "main");
    public static final ModelLayerLocation FROSTSTALKER_HELMET = createLocation("froststalker_helmet", "main");
    public static final ModelLayerLocation ROCKY_CHESTPLATE = createLocation("rocky_chestplate", "main");
    public static final ModelLayerLocation FLYING_FISH_BOOTS = createLocation("flying_fish_boots", "main");
    public static final ModelLayerLocation NOVELTY_HAT = createLocation("novelty_hat", "main");
    public static final ModelLayerLocation UNDERMINER = createLocation("underminer", "main");
    public static final ModelLayerLocation UNSETTLING_KIMONO = createLocation("unsettling_kimono", "main");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(SITTING_WANDERING_VILLAGER, () -> LayerDefinition.create(ModelWanderingVillagerRider.createBodyModel(), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(UNDERMINER, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.05F), 64, 64) );
        EntityModelLayerRegistry.registerModelLayer(ROADRUNNER_BOOTS, () -> ModelRoadrunnerBoots.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(MOOSE_HEADGEAR, () -> ModelMooseHeadgear.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(FRONTIER_CAP, () -> ModelFrontierCap.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(SPIKED_TURTLE_SHELL, () -> ModelSpikedTurtleShell.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(FEDORA, () -> ModelFedora.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(AM_ELYTRA, () -> ModelAMElytra.createLayer(new CubeDeformation(1.0F)));
        EntityModelLayerRegistry.registerModelLayer(SOMBRERO, () -> ModelSombrero.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(SOMBRERO_GOOFY_FASHION, () -> ModelSombrero.createArmorLayerAprilFools(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(FROSTSTALKER_HELMET, () -> ModelFroststalkerHelmet.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(ROCKY_CHESTPLATE, () -> ModelRockyChestplate.createArmorLayer(new CubeDeformation(0.7F)));
        EntityModelLayerRegistry.registerModelLayer(FLYING_FISH_BOOTS, () -> ModelFlyingFishBoots.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(NOVELTY_HAT, () -> ModelNoveltyHat.createArmorLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(UNSETTLING_KIMONO, () -> ModelUnsettlingKimono.createArmorLayer(new CubeDeformation(0.5F)));

        var armorItems = List.of(
                AMItemRegistry.TARANTULA_HAWK_ELYTRA,
                AMItemRegistry.ROADDRUNNER_BOOTS,
                AMItemRegistry.MOOSE_HEADGEAR,
                AMItemRegistry.FRONTIER_CAP,
                AMItemRegistry.SPIKED_TURTLE_SHELL,
                AMItemRegistry.FEDORA,
                AMItemRegistry.SOMBRERO,
                AMItemRegistry.FROSTSTALKER_HELMET,
                AMItemRegistry.ROCKY_CHESTPLATE,
                AMItemRegistry.FLYING_FISH_BOOTS,
                AMItemRegistry.NOVELTY_HAT,
                AMItemRegistry.UNSETTLING_KIMONO
        );

        ArmorRendererRegistry.register(new CustomArmorRenderer(), armorItems.stream().map(RegistryObject::get).toList().toArray(new Item[0]));
    }

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation("alexsmobs", model), layer);
    }


}
