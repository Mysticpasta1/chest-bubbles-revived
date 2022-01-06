package azmalent.chestbubbles.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.Direction;
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

@Mixin(ChestTileEntity.class)
public class ChestTileEntityMixin {
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void makeBubbleParticles(SoundEvent event, CallbackInfo ci, ChestType chestType, double x, double y, double z) {
        if (event != SoundEvents.BLOCK_CHEST_OPEN) {
            return;
        }

        ChestTileEntity self = (ChestTileEntity) (Object) this;

        BlockState state = self.getBlockState();
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.get(BlockStateProperties.WATERLOGGED)) {
            World world = self.getWorld();
            Random random = world.getRandom();

            double xRange = 0.75;
            double zRange = 0.75;
            int numParticles = 12;

            if (chestType == ChestType.RIGHT) {
                numParticles = 24;

                if (state.get(ChestBlock.FACING).getAxis() == Direction.Axis.X) {
                    zRange = 1.5;
                } else {
                    xRange = 1.5;
                }
            }

            for (int i = 0; i < numParticles; i++) {
                double xPos = x + (random.nextDouble() - 0.5) * xRange;
                double yPos = y + random.nextDouble() / 4 + 0.5;
                double zPos = z + (random.nextDouble() - 0.5) * zRange;
                double speed = 0.08 + random.nextDouble() * 0.04;

                world.addParticle(ParticleTypes.BUBBLE, xPos, yPos, zPos, 0, speed, 0);
            }

            world.playSound(null, x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.6F + random.nextFloat() * 0.4F, 0.9F + random.nextFloat() * 0.15F);
        }
    }
}
