package xyz.achsdiscord.parse;

import static xyz.achsdiscord.parse.util.ParserUtility.ParserUtil.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LobbyNameParser implements INameParser {
    // rank colors
    public static final Color MVP_PLUS_PLUS = new Color(255, 170, 0);
    public static final Color MVP_PLUS = new Color(85, 255, 255);
    public static final Color MVP = new Color(85, 255, 255);
    public static final Color VIP_PLUS = new Color(85, 255, 85);
    public static final Color VIP = new Color(85, 255, 85);
    public static final Color NONE = new Color(170, 170, 170);

    public static final Map<String, String> binaryToCharacters;
    static {
        binaryToCharacters = new HashMap<>();
        binaryToCharacters.put("011111001111111010000010101000101011111010111100", "");
        binaryToCharacters.put("111111101111111010100000101000001111111001011110", "");
        binaryToCharacters.put("100000001100000001111110011111101100000010000000", "");
        binaryToCharacters.put("111111101111111010100010101000101111111001011100", "");
        binaryToCharacters.put("0111110010001010100100101010001001111100", "0");
        binaryToCharacters.put("0000001001000010111111100000001000000010", "1");
        binaryToCharacters.put("0100011010001010100100101001001001100110", "2");
        binaryToCharacters.put("0100010010000010100100101001001001101100", "3");
        binaryToCharacters.put("0001100000101000010010001000100011111110", "4");
        binaryToCharacters.put("1110010010100010101000101010001010011100", "5");
        binaryToCharacters.put("0011110001010010100100101001001000001100", "6");
        binaryToCharacters.put("1100000010000000100011101001000011100000", "7");
        binaryToCharacters.put("0110110010010010100100101001001001101100", "8");
        binaryToCharacters.put("0110000010010010100100101001010001111000", "9");
        binaryToCharacters.put("0000000100000001000000010000000100000001", "_");
        binaryToCharacters.put("0111111010100000101000001010000001111110", "A");
        binaryToCharacters.put("0000010000101010001010100010101000011110", "a");
        binaryToCharacters.put("1111111010100010101000101010001001011100", "B");
        binaryToCharacters.put("1111111000010010001000100010001000011100", "b");
        binaryToCharacters.put("0111110010000010100000101000001001000100", "C");
        binaryToCharacters.put("0001110000100010001000100010001000010100", "c");
        binaryToCharacters.put("1111111010000010100000101000001001111100", "D");
        binaryToCharacters.put("0001110000100010001000100001001011111110", "d");
        binaryToCharacters.put("1111111010100010101000101000001010000010", "E");
        binaryToCharacters.put("0001110000101010001010100010101000011010", "e");
        binaryToCharacters.put("1111111010100000101000001000000010000000", "F");
        binaryToCharacters.put("00100000011111101010000010100000", "f");
        binaryToCharacters.put("0111110010000010100000101010001010111100", "G");
        binaryToCharacters.put("0001100100100101001001010010010100111110", "g");
        binaryToCharacters.put("1111111000100000001000000010000011111110", "H");
        binaryToCharacters.put("1111111000010000001000000010000000011110", "h");
        binaryToCharacters.put("100000101111111010000010", "I");
        binaryToCharacters.put("10111110", "i");
        binaryToCharacters.put("0000010000000010000000100000001011111100", "J");
        binaryToCharacters.put("0000011000000001000000010000000110111110", "j");
        binaryToCharacters.put("1111111000100000001000000101000010001110", "K");
        binaryToCharacters.put("11111110000010000001010000100010", "k");
        binaryToCharacters.put("1111111000000010000000100000001000000010", "L");
        binaryToCharacters.put("1111110000000010", "l");
        binaryToCharacters.put("1111111001000000001000000100000011111110", "M");
        binaryToCharacters.put("0011111000100000000110000010000000011110", "m");
        binaryToCharacters.put("1111111001000000001000000001000011111110", "N");
        binaryToCharacters.put("0011111000100000001000000010000000011110", "n");
        binaryToCharacters.put("0111110010000010100000101000001001111100", "O");
        binaryToCharacters.put("0001110000100010001000100010001000011100", "o");
        binaryToCharacters.put("1111111010100000101000001010000001000000", "P");
        binaryToCharacters.put("0011111100010100001001000010010000011000", "p");
        binaryToCharacters.put("0111110010000010100000101000010001111010", "Q");
        binaryToCharacters.put("0001100000100100001001000001010000111111", "q");
        binaryToCharacters.put("1111111010100000101000001010000001011110", "R");
        binaryToCharacters.put("0011111000010000001000000010000000010000", "r");
        binaryToCharacters.put("0100010010100010101000101010001010011100", "S");
        binaryToCharacters.put("0001001000101010001010100010101000100100", "s");
        binaryToCharacters.put("1000000010000000111111101000000010000000", "T");
        binaryToCharacters.put("001000001111110000100010", "t");
        binaryToCharacters.put("1111110000000010000000100000001011111100", "U");
        binaryToCharacters.put("0011110000000010000000100000001000111110", "u");
        binaryToCharacters.put("1111000000001100000000100000110011110000", "V");
        binaryToCharacters.put("0011100000000100000000100000010000111000", "v");
        binaryToCharacters.put("1111111000000100000010000000010011111110", "W");
        binaryToCharacters.put("0011110000000010000011100000001000111110", "w");
        binaryToCharacters.put("1000111001010000001000000101000010001110", "X");
        binaryToCharacters.put("0010001000010100000010000001010000100010", "x");
        binaryToCharacters.put("1000000001000000001111100100000010000000", "Y");
        binaryToCharacters.put("0011100100000101000001010000010100111110", "y");
        binaryToCharacters.put("1000011010001010100100101010001011000010", "Z");
        binaryToCharacters.put("0010001000100110001010100011001000100010", "z");
    }

    public static final int DEFAULT_WIDTH = 2;
    public static final int LISTED_NUMS_OFFSET = 30;

    // private general variables
    private BufferedImage _img;
    private int _width;

    // for control
    private boolean calledCropIfFullScreen = false;
    private boolean calledCropHeaderFooter = false;
    private boolean calledMakeBlkWtFunc = false;
    private boolean calledFixImgFunc = false;

    /**
     * Creates a new NameProcessor object with the specified BufferedImage.
     *
     * @param img The image.
     */
    public LobbyNameParser(BufferedImage img) {
        this._img = img;
    }

    /**
     * Creates a new NameProcessor object with the specified path to the image.
     *
     * @param file The path to the image.
     * @throws IOException If the path is invalid.
     */
    public LobbyNameParser(File file) throws IOException {
        this._img = ImageIO.read(file);
    }

    /**
     * Creates a new NameProcessor object with the specified URL.
     *
     * @param link The link to the image.
     * @throws IOException If the URL is invalid.
     */
    public LobbyNameParser(URL link) throws IOException {
        this._img = ImageIO.read(link);
    }

    /**
     * If the screenshot provided is a screenshot that shows the entire Minecraft application, call this method first to crop the screenshot appropriately.
     * <p>If you use a screenshot that shows the entire Minecraft application, you MUST run this method first.
     *
     * @return This object.
     * @throws InvalidImageException If the screenshot has no player list.
     */
    public LobbyNameParser cropImageIfFullScreen() throws InvalidImageException {
        if (this.calledCropIfFullScreen) {
            return this;
        }

        this.calledCropIfFullScreen = true;

        // top to bottom, left to right
        // find the top left coordinates of the
        // player list box
        int topLeftX = -1;
        int topLeftY = -1;
        major:
        for (int y = 0; y < this._img.getHeight(); y++) {
            for (int x = 0; x < this._img.getWidth(); x++) {
                if (this._img.getRGB(x, y) == BOSS_BAR_COLOR.getRGB()) {
                    topLeftX = x;
                    topLeftY = y;
                    break major;
                }
            }
        }

        // right to left, bottom to top
        int bottomRightX = -1;
        int bottomRightY = -1;
        major:
        for (int x = this._img.getWidth() - LISTED_NUMS_OFFSET; x >= 0; x--) {
            for (int y = this._img.getHeight() - 1; y >= 0; y--) {
                if (this._img.getRGB(x, y) == STORE_HYPIXEL_NET_DARK_COLOR.getRGB()) {
                    bottomRightX = x;
                    bottomRightY = y;
                    break major;
                }
            }
        }

        if (topLeftX == -1 || bottomRightX == -1) {
            throw new InvalidImageException("Invalid image given. Either a player " +
                    "list wasn't detected or the \"background\" of the player list isn't " +
                    "just the sky. Make sure the image contains the player list and that " +
                    "the \"background\" of the player list is just the sky (no clouds).");
        }

        this._img = cropImage(
                this._img,
                topLeftX,
                topLeftY,
                bottomRightX - topLeftX,
                bottomRightY - topLeftY
        );

        return this;
    }

    /**
     * Makes the image black and white for easier processing. This will also get the width of each character, which will be used later.
     * <p> You must call this method, regardless of screenshot type.
     * <p> If the screenshot provided shows all of Minecraft, you must run {@code cropImageIfFullScreen()} first.
     *
     * @return This object.
     */
    public LobbyNameParser adjustColorsAndIdentifyWidth() {
        if (this.calledMakeBlkWtFunc) {
            return this;
        }

        this.calledMakeBlkWtFunc = true;

        boolean foundLineWithValidColor = false;
        boolean hasTestedWidth = false;
        List<Integer> possibleWidths = new ArrayList<>();

        // make image black and white.
        for (int y = 0; y < this._img.getHeight(); y++) {
            int width = 0;
            for (int x = 0; x < this._img.getWidth(); x++) {
                Color color = new Color(this._img.getRGB(x, y));

                if (this.isValidColor(color)) {
                    foundLineWithValidColor = true;
                    this._img.setRGB(x, y, Color.black.getRGB());
                } else {
                    this._img.setRGB(x, y, Color.white.getRGB());
                }

                if (!hasTestedWidth) {
                    if (this.isValidColor(color)) {
                        foundLineWithValidColor = true;
                        ++width;
                    } else {
                        if (width != 0) {
                            possibleWidths.add(width);
                            width = 0;
                        }
                    }
                }

            }

            if (foundLineWithValidColor) {
                hasTestedWidth = true;
            }
        }

        // this should never be size 0
        if (possibleWidths.size() != 0) {
            this._width = mostCommon(possibleWidths);
        }

        // now, let's make sure there aren't any random "particles" sitting around.
        for (int x = 0; x < this._img.getWidth(); x++) {
            int numberOfParticles = numberParticlesInVertLine(x);
            // more than 10 particles means it's a name.
            if (numberOfParticles > 10) {
                break;
            } else {
                // probably leftovers from a skin with the same
                // colors as one of the rank colors
                for (int y = 0; y < this._img.getHeight(); y++) {
                    if (this._img.getRGB(x, y) == Color.black.getRGB()) {
                        this._img.setRGB(x, y, Color.white.getRGB());
                    }
                }
            }
        }
        return this;
    }

    /**
     * Use this method if you need to crop out the header and footer of the player list.
     * <p>In the case of Hypixel, that will be "You are playing..." and "Ranks, Boosters..."
     * <p>Only run this method if the screenshot you provided was a screenshot of the entire Minecraft application OR you have both header and footer.
     * <p>You must have used the {@code adjustColorsAndIdentifyWidth()} method first.
     *
     * @return This object.
     * @throws InvalidImageException If the image wasn't processed through the {@code adjustColorsAndIdentifyWidth()} method.
     */
    public LobbyNameParser cropHeaderAndFooter() throws InvalidImageException {
        if (this.calledCropHeaderFooter) {
            return this;
        }

        this.calledCropHeaderFooter = true;

        boolean topFirstBlankPast = false;
        boolean topSepFound = false;
        int topY = -1;

        // top to bottom
        for (int y = 0; y < this._img.getHeight(); y++) {
            boolean isSeparator = numberParticlesInHorizLine(y) == 0;
            // top first blank is the top separator that isn't cropped
            if (topFirstBlankPast) {
                // topSepFound is the separator that is
                // BELOW "You are playing on..."
                if (!topSepFound && isSeparator) {
                    topSepFound = true;
                }

                if (topSepFound) {
                    if (isSeparator) {
                        topY = y;
                    } else {
                        break;
                    }
                }
            } else {
                if (!isSeparator) {
                    topFirstBlankPast = true;
                }
            }
        }

        for (int y = this._img.getHeight() - 1; y >= 0; y--) {
            boolean isSep = numberParticlesInHorizLine(y) == 0;
            if (isSep) {
                break;
            } else {
                for (int x = 0; x < this._img.getWidth(); x++) {
                    if (this._img.getRGB(x, y) != Color.white.getRGB()) {
                        this._img.setRGB(x, y, Color.white.getRGB());
                    }
                }
            }
        }

        if (topY == -1) {
            throw new InvalidImageException("Couldn't crop the image. Make " +
                    "sure the image was processed beforehand; perhaps try to " +
                    "run the adjustColorsAndIdentifyWidth() method first!");
        }

        this._img = cropImage(this._img, 0, topY, this._img.getWidth(), this._img.getHeight() - topY);
        return this;
    }

    /**
     * Attempts to crop the image so ONLY the player names show up. The picture must have been made black and white.
     * <p>You must call this method.
     *
     * @return The object.
     * @throws InvalidImageException If the image wasn't processed through the {@code adjustColorsAndIdentifyWidth()} method.
     */
    public LobbyNameParser fixImage() throws InvalidImageException {
        if (this.calledFixImgFunc) {
            return this;
        }

        this.calledFixImgFunc = true;
        // try to crop the
        // list of players
        int startingXVal;
        int startingYVal;
        int minStartingXVal = this._img.getWidth();
        int minStartingYVal = this._img.getHeight();
        // left to right, top to bottom
        for (int y = 0; y < this._img.getHeight(); y++) {
            for (int x = 0; x < this._img.getWidth(); x++) {
                if (this._img.getRGB(x, y) == Color.black.getRGB()) {
                    if (x < minStartingXVal) {
                        minStartingXVal = x;
                    }

                    if (y < minStartingYVal) {
                        minStartingYVal = y;
                    }
                }
            }
        }

        startingXVal = minStartingXVal;
        startingYVal = minStartingYVal;

        if (startingXVal == this._img.getWidth() || startingYVal == this._img.getHeight()) {
            throw new InvalidImageException("Couldn't crop the image. Make " +
                    "sure the image was processed beforehand; perhaps try to " +
                    "run the adjustColorsAndIdentifyWidth() method first!");
        }

        // make new copy of the image
        this._img = cropImage(this._img, startingXVal, startingYVal, this._img.getWidth() - startingXVal, this._img.getHeight() - startingYVal);

        return this;
    }

    /**
     * Gets the player names. If you do not call the appropriate methods, this method will return an empty list.
     *
     * @return A list of names that were in the screenshot.
     */
    public List<String> getPlayerNames() {
        return this.getPlayerNames(new ArrayList<>());
    }

    /**
     * Gets the player names. If you do not call the appropriate methods, this method will return an empty list.
     *
     * @param peopleToExclude The people to exclude in the final list.
     * @return A list of names that were in the screenshot.
     */
    public List<String> getPlayerNames(List<String> peopleToExclude) {
        if (!this.calledMakeBlkWtFunc && !this.calledFixImgFunc) {
            return new ArrayList<>();
        }
        // will contain list of names
        List<String> names = new ArrayList<>();
        int y = 0;

        while (y <= this._img.getHeight()) {
            StringBuilder name = new StringBuilder();
            int x = 0;

            while (true) {
                StringBuilder ttlBytes = new StringBuilder();
                boolean errored = false;
                while (ttlBytes.length() == 0 || !ttlBytes.substring(ttlBytes.length() - 8).equals("00000000")) {
                    try {
                        StringBuilder columnBytes = new StringBuilder();
                        for (int dy = 0; dy < 8 * this._width; dy += this._width) {
                            if (this._img.getRGB(x, y + dy) == Color.black.getRGB()) {
                                columnBytes.append("1");
                            } else {
                                columnBytes.append("0");
                            }
                        }

                        ttlBytes.append(columnBytes.toString());
                        x += this._width;
                    } catch (Exception e) {
                        // this is probably due to poor cropping
                        // or any extra useless characters.
                        errored = true;
                        break;
                    }
                }

                if (!errored) {
                    ttlBytes = new StringBuilder(ttlBytes.substring(0, ttlBytes.length() - 8));
                }

                if (binaryToCharacters.containsKey(ttlBytes.toString())) {
                    name.append(binaryToCharacters.get(ttlBytes.toString()));
                } else {
                    break;
                }
            }
            if (!peopleToExclude.contains(name.toString())) {
                names.add(name.toString());
            }
            // 8 + 1 means the names + the space
            // that separates the first name from
            // the next one
            y += 9 * this._width;
        }

        names = names.stream()
                .filter(name -> name.length() != 0)
                .collect(Collectors.toList());
        return names;
    }

    /**
     * Determines if a pixel is a valid color (one of the rank colors).
     *
     * @param color The color.
     * @return Whether the color is valid or not.
     */
    private boolean isValidColor(Color color) {
        return color.getRGB() == MVP_PLUS_PLUS.getRGB()
                || color.getRGB() == MVP_PLUS.getRGB()
                || color.getRGB() == MVP.getRGB()
                || color.getRGB() == VIP_PLUS.getRGB()
                || color.getRGB() == VIP.getRGB()
                || color.getRGB() == NONE.getRGB();
    }

    /**
     * Returns the image at its current state.
     *
     * @return The image.
     */
    public BufferedImage getImage() {
        return this._img;
    }

    /**
     * Determines the number of particles in a line.
     *
     * @param y The y-line to check.
     * @return The number of particles in that line. "0" means there are no lines (i.e. a separator).
     */
    public int numberParticlesInHorizLine(final int y) {
        int particles = 0;
        for (int x = 0; x < this._img.getWidth(); x++) {
            if (this._img.getRGB(x, y) == Color.black.getRGB()) {
                particles++;
            }
        }

        return particles;
    }

    /**
     * Determines the number of particles in a line.
     *
     * @param x The x-line to check.
     * @return The number of particles in that line. "0" means there are no lines (i.e. a separator).
     */
    public int numberParticlesInVertLine(final int x) {
        int particles = 0;
        for (int y = 0; y < this._img.getHeight(); y++) {
            if (this._img.getRGB(x, y) == Color.black.getRGB()) {
                particles++;
            }
        }

        return particles;
    }
}
