package com.github.alexthe666.alexsmobs.entity.ai;

import com.github.alexthe666.alexsmobs.entity.EntityKomodoDragon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.BreedGoal;

public class KomodoDragonAIBreed extends BreedGoal {
    boolean withPartner;
    private final EntityKomodoDragon komodo;
    int selfBreedTime = 0;

    public KomodoDragonAIBreed(EntityKomodoDragon entityKomodoDragon, double v) {
        super(entityKomodoDragon, v);
        this.komodo = entityKomodoDragon;
    }

    @Override
    public boolean canUse(){
        boolean prev = super.canUse();
        withPartner = prev;
        return withPartner || animal.isInLove();
    }

    @Override
    public boolean canContinueToUse() {
        return withPartner ? super.canContinueToUse() : selfBreedTime < 60;
    }

    @Override
    public void stop() {
        super.stop();
        selfBreedTime = 0;
    }

    @Override
    public void tick() {
        if(withPartner){
            super.tick();
        }else{
            this.animal.getNavigation().stop();
            ++this.selfBreedTime;
            if (this.selfBreedTime >= 60) {
                this.spawnParthogenicBaby();
            }
        }
    }

    @Override
    protected void breed() {
        for(int i = 0; i < 2 + this.animal.getRandom().nextInt(2); i++){
            this.animal.spawnChildFromBreeding((ServerLevel)this.level, this.partner);
        }
        komodo.slaughterCooldown = 200;
    }

    private void spawnParthogenicBaby() {
        for(int i = 0; i < 2 + this.animal.getRandom().nextInt(2); i++){
            this.animal.spawnChildFromBreeding((ServerLevel)this.level, this.animal);
        }
        komodo.slaughterCooldown = 200;
    }
}
