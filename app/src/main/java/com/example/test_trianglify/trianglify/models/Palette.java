package com.example.test_trianglify.trianglify.models;

/**
 * <h1>Palette</h1>
 * <b>Description : </b>
 * Set of 9 colors that are used to color a triangulation. Palette contains few predefined color sets
 * as well as method to perform operations on palette.
 *
 * @author kriti
 * @since 18/3/17.
 */

public class Palette {
    public static final int DEFAULT_PALETTE_COUNT = 28;

    private static final int YL_GN = 0;
    private static final int YL = 1;
    private static final int YL_GN_BU = 2;
    private static final int GN_BU = 3;
    private static final int BU_GN = 4;
    private static final int PU_BU_GN = 5;
    private static final int PU_BU = 6;
    private static final int BU_PU = 7;
    private static final int RD_PU = 8;
    private static final int PU_RD = 9;
    private static final int OR_RD = 10;
    private static final int YL_OR_RD = 11;
    private static final int YL_OR_BR = 12;
    private static final int PURPLES = 13;
    private static final int BLUES = 14;
    private static final int GREENS = 15;
    private static final int ORANGES = 16;
    private static final int REDS = 17;
    private static final int GREYS = 18;
    private static final int PU_OR = 19;
    private static final int BR_BL = 20;
    private static final int PU_RD_GN = 21;
    private static final int PI_YL_GN = 22;
    private static final int RD_BU = 23;
    private static final int RD_GY = 24;
    private static final int RD_YL_BU = 25;
    private static final int SPECTRAL = 26;
    private static final int RD_YL_GN = 27;

    // Human-understandable names of all palettes
    public static final String[] PALETTE_NAMES = {
            "Leaf",     // YL_GN
            "Sun",      // YL
            "Shore",    // YL_GN_BU
            "Reef",     // GN_BU
            "Wave",     // BU_GN
            "Bay",      // PU_BU_GN
            "Sky",      // PU_BU
            "Twilight", // BU_PU
            "Plum",     // RD_PU
            "Blush",    // PU_RD
            "Fire",     // OR_RD
            "Amber",    // YL_OR_RD
            "Honey",    // YL_OR_BR
            "Lilac",    // PURPLES
            "Cyan",     // BLUES
            "Moss",     // GREENS
            "Tangerine",// ORANGES
            "Ruby",     // REDS
            "Ash",      // GREYS
            "Vivid",    // PU_OR
            "Desert",   // BR_BL
            "Woods",    // PU_RD_GN
            "Spring",   // PI_YL_GN
            "Frost",    // RD_BU
            "Slate",    // RD_GY
            "Tide",     // RD_YL_BU
            "Spectrum", // SPECTRAL
            "Meadow"    // RD_YL_GN
    };

