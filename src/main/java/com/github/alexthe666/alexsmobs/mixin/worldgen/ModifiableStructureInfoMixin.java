package com.github.alexthe666.alexsmobs.mixin.worldgen;

import com.github.alexthe666.alexsmobs.world.AMMobSpawnStructureModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.bluspring.forgebiomemodifiers.structures.ModifiableStructureInfo;
import xyz.bluspring.forgebiomemodifiers.structures.StructureModifier;

import java.util.ArrayList;
import java.util.List;

@Mixin(ModifiableStructureInfo.class)
public class ModifiableStructureInfoMixin {
    @ModifyVariable(method = "applyStructureModifiers", at = @At("HEAD"), remap = false, ordinal = 0, argsOnly = true)
    public List<StructureModifier> am$addToStructureModifiers(List<StructureModifier> modifiers) {
        var list = new ArrayList<>(modifiers);

        list.add(new AMMobSpawnStructureModifier());

        return list;
    }
}
