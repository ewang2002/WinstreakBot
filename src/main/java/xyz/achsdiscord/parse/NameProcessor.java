package xyz.achsdiscord.parse;

import xyz.achsdiscord.classes.RankColor;
import xyz.achsdiscord.util.Utility;

import javax.imageio.ImageIO;
import javax.naming.Name;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameProcessor {
    public static final HashMap<String, BufferedImage> characterImageMap;

    public static final RankColor[] rankColors = {
            new RankColor("MVP++", 255, 170, 0),
            new RankColor("MVP+", 85, 255, 255),
            new RankColor("MVP", 85, 255, 255),
            new RankColor("VIP+", 85, 255, 85),
            new RankColor("VIP", 85, 255, 85),
            new RankColor("NONE", 170, 170, 170)
    };

    private BufferedImage _img;

    public NameProcessor(BufferedImage img) {
        this._img = img;
    }

    public NameProcessor(Path imgPath) throws IOException {
        this._img = ImageIO.read(imgPath.toFile());
    }

    public NameProcessor(URL link) throws IOException {
        this._img = ImageIO.read(link);
    }

    public NameProcessor fixImage() {
        for (int y = 0; y < this._img.getHeight(); y++) {
            for (int x = 0; x < this._img.getWidth(); x++) {
                Color color = new Color(this._img.getRGB(x, y));

                boolean isChanged = false;
                for (RankColor rankColor : rankColors) {
                    if (color.getRGB() == rankColor.color.getRGB()) {
                        isChanged = true;
                        this._img.setRGB(x, y, Color.black.getRGB());
                    }
                }

                if (!isChanged) {
                    this._img.setRGB(x, y, Color.white.getRGB());
                }
            }
        }

        return this;
    }

    public NameProcessor cropImage() throws Exception {
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

        // right to left, top to bottom
        int finalXVal;
        int mostXVal = -1;
        for (int y = 0; y < this._img.getHeight(); y++) {
            for (int x = this._img.getWidth() - 1; x >= 0; x--) {
                if (this._img.getRGB(x, y) == Color.black.getRGB()) {
                    int newPossibleX = x;
                    if (newPossibleX > mostXVal) {
                        mostXVal = newPossibleX;
                    }
                }
            }
        }

        finalXVal = mostXVal;

        // left to right, bottom to top
        int finalYVal;
        int mostYVal = -1;
        for (int x = 0; x < this._img.getWidth(); x++) {
            for (int y = this._img.getHeight() - 1; y >= 0; y--) {
                if (this._img.getRGB(x, y) == Color.black.getRGB()) {
                    int newPossibleY = y;
                    if (newPossibleY > mostYVal) {
                        mostYVal = newPossibleY;
                    }
                }
            }
        }

        finalYVal = mostYVal;

        if (finalXVal == -1 || finalYVal == -1) {
            throw new Exception("invalid image given");
        }

        // make new copy of the image
        BufferedImage img = this._img.getSubimage(startingXVal, startingYVal, finalXVal - startingXVal, finalYVal - startingYVal); //fill in the corners of the desired crop location here
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        this._img = copyOfImage;
        return this;
    }

    public List<String> getPlayerNames(final int widthOfEachCharacter, final int heightOfName) throws IOException {
        final List<String> list = new ArrayList<>();

        final List<BufferedImage> allNamesInChunks = new ArrayList<>();

        final int INCREMENT_BY = 16;
        for (int y = 0; y < this._img.getHeight() - INCREMENT_BY; y += INCREMENT_BY) {
            BufferedImage img = this._img.getSubimage(0, y, this._img.getWidth(), INCREMENT_BY); //fill in the corners of the desired crop location here
            BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = copyOfImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            allNamesInChunks.add(copyOfImage);
            y += 2;
        }

        int num = 0;
        for (BufferedImage img : allNamesInChunks) {
            File outputfile = new File("C:\\Users\\ewang\\Desktop\\Output\\" + ++num + ".png");
            ImageIO.write(img, "png", outputfile);
        }

        return list;
    }


    public BufferedImage getImage() {
        return this._img;
    }

    static {
        characterImageMap = new HashMap<>();
        try {
            characterImageMap.put("0", ImageIO.read(new File(Utility.getPathOfResource("mcText/0.png"))));
            characterImageMap.put("1", ImageIO.read(new File(Utility.getPathOfResource("mcText/1.png"))));
            characterImageMap.put("2", ImageIO.read(new File(Utility.getPathOfResource("mcText/2.png"))));
            characterImageMap.put("3", ImageIO.read(new File(Utility.getPathOfResource("mcText/3.png"))));
            characterImageMap.put("4", ImageIO.read(new File(Utility.getPathOfResource("mcText/4.png"))));
            characterImageMap.put("5", ImageIO.read(new File(Utility.getPathOfResource("mcText/5.png"))));
            characterImageMap.put("6", ImageIO.read(new File(Utility.getPathOfResource("mcText/6.png"))));
            characterImageMap.put("7", ImageIO.read(new File(Utility.getPathOfResource("mcText/7.png"))));
            characterImageMap.put("8", ImageIO.read(new File(Utility.getPathOfResource("mcText/8.png"))));
            characterImageMap.put("9", ImageIO.read(new File(Utility.getPathOfResource("mcText/9.png"))));
            characterImageMap.put("A", ImageIO.read(new File(Utility.getPathOfResource("mcText/a_cap.png"))));
            characterImageMap.put("a", ImageIO.read(new File(Utility.getPathOfResource("mcText/a_low.png"))));
            characterImageMap.put("B", ImageIO.read(new File(Utility.getPathOfResource("mcText/b_cap.png"))));
            characterImageMap.put("b", ImageIO.read(new File(Utility.getPathOfResource("mcText/b_low.png"))));
            characterImageMap.put("C", ImageIO.read(new File(Utility.getPathOfResource("mcText/c_cap.png"))));
            characterImageMap.put("c", ImageIO.read(new File(Utility.getPathOfResource("mcText/c_low.png"))));
            characterImageMap.put("D", ImageIO.read(new File(Utility.getPathOfResource("mcText/d_cap.png"))));
            characterImageMap.put("d", ImageIO.read(new File(Utility.getPathOfResource("mcText/d_low.png"))));
            characterImageMap.put("E", ImageIO.read(new File(Utility.getPathOfResource("mcText/e_cap.png"))));
            characterImageMap.put("e", ImageIO.read(new File(Utility.getPathOfResource("mcText/e_low.png"))));
            characterImageMap.put("F", ImageIO.read(new File(Utility.getPathOfResource("mcText/f_cap.png"))));
            characterImageMap.put("f", ImageIO.read(new File(Utility.getPathOfResource("mcText/f_low.png"))));
            characterImageMap.put("G", ImageIO.read(new File(Utility.getPathOfResource("mcText/g_cap.png"))));
            characterImageMap.put("g", ImageIO.read(new File(Utility.getPathOfResource("mcText/g_low.png"))));
            characterImageMap.put("H", ImageIO.read(new File(Utility.getPathOfResource("mcText/h_cap.png"))));
            characterImageMap.put("h", ImageIO.read(new File(Utility.getPathOfResource("mcText/h_low.png"))));
            characterImageMap.put("I", ImageIO.read(new File(Utility.getPathOfResource("mcText/i_cap.png"))));
            characterImageMap.put("i", ImageIO.read(new File(Utility.getPathOfResource("mcText/i_low.png"))));
            characterImageMap.put("J", ImageIO.read(new File(Utility.getPathOfResource("mcText/j_cap.png"))));
            characterImageMap.put("j", ImageIO.read(new File(Utility.getPathOfResource("mcText/j_low.png"))));
            characterImageMap.put("K", ImageIO.read(new File(Utility.getPathOfResource("mcText/k_cap.png"))));
            characterImageMap.put("k", ImageIO.read(new File(Utility.getPathOfResource("mcText/k_low.png"))));
            characterImageMap.put("L", ImageIO.read(new File(Utility.getPathOfResource("mcText/l_cap.png"))));
            characterImageMap.put("l", ImageIO.read(new File(Utility.getPathOfResource("mcText/l_low.png"))));
            characterImageMap.put("M", ImageIO.read(new File(Utility.getPathOfResource("mcText/m_cap.png"))));
            characterImageMap.put("m", ImageIO.read(new File(Utility.getPathOfResource("mcText/m_low.png"))));
            characterImageMap.put("N", ImageIO.read(new File(Utility.getPathOfResource("mcText/n_cap.png"))));
            characterImageMap.put("n", ImageIO.read(new File(Utility.getPathOfResource("mcText/n_low.png"))));
            characterImageMap.put("O", ImageIO.read(new File(Utility.getPathOfResource("mcText/o_cap.png"))));
            characterImageMap.put("o", ImageIO.read(new File(Utility.getPathOfResource("mcText/o_low.png"))));
            characterImageMap.put("P", ImageIO.read(new File(Utility.getPathOfResource("mcText/p_cap.png"))));
            characterImageMap.put("p", ImageIO.read(new File(Utility.getPathOfResource("mcText/p_low.png"))));
            characterImageMap.put("Q", ImageIO.read(new File(Utility.getPathOfResource("mcText/q_cap.png"))));
            characterImageMap.put("q", ImageIO.read(new File(Utility.getPathOfResource("mcText/q_low.png"))));
            characterImageMap.put("R", ImageIO.read(new File(Utility.getPathOfResource("mcText/r_cap.png"))));
            characterImageMap.put("r", ImageIO.read(new File(Utility.getPathOfResource("mcText/r_low.png"))));
            characterImageMap.put("S", ImageIO.read(new File(Utility.getPathOfResource("mcText/s_cap.png"))));
            characterImageMap.put("s", ImageIO.read(new File(Utility.getPathOfResource("mcText/s_low.png"))));
            characterImageMap.put("T", ImageIO.read(new File(Utility.getPathOfResource("mcText/t_cap.png"))));
            characterImageMap.put("t", ImageIO.read(new File(Utility.getPathOfResource("mcText/t_low.png"))));
            characterImageMap.put("U", ImageIO.read(new File(Utility.getPathOfResource("mcText/u_cap.png"))));
            characterImageMap.put("u", ImageIO.read(new File(Utility.getPathOfResource("mcText/u_low.png"))));
            characterImageMap.put("V", ImageIO.read(new File(Utility.getPathOfResource("mcText/v_cap.png"))));
            characterImageMap.put("v", ImageIO.read(new File(Utility.getPathOfResource("mcText/v_low.png"))));
            characterImageMap.put("W", ImageIO.read(new File(Utility.getPathOfResource("mcText/w_cap.png"))));
            characterImageMap.put("w", ImageIO.read(new File(Utility.getPathOfResource("mcText/w_low.png"))));
            characterImageMap.put("X", ImageIO.read(new File(Utility.getPathOfResource("mcText/x_cap.png"))));
            characterImageMap.put("x", ImageIO.read(new File(Utility.getPathOfResource("mcText/x_low.png"))));
            characterImageMap.put("Y", ImageIO.read(new File(Utility.getPathOfResource("mcText/y_cap.png"))));
            characterImageMap.put("y", ImageIO.read(new File(Utility.getPathOfResource("mcText/y_low.png"))));
            characterImageMap.put("Z", ImageIO.read(new File(Utility.getPathOfResource("mcText/z_cap.png"))));
            characterImageMap.put("z", ImageIO.read(new File(Utility.getPathOfResource("mcText/z_low.png"))));
            characterImageMap.put("_", ImageIO.read(new File(Utility.getPathOfResource("mcText/underscore.png"))));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
