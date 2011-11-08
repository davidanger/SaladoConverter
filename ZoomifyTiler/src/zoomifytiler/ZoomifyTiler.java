package zoomifytiler;

/**
 * Copyright 2011 Marek Standio
 * This file is part of ZoomifyTiler.java.
 * ZoomifyTiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with ZoomifyTiler. If not, see http://www.gnu.org/licenses/.
 * This code is modified from DeepJZoom, courtesy of Glenn Lawrence,
 * which is also licensed under the GPL.  Thank you!
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.imageio.*;
import javax.imageio.stream.*;
import com.sun.media.jai.codec.FileSeekableStream;
import java.awt.Point;
import java.awt.geom.Point2D;

public class ZoomifyTiler {

    static final String help = "\nZoomifyTiler v1.0 \n\n Usage: \n\n"
            + "java [-java_options] -jar path/to/ZoomifyTiler.jar [-options] [args...]\n\n"
            + "For a list of java options try: java -help or java -X for a list of less\n"
            + "common options. Loading large images for conversion takes a lot of RAM,\n"
            + "so you will find the -Xmx option useful to raise Java's maximum heap size.\n"
            + "The -Xmx command is followed immediately by an integer specifying RAM size\n"
            + "and a unit indicator. For example: -Xmx1024m means to use 1024 megabytes.\n"
            + "If you see an heap size error, then you will need to increase this value.\n\n"
            + " Basic usage example for the jar file:\n\n"
            + "java -Xmx1024m -jar path/to/ZoomifyTiler.jar path/to/image\n\n"
            + "This will generate a folder besides input image with 'tiles_' prepended\n"
            + "onto its name. So, in basic example above the output folder for image\n"
            + "would be in path/to/image/tiles_image.\n\n"
            + " Options:\n\n"
            + "-quality: output JPEG compression. 0.0 is maximum compression, lowest\n"
            + "\tquality, smallest file. 1.0 is least compression, highest quality,\n"
            + "\tlargest file. Default is 0.8\n\n"
            + "-tilesize: target pixel tile size. Tiling starts at the top left corner\n"
            + "\tof an image, so tiles at the right and bottom to the image may not\n"
            + "\tbe this size, respectively, unless the input image's size is\n"
            + "-zerotilesize: size of longer side of 0-0-0 tile, which is single tile\n"
            + "\tshowing entire image. Such tile can be used as preview and it can be too\n"
            + "\tbig for big value of tile size. Setting 0 value is interpreted as using\n"
            + "\tdefault zoomify size. Default is 0\n\n"
            + "-outputdir or -o: the output directory for the converted images. It need\n"
            + "\tnot exist. Default is a folder next to the input file twith\n"
            + "\t'tiles_' prepended to the name of the input image.\n\n"
            + "-simpleoutput or -s: when processing single image 'tiles_' parent directory\n"
            + "\tfor output file is not created. Both xml file and folder containing\n"
            + "\ttiles are saved directly into output folder.\n\n"
            + " Arguments:\n\n"
            + "The argument following any options is input image or folder that contains\n"
            + "images. Input folder will not be NOT be recursed. Only images immediately\n"
            + "inside the folder will be processed.\n";

    private enum CmdParseState {

        DEFAULT, OUTPUTDIR, TILESIZE, ZEROTILESIZE, QUALITY, INPUTFILE
    }
    // The following can be overriden/set by the indicated command line arguments
    static boolean showHelp = false;               // -help | -h
    static int tileSize = 512;                     // -tilesize
    static int zeroTileSize = 0;                  // -zerotilesize
    static float quality = 0.8f;	           // -quality (0.0 to 1.0)
    static File outputDir = null;                  // -outputdir | -o    
    static boolean simpleoutput = false;           // -simpleoutput | -s
    static boolean verboseMode = false;            // -verbose
    static ArrayList<File> inputFiles = new ArrayList<File>(); // must follow all other args    

    public static void main(String[] args) {
        try {
            try {
                parseCommandLine(args);
                if (showHelp) {
                    System.out.println(help);
                    return;
                }
                if (outputDir != null) {
                    if (!outputDir.exists() || !outputDir.isDirectory()) {
                        if (!outputDir.mkdir()) {
                            throw new IOException("Unable to create directory: " + outputDir);
                        }
                    }
                } else {
                    outputDir = inputFiles.get(0).getAbsoluteFile().getParentFile();
                }
            } catch (Exception e) {
                System.out.println("Invalid command: " + e.getMessage());
                System.out.println("For avaible options type: -help or -h");
                return;
            }
            System.setProperty("com.sun.media.jai.disableMediaLib", "true");
            // it probably can be problematic in non-admin accounts
            // java -D com.sun.media.jai.disableMediaLib=true YourApp

            if (inputFiles.size() > 1) {
                simpleoutput = false;
            }
            for (int i = 0; i < inputFiles.size(); i++) {
                if (simpleoutput) {
                    processImageFile(inputFiles.get(i), outputDir);
                } else {
                    String fileName = inputFiles.get(i).getName();
                    String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                    processImageFile(inputFiles.get(i),
                            createDir(outputDir,
                            "tiles_" + nameWithoutExtension));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseCommandLine(String[] args) throws Exception {
        CmdParseState state = CmdParseState.DEFAULT;
        for (int count = 0; count < args.length; count++) {
            String arg = args[count];
            switch (state) {
                case DEFAULT:
                    if (arg.equals("-h") || arg.equals("-help")) {
                        showHelp = true;
                        return;
                    } else if (arg.equals("-verbose")) {
                        verboseMode = true;
                    } else if (arg.equals("-simpleoutput") || arg.equals("-s")) {
                        simpleoutput = true;
                    } else if (arg.equals("-outputdir") || arg.equals("-o")) {
                        state = CmdParseState.OUTPUTDIR;
                    } else if (arg.equals("-tilesize")) {
                        state = CmdParseState.TILESIZE;
                    } else if (arg.equals("-zerotilesize")) {
                        state = CmdParseState.ZEROTILESIZE;
                    } else if (arg.equals("-quality")) {
                        state = CmdParseState.QUALITY;
                    } else {
                        state = CmdParseState.INPUTFILE;
                    }
                    break;
                case OUTPUTDIR:
                    outputDir = new File(args[count]);
                    state = CmdParseState.DEFAULT;
                    break;
                case TILESIZE:
                    tileSize = Integer.parseInt(args[count]);
                    state = CmdParseState.DEFAULT;
                    break;
                case ZEROTILESIZE:
                    zeroTileSize = Integer.parseInt(args[count]);
                    state = CmdParseState.DEFAULT;
                    break;
                case QUALITY:
                    float qtmp = Float.parseFloat(args[count]);
                    if (qtmp < 0 || qtmp > 1) {
                        throw new Exception("-quality");
                    }
                    quality = qtmp;
                    state = CmdParseState.DEFAULT;
                    break;
            }
            if (state == CmdParseState.INPUTFILE) {
                File inputFile = new File(arg);
                if (!inputFile.exists()) {
                    throw new FileNotFoundException("Missing input: " + inputFile.getPath());
                }
                ArrayList<String> exts = new ArrayList<String>();
                exts.add("bmp");
                exts.add("jpg");
                exts.add("jpeg");
                exts.add("png");
                exts.add("gif");
                exts.add("tif");
                exts.add("tiff");
                if (inputFile.isDirectory()) {
                    FilenameFilter select = new FileListFilter(exts);
                    File[] files = inputFile.listFiles(select);
                    java.util.List fileList = java.util.Arrays.asList(files);
                    for (java.util.Iterator itr = fileList.iterator(); itr.hasNext();) {
                        File f = (File) itr.next();
                        inputFiles.add((File) f);
                    }
                } else {
                    String fExt = inputFile.getAbsolutePath().substring(inputFile.getAbsolutePath().lastIndexOf(".") + 1);
                    for (String ext : exts) {
                        if (ext.equals(fExt)) {
                            inputFiles.add(inputFile);
                            break;
                        }
                    }
                }
                break;
            }
        }
        if (inputFiles.isEmpty()) {
            throw new Exception("No input files given.");
        }
    }

    private static void processImageFile(File inFile, File outputDir) throws IOException {
        if (verboseMode) {
            System.out.printf("Processing image: %s\n", inFile);
        }
        BufferedImage image = loadImage(inFile);

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int numTiers = numTiers(originalWidth, originalHeight);
        if (verboseMode) {
            System.out.printf("nTiers=%d\n", numTiers);
        }
        // Delete any existing output files and folders for this image
        File descriptor = new File(outputDir + File.separator + "ImageProperties.xml");
        if (descriptor.exists()) {
            if (verboseMode) {
                System.out.printf("Deleting descriptor: %s\n", descriptor);
            }
            deleteFile(descriptor);
        }

        File imgDir = null;
        for (int i = 0; i < 100; i++) {
            imgDir = new File(outputDir + File.separator + "TileGroup" + i);
            if (imgDir.exists()) {
                if (verboseMode) {
                    System.out.printf("Deleting directory: %s\n", imgDir);
                }
                deleteDir(imgDir);
            }
        }
        imgDir = outputDir;
        System.out.printf("Writing into directory: %s\n", imgDir);

        double width = originalWidth;
        double height = originalHeight;

        final int totalTiles = numTilesTotal(originalWidth, originalHeight);
        int currentTileGroup = (int) Math.ceil((double) totalTiles / 256d) - 1;
        int totalTilesCounter = totalTiles;
        File dir = createDir(imgDir, "TileGroup" + Integer.toString(currentTileGroup));
        for (int currentTier = numTiers; currentTier >= 0; currentTier--) {
            int nCols = (int) Math.ceil(width / tileSize);
            int nRows = (int) Math.ceil(height / tileSize);

            for (int row = nRows - 1; row >= 0; row--) {
                for (int col = nCols - 1; col >= 0; col--) {
                    saveImageAtQuality(getTile(image, row, col), dir + File.separator + currentTier + '-' + col + '-' + row, quality);
                    totalTilesCounter--;
                    if (totalTilesCounter > 0 && totalTilesCounter % 256 == 0) {
                        currentTileGroup--;
                        dir = createDir(imgDir, "TileGroup" + Integer.toString(currentTileGroup));
                    }
                }
            }

            if (currentTier == 1 && zeroTileSize > 0) {
                // scale for zero tile
                if (width > height) {
                    height = (int) Math.floor((double) (zeroTileSize * height) / (double) width);
                    width = zeroTileSize;
                } else if (width < height) {
                    width = (int) Math.floor((double) (zeroTileSize * width) / (double) height);
                    height = zeroTileSize;
                } else {
                    width = height = zeroTileSize;
                }
            } else {
                // Scale down image for next level
                width = Math.floor(width / 2d);
                height = Math.floor(height / 2d);
            }

            if (width > 10 && height > 10) {
                // resize in stages to improve quality
                image = resizeImage(image, width * 1.66d, height * 1.66d);
                image = resizeImage(image, width * 1.33d, height * 1.33d);
            }
            image = resizeImage(image, width, height);

            if (currentTier == 1 && zeroTileSize > 0) {
                saveImageAtQuality(getTile(image, 0, 0), dir + File.separator + 0 + '-' + 0 + '-' + 0, quality);
                break;
            }
        }
        saveImageDescriptor(originalWidth, originalHeight, totalTiles, descriptor);
    }

    private static int numTilesTotal(int originalWidth, int originalHeight) {
        int result = 0;
        int numTiers = numTiers(originalWidth, originalHeight);
        for (int i = numTiers; i >= 0; i--) {
            result += numTilesFromTier(originalWidth, originalHeight, i);
        }
        return result;
    }

    private static int numTilesFromTier(int originalWidth, int originalHeight, int tier) {
        Point2D sizeAtTier = sizeAtTier(originalWidth, originalHeight, tier);
        return ((int) Math.ceil(sizeAtTier.getX() / tileSize) * (int) Math.ceil(sizeAtTier.getY() / tileSize));
    }

    private static Point2D sizeAtTier(int originalWidth, int originalHeight, int tier) {
        double width = originalWidth;
        double height = originalHeight;
        int tiers = numTiers(originalWidth, originalHeight);
        for (int i = 0; i < (tiers - tier); i++) {
            width = Math.floor(width / 2d);
            height = Math.floor(height / 2d);
        }
        return new Point((int) width, (int) height);
    }

    /**
     * follows convention when "0" means 1 tier(0)
     * "1" means 2 tiers(0 and 1) and so on
     */
    private static int numTiers(int originalWidth, int originalHeight) {
        int result = 0;
        double size = Math.max(originalWidth, originalHeight);
        while (size > tileSize) {
            size = Math.floor(size / 2d);
            result++;
        }
        return result;
    }

    private static void deleteFile(File file) throws IOException {
        if (!file.delete()) {
            throw new IOException("Failed to delete file: " + file);
        }
    }

    private static void deleteDir(File dir) throws IOException {
        if (!dir.isDirectory()) {
            deleteFile(dir);
        } else {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    deleteFile(file);
                }
            }
            if (!dir.delete()) {
                throw new IOException("Failed to delete directory: " + dir);
            }
        }
    }

    private static File createDir(File parent, String name) throws IOException {
        assert (parent.isDirectory());
        File result = new File(parent + File.separator + name);
        if (!(result.exists() || result.mkdir())) {
            throw new IOException("Unable to create directory: " + result);
        }
        return result;
    }

    private static BufferedImage loadImage(File file) throws IOException {
        FileSeekableStream stream = null;
        BufferedImage result = null;
        try {
            stream = new FileSeekableStream(file);
            PlanarImage planarImage = JAI.create("stream", stream);
            //PlanarImage planarImage = JAI.create("fileload", file.getAbsolutePath());
            result = planarImage.getAsBufferedImage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Cannot read image file: " + file.getAbsolutePath());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return result;
    }

    private static BufferedImage getTile(BufferedImage img, int row, int col) {
        int x = col * tileSize;
        int y = row * tileSize;
        int w = tileSize;
        int h = tileSize;
        if (x + w > img.getWidth()) {
            w = img.getWidth() - x;
        }
        if (y + h > img.getHeight()) {
            h = img.getHeight() - y;
        }

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(img, 0, 0, w, h, x, y, x + w, y + h, null);

        return result;
    }

    private static BufferedImage resizeImage(BufferedImage img, double width, double height) {
        int w = (int) width;
        int h = (int) height;
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(), null);
        //also: http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html        
        //surprisingly following code gives worse results
        //RenderingHints qualityHints = new RenderingHints(
        //    RenderingHints.KEY_RENDERING,
        //    RenderingHints.VALUE_RENDER_QUALITY);
        //BufferedImage result = JAI.create("SubsampleAverage",
        //  img, width / (double) img.getWidth(),
        //  height / (double) img.getHeight(),
        //  qualityHints).getAsBufferedImage();
        return result;
    }

    private static void saveImageAtQuality(BufferedImage img, String path, float quality) throws IOException {
        File outputFile = new File(path + ".jpg");
        Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = (ImageWriter) iter.next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(quality);
        FileImageOutputStream output = new FileImageOutputStream(outputFile);
        writer.setOutput(output);
        IIOImage image = new IIOImage(img, null, null);
        try {
            writer.write(null, image, iwp);
        } catch (IOException e) {
            throw new IOException("Unable to save image file: " + outputFile);
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (output != null) {
                output.close();
            }
        }
    }

    private static void saveImageDescriptor(int width, int height, int numTiles, File file) throws IOException {
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        ArrayList<String> lines = new ArrayList<String>();
        lines.add(xmlHeader);
        lines.add("<IMAGE_PROPERTIES WIDTH=\"" + width
                + "\" HEIGHT=\"" + height
                + "\" NUMTILES=\"" + numTiles
                + "\" NUMIMAGES=\"1\" VERSION=\"1.8\""
                + " TILESIZE=\"" + tileSize + "\"/>");
        saveText(lines, file);
    }

    private static void saveText(ArrayList lines, File file) throws IOException {
        if (verboseMode) {
            System.out.printf("Writing file: %s\n", file);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            for (int i = 0; i < lines.size(); i++) {
                ps.println((String) lines.get(i));
            }
        } catch (IOException e) {
            throw new IOException("Unable to write to text file: " + file);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}

class FileListFilter implements FilenameFilter {

    private ArrayList<String> extensions;

    public FileListFilter(ArrayList<String> extensions) {
        this.extensions = extensions;
    }

    @Override
    public boolean accept(File directory, String filename) {
        if (extensions != null) {
            Iterator<String> itr = extensions.iterator();
            while (itr.hasNext()) {
                String ext = (String) itr.next();
                if (filename.toLowerCase().endsWith('.' + ext)) {
                    return true;
                }
            }
        }
        return false;
    }
}
