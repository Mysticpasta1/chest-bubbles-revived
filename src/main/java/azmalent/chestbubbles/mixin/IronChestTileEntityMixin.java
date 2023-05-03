package azmalent.chestbubbles.mixin;

import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractIronChestBlockEntity.class)
public class IronChestTileEntityMixin {
    /*@Inject(method = "lidAnimateTick", at = @At(value = "TAIL"))
    private static void makeBubbleParticles(Level level, BlockPos blockPos, BlockState blockState, AbstractIronChestBlockEntity self, CallbackInfo ci) {
        if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
            RandomSource random = level.getRandom();

            for (int i = 0; i < 1; i++) {
                double speed = 0.5 + random.nextDouble() * 0.04;

                level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, speed, 0);
            }

            level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.6F + random.nextFloat() * 0.4F, 0.9F + random.nextFloat() * 0.15F);
        }
    }*/
}
