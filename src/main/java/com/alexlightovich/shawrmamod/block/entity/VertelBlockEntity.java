package com.alexlightovich.shawrmamod.block.entity;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.block.VertelBlock;
import com.alexlightovich.shawrmamod.item.ModItems;
import com.alexlightovich.shawrmamod.screen.VertelBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import java.util.Collection;
import java.util.Optional;

public class VertelBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private static final int FUEL_SLOT = 2;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    private int litProgress = 0;

    private int maxLitProgress = 100;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public VertelBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.VERTEL_BLOCK.get(), p_155229_, p_155230_);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                //System.out.println("pindex" + pIndex+ " pvalue");
                return switch (pIndex) {

                    case 0 -> VertelBlockEntity.this.progress;
                    case 1 -> VertelBlockEntity.this.maxProgress;
                    case 2 -> VertelBlockEntity.this.litProgress;
                    case 3 -> VertelBlockEntity.this.maxLitProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                System.out.println("pindex" + pIndex+ " pvalue" + pValue);
                switch (pIndex) {
                    case 0 -> VertelBlockEntity.this.progress = pValue;
                    case 1 -> VertelBlockEntity.this.maxProgress = pValue;
                    case 2 -> VertelBlockEntity.this.litProgress = pValue;
                    case 3 -> VertelBlockEntity.this.maxLitProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }



    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.shawrmamod.vertel_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new VertelBlockMenu(i,inventory,this,this.data);
    }

    @Override
    public void load(CompoundTag p_155245_) {

        super.load(p_155245_);
        itemHandler.deserializeNBT(p_155245_.getCompound("inventory"));
        progress = p_155245_.getInt("vertel_block.progress");
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.put("inventory", itemHandler.serializeNBT());
        p_187471_.putInt("vertel_block.progress", progress);
        super.saveAdditional(p_187471_);
    }
    private void litProgressChecker(Level level, BlockPos blockPos, BlockState blockState) {
        if(litProgress > 0) {
            decreaseFuelProgress();
            level.setBlock(blockPos,blockState.setValue(VertelBlock.IS_BURNING,Boolean.TRUE),3);
        }
        if(litProgress <= 0) {
            level.setBlock(blockPos,blockState.setValue(VertelBlock.IS_BURNING,Boolean.FALSE),3);
        }
    }
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
            litProgressChecker(level,blockPos,blockState);
            if(hasRecipe()) {
                if(litProgress <= 0) {
                    maxLitProgress = getFuelTime();
                    litProgress = getFuelTime();
                    this.itemHandler.extractItem(FUEL_SLOT,1,false);
                    setChanged(level, blockPos, blockState);
                } else {
                    increaseCraftingProgress();
                    setChanged(level, blockPos, blockState);
                    if (hasProgressFinished()) {
                        //System.out.println("WE ARE IN RECEIPE");
                        craftItem();
                        resetProgress();
                    }
                }
            } else {
                resetProgress();
            }
    }

    private void decreaseFuelProgress() {
        litProgress--;
    }

    private int getFuelTime() {
        return ForgeHooks.getBurnTime(this.itemHandler.getStackInSlot(FUEL_SLOT),null);
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.LAVASH.get(),1);
        this.itemHandler.extractItem(INPUT_SLOT,1,false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()+result.getCount()));
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.RAW_LAVASH.get();
        ItemStack result = new ItemStack(ModItems.LAVASH.get());
        return hasCraftingItem && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

}
