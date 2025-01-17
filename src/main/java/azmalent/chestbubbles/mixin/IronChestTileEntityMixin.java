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
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractIronChestBlockEntity.class)
public class IronChestTileEntityMixin {
    private static int e, a;

    @Inject(method = "lidAnimateTick", at = @At(value = "TAIL"), remap = false)
    private static void makeBubbleParticles(Level level, BlockPos blockPos, BlockState blockState, AbstractIronChestBlockEntity self, CallbackInfo ci) {
        if (ModList.get().isLoaded("ironchest")) {
            if (blockState.getValue(BlockStateProperties.WATERLOGGED) && self.triggerEvent((int) self.getOpenNess((float) e), a)) {
                RandomSource random = level.getRandom();

                for (int i = 0; i < 1; i++) {
                    double speed = 0.5 + random.nextDouble() * 0.04;

                    level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, 0, speed, 0);
                }

                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.6F + random.nextFloat() * 0.4F, 0.9F + random.nextFloat() * 0.15F);
            }
        }
    }

    @Inject(method = "triggerEvent", at = @At("TAIL"))
    public void getInts(int e2, int a2, CallbackInfoReturnable<Boolean> cir) {
        if (ModList.get().isLoaded("ironchest")) {
            a = a2;
            e = e2;
        }
    }
}
