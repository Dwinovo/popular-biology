package com.dwinovo.chiikawa.utils;

import com.dwinovo.chiikawa.entity.AbstractPet;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

// Spiral search helpers.
public final class BlockSearch {
    /**
     * Predicate for checking a position during search.
     * @param level the server level
     * @param pos the position
     * @param pet the pet entity
     * @return whether the position matches
     */
    @FunctionalInterface
    public interface BlockCheckPredicate {
        boolean test(ServerLevel level, BlockPos pos, AbstractPet pet);
    }

    private BlockSearch() {
    }

    /**
     * Searches around the pet in a spiral for a matching position.
     * @param level the server level
     * @param pet the pet entity
     * @param maxRadius max horizontal radius
     * @param verticalRange vertical range to scan
     * @param predicate test for a valid position
     * @return the first matching position, if any
     */
    public static Optional<BlockPos> spiralBlockSearch(ServerLevel level, AbstractPet pet,
                                                       int maxRadius, int verticalRange,
                                                       BlockCheckPredicate predicate) {
        BlockPos center = pet.blockPosition();
        for (int radius = 0; radius <= maxRadius; radius++) {
            for (int quadrant = 0; quadrant < 4; quadrant++) {
                for (int i = -radius; i <= radius; i++) {
                    for (int y = -verticalRange; y <= verticalRange; y++) {
                        BlockPos pos = calculateSpiralPos(center, radius, quadrant, i, y);
                        if (predicate.test(level, pos, pet)) {
                            return Optional.of(pos);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Computes a spiral position by quadrant.
     * @param center the center position
     * @param radius radius from the center
     * @param quadrant quadrant index (0-3)
     * @param i horizontal offset index
     * @param y vertical offset
     * @return computed position
     */
    private static BlockPos calculateSpiralPos(BlockPos center, int radius, int quadrant, int i, int y) {
        return switch (quadrant) {
            case 0 -> center.offset(radius, y, i);
            case 1 -> center.offset(-radius, y, i);
            case 2 -> center.offset(i, y, radius);
            case 3 -> center.offset(i, y, -radius);
            default -> center;
        };
    }
}

