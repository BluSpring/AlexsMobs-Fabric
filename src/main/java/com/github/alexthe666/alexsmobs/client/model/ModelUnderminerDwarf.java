package com.github.alexthe666.alexsmobs.client.model;

import com.github.alexthe666.alexsmobs.entity.EntityUnderminer;
import com.github.alexthe666.alexsmobs.entity.util.Maths;
import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class ModelUnderminerDwarf extends AdvancedEntityModel<EntityUnderminer> {
    private final AdvancedModelBox body;
    private final AdvancedModelBox head;
    private final AdvancedModelBox helmet;
    private final AdvancedModelBox beard;
    private final AdvancedModelBox leftArm;
    private final AdvancedModelBox rightArm;
    private final AdvancedModelBox leftLeg;
    private final AdvancedModelBox rightLeg;
    public HumanoidModel.ArmPose leftArmPose = HumanoidModel.ArmPose.EMPTY;
    public HumanoidModel.ArmPose rightArmPose = HumanoidModel.ArmPose.EMPTY;
    public boolean crouching;
    public float swimAmount;

    public ModelUnderminerDwarf() {
        textureWidth = 128;
        textureHeight = 128;

        body = new AdvancedModelBox(this, "body");
        body.setRotationPoint(0.0F, 12.0F, 0.0F);
        body.setTextureOffset(0, 36).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 11.0F, 6.0F, 0.0F, false);

        head = new AdvancedModelBox(this, "head");
        head.setRotationPoint(0.0F, -10.02F, 0.0F);
        body.addChild(head);
        head.setTextureOffset(30, 24).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 9.0F, 0.0F, false);
        head.setTextureOffset(0, 15).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 9.0F, 0.1F, false);

        helmet = new AdvancedModelBox(this, "helmet");
        helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(helmet);
        helmet.setTextureOffset(0, 0).addBox(-6.0F, -10.0F, -5.5F, 12.0F, 4.0F, 10.0F, 0.1F, false);

        beard = new AdvancedModelBox(this, "beard");
        beard.setRotationPoint(0.0F, 0.1F, -4.1F);
        head.addChild(beard);
        beard.setTextureOffset(0, 54).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 9.0F, 2.0F, 0.0F, false);

        leftArm = new AdvancedModelBox(this, "leftArm");
        leftArm.setRotationPoint(7.0F, -9.0F, 0.0F);
        body.addChild(leftArm);
        leftArm.setTextureOffset(45, 0).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 13.0F, 5.0F, 0.0F, false);

        rightArm = new AdvancedModelBox(this, "rightArm");
        rightArm.setRotationPoint(-7.0F, -9.0F, 0.0F);
        body.addChild(rightArm);
        rightArm.setTextureOffset(45, 0).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 13.0F, 5.0F, 0.0F, true);

        leftLeg = new AdvancedModelBox(this, "leftLeg");
        leftLeg.setRotationPoint(2.0F, 2.0F, 0.0F);
        body.addChild(leftLeg);
        leftLeg.setTextureOffset(33, 42).addBox(-2.0F, -1.0F, -3.0F, 5.0F, 11.0F, 6.0F, 0.0F, false);

        rightLeg = new AdvancedModelBox(this, "rightLeg");
        rightLeg.setRotationPoint(-2.0F, 2.0F, 0.0F);
        body.addChild(rightLeg);
        rightLeg.setTextureOffset(33, 42).addBox(-3.0F, -1.0F, -3.0F, 5.0F, 11.0F, 6.0F, 0.0F, true);
        this.updateDefaultPose();
    }

    @Override
    public Iterable<BasicModelPart> parts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(EntityUnderminer entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.resetToDefaultPose();
        setupHumanoidAnims(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return ImmutableList.of(body, head, beard, helmet, rightLeg, leftLeg, rightArm, leftArm);
    }

    /*
        From here on out copied from HumanoidModel
     */
    public void setupHumanoidAnims(EntityUnderminer entityIn, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {
        boolean flag = entityIn.getFallFlyingTicks() > 4;
        boolean flag1 = entityIn.isVisuallySwimming();
        this.head.yRot = p_102870_ * Mth.DEG_TO_RAD;
        if (flag) {
            this.head.xRot = (-Maths.QUARTER_PI);
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, -Maths.QUARTER_PI);
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, p_102871_ * Mth.DEG_TO_RAD);
            }
        } else {
            this.head.xRot = p_102871_ * Mth.DEG_TO_RAD;
        }
        float f = 1.0F;
        if (flag) {
            f = (float)entityIn.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightArm.xRot = Mth.cos(p_102867_ * 0.6662F + Mth.PI) * 2.0F * p_102868_ * 0.5F / f;
        this.leftArm.xRot = Mth.cos(p_102867_ * 0.6662F) * 2.0F * p_102868_ * 0.5F / f;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = Mth.cos(p_102867_ * 0.6662F) * 1.4F * p_102868_ / f;
        this.leftLeg.xRot = Mth.cos(p_102867_ * 0.6662F + Mth.PI) * 1.4F * p_102868_ / f;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.zRot = 0.0F;
        if (this.riding) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        }

        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;
        boolean flag2 = entityIn.getMainArm() == HumanoidArm.RIGHT;
        if (entityIn.isUsingItem()) {
            boolean flag3 = entityIn.getUsedItemHand() == InteractionHand.MAIN_HAND;
            if (flag3 == flag2) {
                this.poseRightArm(entityIn);
            } else {
                this.poseLeftArm(entityIn);
            }
        } else {
            boolean flag4 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
            if (flag2 != flag4) {
                this.poseLeftArm(entityIn);
                this.poseRightArm(entityIn);
            } else {
                this.poseRightArm(entityIn);
                this.poseLeftArm(entityIn);
            }
        }

        this.setupAttackAnimation(entityIn, p_102869_);
        if (this.crouching) {
            this.body.xRot = 0.5F;
            this.rightArm.xRot += 0.4F;
            this.leftArm.xRot += 0.4F;
        }

        if (this.rightArmPose != HumanoidModel.ArmPose.SPYGLASS) {
            this.rightArm.zRot += (Mth.cos(p_102869_ * 0.09F) * 0.05F + 0.05F);
            this.rightArm.xRot += 1.0F * Mth.sin(p_102869_ * 0.067F) * 0.05F;
        }

        if (this.leftArmPose != HumanoidModel.ArmPose.SPYGLASS) {
            this.leftArm.zRot += -1.0F * (Mth.cos(p_102869_ * 0.09F) * 0.05F + 0.05F);
            this.leftArm.xRot += -1.0F * Mth.sin(p_102869_ * 0.067F) * 0.05F;
        }

        if (this.swimAmount > 0.0F) {
            final float f5 = p_102867_ % 26.0F;
            HumanoidArm humanoidarm = this.getAttackArm(entityIn);
            final float f1 = humanoidarm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            final float f2 = humanoidarm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            if (!entityIn.isUsingItem()) {
                if (f5 < 14.0F) {
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, 0.0F);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, 0.0F);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, Mth.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, Mth.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, Mth.PI + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, Mth.PI - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                } else if (f5 >= 14.0F && f5 < 22.0F) {
                    float f6 = (f5 - 14.0F) / 8.0F;
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, Mth.HALF_PI * f6);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, Mth.HALF_PI * f6);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, Mth.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, Mth.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
                } else if (f5 >= 22.0F && f5 < 26.0F) {
                    float f3 = (f5 - 22.0F) / 4.0F;
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, Mth.HALF_PI - Mth.HALF_PI * f3);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, Mth.HALF_PI - Mth.HALF_PI * f3);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, Mth.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, Mth.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, Mth.PI);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, Mth.PI);
                }
            }

            this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(p_102867_ * 0.33333334F + Mth.PI));
            this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(p_102867_ * 0.33333334F));
        }
    }

    private void poseRightArm(EntityUnderminer p_102876_) {
        switch (this.rightArmPose) {
            case EMPTY:
                this.rightArm.yRot = 0.0F;
                break;
            case BLOCK:
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
                this.rightArm.yRot = (-(float)Math.PI / 6F);
                break;
            case ITEM:
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float)Math.PI / 10F);
                this.rightArm.yRot = 0.0F;
                break;
            case THROW_SPEAR:
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - Mth.PI;
                this.rightArm.yRot = 0.0F;
                break;
            case BOW_AND_ARROW:
                this.rightArm.yRot = -0.1F + this.head.yRot;
                this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
                this.rightArm.xRot = (-Mth.HALF_PI) + this.head.xRot;
                this.leftArm.xRot = (-Mth.HALF_PI) + this.head.xRot;
                break;
            case CROSSBOW_CHARGE: // fallthrough
            case CROSSBOW_HOLD:
                break;
            case SPYGLASS:
                this.rightArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (p_102876_.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                this.rightArm.yRot = this.head.yRot - 0.2617994F;
                break;
            case TOOT_HORN:
                this.rightArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                this.rightArm.yRot = this.head.yRot - ((float)Math.PI / 6F);
        }

    }

    private void poseLeftArm(EntityUnderminer p_102879_) {
        switch (this.leftArmPose) {
            case EMPTY:
                this.leftArm.yRot = 0.0F;
                break;
            case BLOCK:
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
                this.leftArm.yRot = ((float)Math.PI / 6F);
                break;
            case ITEM:
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float)Math.PI / 10F);
                this.leftArm.yRot = 0.0F;
                break;
            case THROW_SPEAR:
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - Mth.PI;
                this.leftArm.yRot = 0.0F;
                break;
            case BOW_AND_ARROW:
                this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
                this.leftArm.yRot = 0.1F + this.head.yRot;
                this.rightArm.xRot = (-Mth.HALF_PI) + this.head.xRot;
                this.leftArm.xRot = (-Mth.HALF_PI) + this.head.xRot;
                break;
            case CROSSBOW_CHARGE:
                break;
            case CROSSBOW_HOLD:
                break;
            case SPYGLASS:
                this.leftArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (p_102879_.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                this.leftArm.yRot = this.head.yRot + 0.2617994F;
                break;
            case TOOT_HORN:
                this.leftArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                this.leftArm.yRot = this.head.yRot + ((float)Math.PI / 6F);
        }

    }

    protected void setupAttackAnimation(EntityUnderminer p_102858_, float p_102859_) {
        if (!(this.attackTime <= 0.0F)) {
            HumanoidArm humanoidarm = this.getAttackArm(p_102858_);
            AdvancedModelBox modelpart = this.getArm(humanoidarm);
            float f = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(f) * Mth.TWO_PI) * 0.2F;
            if (humanoidarm == HumanoidArm.LEFT) {
                this.body.yRot *= -1.0F;
            }
            this.head.yRot -= this.body.yRot;
            this.leftLeg.yRot -= this.body.yRot;
            this.rightLeg.yRot -= this.body.yRot;
            this.leftArm.xRot += this.body.yRot;
            f = 1.0F - this.attackTime;
            f *= f;
            f *= f;
            f = 1.0F - f;
            final float f1 = Mth.sin(f * Mth.PI);
            final float f2 = Mth.sin(this.attackTime * Mth.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            modelpart.xRot -= f1 * 1.2F + f2;
            modelpart.yRot += this.body.yRot * 2.0F;
            modelpart.zRot += Mth.sin(this.attackTime * Mth.PI) * -0.4F;
        }
    }

    protected float rotlerpRad(float p_102836_, float p_102837_, float p_102838_) {
        float f = (p_102838_ - p_102837_) % Mth.TWO_PI;
        if (f < -Mth.PI) {
            f += Mth.TWO_PI;
        }

        if (f >= Mth.PI) {
            f -= Mth.TWO_PI;
        }

        return p_102837_ + p_102836_ * f;
    }

    private float quadraticArmUpdate(float p_102834_) {
        return -65.0F * p_102834_ + p_102834_ * p_102834_;
    }


    public void translateToHand(HumanoidArm p_102854_, PoseStack p_102855_) {
        this.getArm(p_102854_).translateAndRotate(p_102855_);
    }

    protected AdvancedModelBox getArm(HumanoidArm p_102852_) {
        return p_102852_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public AdvancedModelBox getHead() {
        return this.head;
    }

    private HumanoidArm getAttackArm(EntityUnderminer p_102857_) {
        HumanoidArm humanoidarm = p_102857_.getMainArm();
        return p_102857_.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
    }
}