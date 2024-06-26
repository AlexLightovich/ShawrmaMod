package com.alexlightovich.shawrmamod.block;

import com.alexlightovich.shawrmamod.block.entity.ModBlockEntities;
import com.alexlightovich.shawrmamod.block.entity.VertelBlockEntity;
import com.alexlightovich.shawrmamod.screen.ModMenuTypes;
import com.alexlightovich.shawrmamod.screen.VertelBlockMenu;
import com.alexlightovich.shawrmamod.screen.VertelBlockScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.jetbrains.annotations.Nullable;

public class VertelBlock extends BaseEntityBlock {


    public static final BooleanProperty IS_BURNING = BooleanProperty.create("lit") ;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static int lightLevel = 1;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(IS_BURNING);
        p_49915_.add(FACING);
    }

    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);

    public VertelBlock(Properties p_49224_) {
        super(p_49224_);
    }


    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pLevel.getFluidState(pPos.below()).getType() == Fluids.WATER) {
                BlockEntity entity = pLevel.getBlockEntity(pPos);
                if(entity instanceof VertelBlockEntity) {
                    if(pPlayer instanceof ServerPlayer sPlayer) {
                        ((ServerPlayer) pPlayer).openMenu(entity.getBlockState().getMenuProvider(pLevel, pPos), friendlyByteBuf -> friendlyByteBuf.writeBlockPos(entity.getBlockPos()));
                    }
                    //.unsafeCallWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(new VertelBlockScreen()));
//                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (GemPolishingStationBlockEntity)entity, pPos);
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            } else {
                pPlayer.sendSystemMessage(Component.literal("[Shawrma Mod]: Для использования необходимо разместить воду под печкой."));
            }
        }


        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        if(p_153212_.isClientSide()) {
            return null;
        }

        return createTickerHelper(p_153214_, ModBlockEntities.VERTEL_BLOCK.get(),(level, blockPos, blockState, vertelBlockEntity) -> vertelBlockEntity.tick(level,blockPos,blockState));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof VertelBlockEntity) {
                ((VertelBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new VertelBlockEntity(blockPos,blockState);
    }
}
