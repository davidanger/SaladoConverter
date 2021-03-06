package equirectangulartocubic;
/*
Copyright 2009 Zephyr Renner
Modified 2010 by Marek Standio
This file is part of EquirectangulartoCubic.java.
EquirectangulartoCubic is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
EquirectangulartoCubic is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with EquirectangulartoCubic. If not, see http://www.gnu.org/licenses/.
The Equi2Rect.java library is modified from PTViewer 2.8 licenced under the GPL courtesy of Fulvio Senore, originally developed by Helmut Dersch.  Thank you both!
Some basic structural of this code are influenced by / modified from DeepJZoom, Glenn Lawrence, which is also licensed under the GPL.
 */

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.media.jai.JAI;

/**
 *
 * @author Zephyr Renner
 */
public class EquirectangularToCubic {

    static final String help = "\nEquirectangularToCubic v1.4\n\nUsage:\n\n" + "java [-java_options] -jar path/to/EquirectangularToCubic.jar [-options]\n"
            + "[args...] For a list of java options try: java -help or java -X for a\n"
            + "list of less comon options. Loading large images for conversion takes a\n"
            + "lot of RAM so you will find the -Xmx option useful to raise Java's maximum\n"
            + "heap size. The -Xmx command is followed immediately by an integer specifying\n"
            + "RAM size and a unit indicator. For example, -Xmx1024m means to use 1024\n"
            + "megabytes.If you see an error about heapsize, then you will need to increase\n"
            + "this value. \n\n" + " Basic usage example for the jar file:\n\n"
            + "java -Xmx1024m -jar path/to/EquirectangularToCubic.jar \n"
            + "path/to/directory/of/images/ This will generate a folder of converted files \n"
            + "beside the input directory or file with 'cubic_' prepended onto the name.\n"
            + "So in the basic example above, the output files would be in \n"
            + "path/to/directory/of/cubic_images/. \n\n" + " Options:\n\n"
            + "-overlap: number of pixels of overlap added around the cubefaces. A value\n"
            + "\tof 1 causes the cube faces to be 90 degrees wide in pixels (dependent\n"
            + "\ton the size of the input equirectangular) plus 1 pixel. \n"
            + "\tDefault is 1. \n\n"
            + "-interpolation: possible values are: lanczos2, bilinear, nearest-neighbor.\n"
            + "\tSets the interpolation algorithm to use during remapping. Lanczos2 \n"
            + "\tand bilinear are the highest quality. Nearest-neighbor is faster \n"
            + "\tand generally lower quality, although it preserves sharp edges better. \n"
            + "\tDefault is lanczos2. \n\n"
            + "-outputdir or -o: the output directory for the converted images. It need\n"
            + "\tnot exist. Default is a folder next to the input folder or file, with \n"
            + "\t'cubic_' prepended to the name of the input (input files will have the \n"
            + "\textension removed).\n\n"
            + "-verbose or -v: makes the utility more 'chatty' during processing. \n\n"
            + "-debug: print various debugging messages during processing. \n\n"
            + " Arguments:\n\n"
            + "The arguments following any options are the input images or folders.\n"
            + "(or both) If there are multiple input folders or images, each should\n"
            + "be separated by a space. Input folders will not be NOT be recursed.\n"
            + "Only images immediately inside the folder will be processed. All\n"
            + "inputs will be processed into the one output directory, so general\n"
            + "usage is to process one folder containing multiples images \n"
            + "or to process one singe image file.\n\n"
            + "Happy converting!  And next time you see Helmut Dersch,\n"
            + "Fulvio Senore, and other contributors to PTViewer, say thanks!\n";
    static final String LANCZOS2 = "lanczos2";
    static final String BILINEAR = "bilinear";
    static final String NEAREST_NEIGHBOUR = "nearest-neighbor";

    private enum CmdParseState {

