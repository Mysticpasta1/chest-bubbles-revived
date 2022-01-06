package azmalent.chestbubbles.mixin;

import com.progwml6.ironchest.common.block.tileentity.GenericIronChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(GenericIronChestTileEntity.class)
public class IronChestTileEntityMixin {
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void makeBubbleParticles(SoundEvent event, CallbackInfo ci, double x, double y, double z) {
        if (event != SoundEvents.BLOCK_CHEST_OPEN) {
            return;
        }

        GenericIronChestTileEntity self = (GenericIronChestTileEntity) (Object) this;

        if (self.getBlockState().get(BlockStateProperties.WATERLOGGED)) {
            World world = self.getWorld();
            Random random = world.getRandom();

            for (int i = 0; i < 12; i++) {
                double xPos = x + (random.nextDouble() - 0.5) * 0.75;
                double yPos = y + random.nextDouble() / 4 + 0.5;
                double zPos = z + (random.nextDouble() - 0.5) * 0.75;
                double speed = 0.08 + random.nextDouble() * 0.04;

                world.addParticle(ParticleTypes.BUBBLE, xPos, yPos, zPos, 0, speed, 0);
            }

            world.playSound(null, x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.6F + random.nextFloat() * 0.4F, 0.9F + random.nextFloat() * 0.15F);
        }
    }
}
