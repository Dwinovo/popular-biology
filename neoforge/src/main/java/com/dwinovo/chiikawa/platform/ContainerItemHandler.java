package com.dwinovo.chiikawa.platform;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

final class ContainerItemHandler implements IItemHandler {
    private final Container container;

    ContainerItemHandler(Container container) {
        this.container = container;
    }

    @Override
    public int getSlots() {
        return container.getContainerSize();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return container.getItem(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !isItemValid(slot, stack)) {
            return stack;
        }
        ItemStack existing = container.getItem(slot);
        int limit = getSlotLimit(slot);

        if (existing.isEmpty()) {
            int toMove = Math.min(stack.getCount(), Math.min(limit, stack.getMaxStackSize()));
            if (!simulate) {
                ItemStack moved = stack.copy();
                moved.setCount(toMove);
                container.setItem(slot, moved);
                container.setChanged();
            }
            ItemStack remaining = stack.copy();
            remaining.shrink(toMove);
            return remaining;
        }

        if (!ItemStack.isSameItemSameComponents(existing, stack)) {
            return stack;
        }

        int space = Math.min(limit, existing.getMaxStackSize()) - existing.getCount();
        if (space <= 0) {
            return stack;
        }
        int toMove = Math.min(space, stack.getCount());
        if (!simulate) {
            existing.grow(toMove);
            container.setItem(slot, existing);
            container.setChanged();
        }
        ItemStack remaining = stack.copy();
        remaining.shrink(toMove);
        return remaining;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) {
            return ItemStack.EMPTY;
        }
        ItemStack existing = container.getItem(slot);
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int toExtract = Math.min(amount, existing.getCount());
        if (simulate) {
            ItemStack extracted = existing.copy();
            extracted.setCount(toExtract);
            return extracted;
        }
        ItemStack removed = container.removeItem(slot, toExtract);
        if (!removed.isEmpty()) {
            container.setChanged();
        }
        return removed;
    }

    @Override
    public int getSlotLimit(int slot) {
        return container.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return container.canPlaceItem(slot, stack);
    }
}