        DEFAULT, OUTPUTDIR, OVERLAP, INPUTFILE, INTERPOLATION, QUALITY, RESIZE
    }
    // The following can be overriden/set by the indicated command line arguments    
    static boolean showHelp = false;            // -help | -h
    static String interpolation = LANCZOS2;     // -interpolation (lanczos2, bilinear, nearest-neighbor)
    static int overlap = 1;           	        // -overlap
    static File outputDir = null;               // -outputdir | -o
    static boolean simpleoutput = false;        // -simpleoutput | -s
    static boolean verboseMode = false;         // -verbose
    static boolean debugMode = false;           // -debug
    static ArrayList<File> inputFiles = new ArrayList<File>();  // must follow all other args
    static ArrayList<File> outputFiles = new ArrayList<File>();
    static ImageBuffer imgBuf;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            try {
                parseCommandLine(args);
                if (showHelp) {
                    System.out.println(help);
                    return;
                }
                //when output file is given, output are folders with names of input file in that directory
                if (outputFiles.size() == 1) {
                    outputDir = outputFiles.get(0);
                    outputFiles.clear();

                    if (!outputDir.exists() || !outputDir.isDirectory()) {
                        if (!outputDir.mkdir()) {
                            throw new IOException("Unable to create directory: " + outputDir);
                        }
                    }

                    Iterator<File> itr = inputFiles.iterator();
                    while (itr.hasNext()) {
                        File inputFile = itr.next();
                        String fileName = inputFile.getName();
                        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                        if (simpleoutput) {
                            outputFiles.add(outputDir);
                        } else {
                            File outputFile = createDir(outputDir, "cubic_" + nameWithoutExtension);
                            outputFiles.add(outputFile);
                        }
                    }
                }
                // default location for output files is folder beside input files with the name of the input file
                if (outputFiles.isEmpty()) {
                    Iterator<File> itr = inputFiles.iterator();
                    while (itr.hasNext()) {
                        File inputFile = itr.next();
                        File parentFile = inputFile.getAbsoluteFile().getParentFile();
                        String fileName = inputFile.getName();
                        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                        if (simpleoutput) {
                            outputFiles.add(parentFile);
                        } else {
                            File outputFile = createDir(parentFile, "cubic_" + nameWithoutExtension);
                            outputFiles.add(outputFile);
                        }
                    }
                }
                if (debugMode) {
                    System.out.printf("overlap=%d ", overlap);
                    System.out.printf("interpolation=%s ", interpolation);
                }

            } catch (Exception e) {
                System.out.println("Invalid command: " + e.getMessage());
                System.out.println("type -h to get list of supported commands");
                return;
            }

            Equi2Rect.init();
            System.setProperty("com.sun.media.jai.disableMediaLib", "true");
            // can be problematic in non-admin accounts
            // java -Dcom.sun.media.jai.disableMediaLib=true YourApp
            for (int i = 0; i < inputFiles.size(); i++) {
                processImageFile(inputFiles.get(i), outputFiles.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            if (imgBuf != null) {
                try {
                    imgBuf.close();
                } catch (IOException ex) {
                    System.out.printf("Could not close image stream.");
                }
            }
        }
    }

    /**
     * Process the command line arguments
     * @param args the command line arguments
     */
    private static void parseCommandLine(String[] args) throws Exception {
        CmdParseState state = CmdParseState.DEFAULT;
        for (int count = 0; count < args.length; count++) {
            String arg = args[count];
            switch (state) {
                case DEFAULT:
                    if (arg.equals("-help") || arg.equals("-h")) {
                        showHelp = true;
                    } else if (arg.equals("-verbose")) {
                        verboseMode = true;
                    } else if (arg.equals("-debug")) {
                        verboseMode = true;
                        debugMode = true;
                    } else if (arg.equals("-simpleoutput") || arg.equals("-s")) {
                        simpleoutput = true;
                    } else if (arg.equals("-outputdir") || arg.equals("-o")) {
                        state = CmdParseState.OUTPUTDIR;
                    } else if (arg.equals("-overlap")) {
                        state = CmdParseState.OVERLAP;
                    } else if (arg.equals("-interpolation")) {
                        state = CmdParseState.INTERPOLATION;
                    } else {
                        state = CmdParseState.INPUTFILE;
                    }
                    break;
                case OUTPUTDIR:
                    outputFiles.add(new File(args[count]));
                    state = CmdParseState.DEFAULT;
                    break;

                case OVERLAP:
                    overlap = Integer.parseInt(args[count]);
                    state = CmdParseState.DEFAULT;
                    break;

                case INTERPOLATION:
                    interpolation = args[count];
                    state = CmdParseState.DEFAULT;
                    break;

            }

            if (state == CmdParseState.INPUTFILE) {
                File inputFile = new File(arg);
                if (!inputFile.exists()) {
                    throw new FileNotFoundException("input file not found: " + inputFile.getPath());
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
            }
        }
        if (inputFiles.isEmpty() && !showHelp) {
            throw new Exception("No input files given");
        }
    }

    /**
     * Process the given equirectangular image file, producing its cubic output files
     * in a subdirectory of the given output directory.
     * @param inFile the file containing the image
     * @param outputDir the output directory
     */
    private static void processImageFile(File inFile, File outputDir) throws IOException {
        if (verboseMode) {
            System.out.printf("Processing image: %s\n", inFile);
        }

        String fileName = inFile.getName();
        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));

        imgBuf = new ImageBuffer(inFile, verboseMode);

        int equiWidth = imgBuf.getPanoWidth();
        int equiHeight = imgBuf.getPanoHeight();

        if (equiWidth != equiHeight * 2) {
            if (verboseMode) {
                System.out.println("Image is not equirectangular (" + equiWidth + " x " + equiHeight + "), skipping...");
            }
            return;
        }

        double fov; // horizontal field of view        
        double r = equiWidth / (2D * Math.PI);
        double y = (Math.tan(Math.PI / 4D) * r + overlap);
        fov = Math.atan(y / r) * 180 / Math.PI * 2;
        int rectSize = (int) (y * 2);

        Boolean bilinear;
        Boolean lanczos2;

