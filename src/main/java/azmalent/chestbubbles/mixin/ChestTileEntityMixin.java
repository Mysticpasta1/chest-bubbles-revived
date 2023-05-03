package azmalent.chestbubbles.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class ChestTileEntityMixin {

    @Inject(method = "lidAnimateTick", at = @At(value = "TAIL"))
    private static void makeBubbleParticles(Level level, BlockPos blockPos, BlockState blockState, ChestBlockEntity self, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel) {
            if (blockState.getValue(BlockStateProperties.WATERLOGGED) && self.getOpenNess(serverLevel.getServer().getAverageTickTime()) > 0) {
                RandomSource random = level.getRandom();

                for (int i = 0; i < 1; i++) {
                    double speed = 0.5 + random.nextDouble() * 0.04;

                    level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, 0, speed, 0);
                }

                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.6F + random.nextFloat() * 0.4F, 0.9F + random.nextFloat() * 0.15F);
            }
        }
    }
}