    private int[] colors;

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        if (colors.length != 9) {
            throw new IllegalArgumentException("Colors array length should exactly be 9");
        }
        this.colors = colors;
    }

    /**
     * Return palette object corresponding to supplied value of paletteIndex, palette is constructed
     * from a predefined set of colors
     *
     * @param paletteIndex Index of palette to return
     * @return Palette object generated from predefined set of colors
     */
    public static Palette getPalette(int paletteIndex) {
        return switch (paletteIndex) {
            case YL ->
                    new Palette(0xFFffffe0, 0xFFffffcc, 0xFFfffacd, 0xFFffff00, 0xFFffef00, 0xFFffd300, 0xFFf8de7e, 0xFFffd700, 0xFFc3b091);
            case YL_GN ->
                    new Palette(0xFFffffe5, 0xFFf7fcb9, 0xFFd9f0a3, 0xFFaddd8e, 0xFF78c679, 0xFF41ab5d, 0xFF238443, 0xFF006837, 0xFF004529);
            case YL_GN_BU ->
                    new Palette(0xFFffffd9, 0xFFedf8b1, 0xFFc7e9b4, 0xFF7fcdbb, 0xFF41b6c4, 0xFF1d91c0, 0xFF225ea8, 0xFF253494, 0xFF081d58);
            case GN_BU ->
                    new Palette(0xFFf7fcf0, 0xFFe0f3db, 0xFFccebc5, 0xFFa8ddb5, 0xFF7bccc4, 0xFF4eb3d3, 0xFF2b8cbe, 0xFF0868ac, 0xFF084081);
            case BU_GN ->
                    new Palette(0xFFf7fcfd, 0xFFe5f5f9, 0xFFccece6, 0xFF99d8c9, 0xFF66c2a4, 0xFF41ae76, 0xFF238b45, 0xFF006d2c, 0xFF00441c);
            case PU_BU_GN ->
                    new Palette(0xFFfff7fb, 0xFFece2f0, 0xFFd0d1e6, 0xFFa6bddb, 0xFF67a9cf, 0xFF3690c0, 0xFF02818a, 0xFF016c59, 0xFF014636);
            case PU_BU ->
                    new Palette(0xFFfff7fb, 0xFFece7f2, 0xFFd0d1e6, 0xFFa6bddb, 0xFF74a9cf, 0xFF3690c0, 0xFF0570b0, 0xFF045a8d, 0xFF023858);
            case BU_PU ->
                    new Palette(0xFFf7fcfd, 0xFFe0ecf4, 0xFFbfd3e6, 0xFF9ebcda, 0xFF8c96c6, 0xFF8c6bb1, 0xFF88419d, 0xFF810f7c, 0xFF4d004b);
            case RD_PU ->
                    new Palette(0xFFfff7f3, 0xFFfde0dd, 0xFFfcc5c0, 0xFFfa9fb5, 0xFFf768a1, 0xFFdd3497, 0xFFae017e, 0xFF7a0177, 0xFF49006a);
            case PU_RD ->
                    new Palette(0xFFf7f4f9, 0xFFe7e1ef, 0xFFd4b9da, 0xFFc994c7, 0xFFdf65b0, 0xFFe7298a, 0xFFce1256, 0xFF980043, 0xFF67001f);
            case OR_RD ->
                    new Palette(0xFFfff7ec, 0xFFfee8c8, 0xFFfdd49e, 0xFFfdbb84, 0xFFfc8d59, 0xFFef6548, 0xFFd7301f, 0xFFb30000, 0xFF7f0000);
            case YL_OR_RD ->
                    new Palette(0xFFffffcc, 0xFFffeda0, 0xFFfed976, 0xFFfeb24c, 0xFFfd8d3c, 0xFFfc4e2a, 0xFFe31a1c, 0xFFbd0026, 0xFF800026);
            case YL_OR_BR ->
                    new Palette(0xFFffffe5, 0xFFfff7bc, 0xFFfee391, 0xFFfec44f, 0xFFfe9929, 0xFFec7014, 0xFFcc4c02, 0xFF993404, 0xFF662506);
            case PURPLES ->
                    new Palette(0xFFfcfbfd, 0xFFefedf5, 0xFFdadaeb, 0xFFbcbddc, 0xFF9e9ac8, 0xFF807dba, 0xFF6a51a3, 0xFF54278f, 0xFF3f007d);
            case BLUES ->
                    new Palette(0xFFf7fbff, 0xFFdeebf7, 0xFFc6dbef, 0xFF9ecae1, 0xFF6baed6, 0xFF4292c6, 0xFF2171b5, 0xFF08519c, 0xFF08306b);
            case GREENS ->
                    new Palette(0xFFf7fcf5, 0xFFe5f5e0, 0xFFc7e9c0, 0xFFa1d99b, 0xFF74c476, 0xFF41ab5d, 0xFF238b45, 0xFF006d2c, 0xFF00441b);
            case ORANGES ->
                    new Palette(0xFFfff5eb, 0xFFfee6ce, 0xFFfdd0a2, 0xFFfdae6b, 0xFFfd8d3c, 0xFFf16913, 0xFFd94801, 0xFFa63603, 0xFF7f2704);
            case REDS ->
                    new Palette(0xFFfff5f0, 0xFFfee0d2, 0xFFfcbba1, 0xFFfc9272, 0xFFfb6a4a, 0xFFef3b2c, 0xFFcb181d, 0xFFa50f15, 0xFF67000d);
            case GREYS ->
                    new Palette(0xFFffffff, 0xFFf0f0f0, 0xFFd9d9d9, 0xFFbdbdbd, 0xFF969696, 0xFF737373, 0xFF525252, 0xFF252525, 0xFF000000);
            case PU_OR ->
                    new Palette(0xFF7f3b08, 0xFFb35806, 0xFFe08214, 0xFFfdb863, 0xFFfee0b6, 0xFFf7f7f7, 0xFFd8daeb, 0xFFb2abd2, 0xFF8073ac);
            case BR_BL ->
                    new Palette(0xFF543005, 0xFF8c510a, 0xFFbf812d, 0xFFdfc27d, 0xFFf6e8c3, 0xFFf5f5f5, 0xFFc7eae5, 0xFF80cdc1, 0xFF35978f);
            case PU_RD_GN ->
                    new Palette(0xFF40004b, 0xFF762a83, 0xFF9970ab, 0xFFc2a5cf, 0xFFe7d4e8, 0xFFf7f7f7, 0xFFd9f0d3, 0xFFa6dba0, 0xFF5aae61);
            case PI_YL_GN ->
                    new Palette(0xFF8e0152, 0xFFc51b7d, 0xFFde77ae, 0xFFf1b6da, 0xFFfde0ef, 0xFFf7f7f7, 0xFFe6f5d0, 0xFFb8e186, 0xFF7fbc41);
            case RD_BU ->
                    new Palette(0xFF67001f, 0xFFb2182b, 0xFFd6604d, 0xFFf4a582, 0xFFfddbc7, 0xFFf7f7f7, 0xFFd1e5f0, 0xFF92c5de, 0xFF4393c3);
            case RD_GY ->
                    new Palette(0xFF67001f, 0xFFb2182b, 0xFFd6604d, 0xFFf4a582, 0xFFfddbc7, 0xFFffffff, 0xFFe0e0e0, 0xFFbababa, 0xFF878787);
            case RD_YL_BU ->
                    new Palette(0xFFa50026, 0xFFd73027, 0xFFf46d43, 0xFFfdae61, 0xFFfee090, 0xFFffffbf, 0xFFe0f3f8, 0xFFabd9e9, 0xFF74add1);
            case SPECTRAL ->
                    new Palette(0xFF9e0142, 0xFFd53e4f, 0xFFf46d43, 0xFFfdae61, 0xFFfee08b, 0xFFffffbf, 0xFFe6f598, 0xFFabdda4, 0xFF66c2a5);
            case RD_YL_GN ->
                    new Palette(0xFFa50026, 0xFFd73027, 0xFFf46d43, 0xFFfdae61, 0xFFfee08b, 0xFFffffbf, 0xFFd9ef8b, 0xFFa6d96a, 0xFF66bd63);
            default ->
                    throw new IllegalArgumentException("Index should be less Palette.DEFAULT_PALETTE_COUNT");
        };
    }

    /**
     * Returns index of palette object passed from list of palettes predefined in Palette
     *
     * @param palette Object for finding index
     * @return Index from predefined pallete or -1 if not found
     */
    public static int indexOf(Palette palette) {
        int pos = -1;
        int[] passedPaletteColors = palette.getColors();

        for (int i = 0; i < Palette.DEFAULT_PALETTE_COUNT; i++) {
            int[] calledPaletteColors = Palette.getPalette(i).getColors();

            for (int j = 0; j < 9; j++) {
                if (passedPaletteColors[j] != calledPaletteColors[j]) {
                    break;
                }
                if (j == 8) {
                    return i;
                }
            }
        }
        return pos;
    }

    public Palette(int c0, int c1, int c2, int c3, int c4, int c5, int c6, int c7, int c8) {
        colors = new int[9];
        colors[0] = c0;
        colors[1] = c1;
        colors[2] = c2;
        colors[3] = c3;
        colors[4] = c4;
        colors[5] = c5;
        colors[6] = c6;
        colors[7] = c7;
        colors[8] = c8;
    }

    public Palette(int[] colors) {
        if (colors.length != 9) {
            throw new IllegalArgumentException("Colors array length should exactly be 9");
        }
        this.colors = colors;
    }

    /**
     * Returns color corresponding to index passed from the set of color for a palette
     *
     * @param index Index of color in set of color for current palette object
     * @return color as int without alpha channel
     */
    public int getColor(int index) {
        return switch (index) {
            case 0 -> colors[0];
            case 1 -> colors[1];
            case 2 -> colors[2];
            case 3 -> colors[3];
            case 4 -> colors[4];
            case 5 -> colors[5];
            case 6 -> colors[6];
            case 7 -> colors[7];
            case 8 -> colors[8];
            default -> throw new IllegalArgumentException("Index should be less than 9");
        };
    }
}