        if (interpolation.equals(LANCZOS2)) {
            lanczos2 = true;
            bilinear = false;
        } else if (interpolation.equals(BILINEAR)) {
            lanczos2 = false;
            bilinear = true;
        } else if (interpolation.equals(NEAREST_NEIGHBOUR)) {
            lanczos2 = false;
            bilinear = false;
        } else {
            lanczos2 = true;
            bilinear = false;
        } // lanczos2 is default for junk values.

        int rectData[] = new int[rectSize * rectSize]; // this one soaks lots of ram
        double pitch;
        double yaw;
        BufferedImage output;

        Equi2Rect.initForNewBuffer(imgBuf);

        output = new BufferedImage(rectSize, rectSize, BufferedImage.TYPE_INT_RGB);


        yaw = 0;
        pitch = 0;
        imgBuf.init(yaw, pitch);
        Equi2Rect.extractRectilinear(yaw, pitch, fov, imgBuf, rectSize, equiWidth, bilinear, lanczos2, rectData);
        imgBuf.reset();
        output.setRGB(0, 0, rectSize, rectSize, rectData, 0, rectSize);
        saveImage(output, outputDir + File.separator + nameWithoutExtension + "_f");
        output.flush();
        Arrays.fill(rectData, 0);
        imgBuf.printStats();

        yaw = 90;
        pitch = 0;
        imgBuf.init(yaw, pitch);
        Equi2Rect.extractRectilinear(yaw, pitch, fov, imgBuf, rectSize, equiWidth, bilinear, lanczos2, rectData);
        imgBuf.reset();
        output.setRGB(0, 0, rectSize, rectSize, rectData, 0, rectSize);
        saveImage(output, outputDir + File.separator + nameWithoutExtension + "_r");
        output.flush();
        Arrays.fill(rectData, 0);
        imgBuf.printStats();

        yaw = 180;
        pitch = 0;
        imgBuf.init(yaw, pitch);
        Equi2Rect.extractRectilinear(yaw, pitch, fov, imgBuf, rectSize, equiWidth, bilinear, lanczos2, rectData);
        imgBuf.reset();
        output.setRGB(0, 0, rectSize, rectSize, rectData, 0, rectSize);
        saveImage(output, outputDir + File.separator + nameWithoutExtension + "_b");
        output.flush();
        Arrays.fill(rectData, 0);
        imgBuf.printStats();

        yaw = 270;
        pitch = 0;
        imgBuf.init(yaw, pitch);
        Equi2Rect.extractRectilinear(yaw, pitch, fov, imgBuf, rectSize, equiWidth, bilinear, lanczos2, rectData);
        imgBuf.reset();
        output.setRGB(0, 0, rectSize, rectSize, rectData, 0, rectSize);
        saveImage(output, outputDir + File.separator + nameWithoutExtension + "_l");
        output.flush();
        Arrays.fill(rectData, 0);
        imgBuf.printStats();

        yaw = 0;
        pitch = 90;
        imgBuf.init(yaw, pitch);
        Equi2Rect.extractRectilinear(yaw, pitch, fov, imgBuf, rectSize, equiWidth, bilinear, lanczos2, rectData);
        imgBuf.reset();
        output.setRGB(0, 0, rectSize, rectSize, rectData, 0, rectSize);
        saveImage(output, outputDir + File.separator + nameWithoutExtension + "_u");
        output.flush();
        Arrays.fill(rectData, 0);
        imgBuf.printStats();

        yaw = 0;
        pitch = -90;
        imgBuf.init(yaw, pitch);
        Equi2Rect.extractRectilinear(yaw, pitch, fov, imgBuf, rectSize, equiWidth, bilinear, lanczos2, rectData);
        imgBuf.reset();
        output.setRGB(0, 0, rectSize, rectSize, rectData, 0, rectSize);
        saveImage(output, outputDir + File.separator + nameWithoutExtension + "_d");
        output.flush();
        imgBuf.printStats();
    }

    /**
     * Creates a directory
     * @param parent the parent directory for the new directory
     * @param name the new directory name
     */
    private static File createDir(File parent, String name) throws IOException {
        assert (parent.isDirectory());
        File result = new File(parent + File.separator + name);
        if (!(result.exists() || result.mkdir())) {
            throw new IOException("Unable to create directory: " + result);
        }
        return result;
    }

    /**
     * Saves image to the given file
     * @param img the image to be saved
     * @param path the path of the file to which it is saved (less the extension)     
     */
    private static void saveImage(BufferedImage img, String path) throws IOException {
        if (verboseMode) {
            System.out.print("Saving image...");
        }
        JAI.create("filestore", img, path + ".tif", "TIFF");
        if (verboseMode) {
            System.out.print("Created: " + path + ".tif");
        }
    }
}

class FileListFilter implements FilenameFilter {

    private ArrayList<String> extensions;

    public FileListFilter(ArrayList<String> extensions) {
        this.extensions = extensions;
    }

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
