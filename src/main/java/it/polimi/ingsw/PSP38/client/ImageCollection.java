package it.polimi.ingsw.PSP38.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import javax.imageio.ImageIO;

/**
 * Immutable class that represents a collection of images.
 *
 * @author Maximilien Groh (10683107)
 */

public final class ImageCollection {
    public final Map<Byte, Image> dirImages;

    /**
     * Uses the given string to locate a directory and constructs an image
     * collection with the images that are found in it. The images are
     * associated with the 2-digit number found at the beginning of their name.
     * If files fail to be read, they are simply ignored.
     *
     * @param dirName The name of the directory.
     */

    public ImageCollection(String dirName) {
        Map<Byte, Image> images = new HashMap<>();
        try {
            File dir = new File(Objects.requireNonNull(getClass().getResource(dirName)).toURI());
            List<File> dirFiles = new LinkedList<>(
                    Arrays.asList(Objects.requireNonNull(dir.listFiles())));

            for (File file : dirFiles) {
                String fileName = file.getName();
                try {
                    byte imageNumber = (byte) Integer
                            .parseInt(fileName.substring(0, 2));
                    images.put(imageNumber, ImageIO.read(file));
                } catch (IOException | NumberFormatException
                        | IndexOutOfBoundsException e) {
                    // ignore the file
                }
            }
        } catch (URISyntaxException ignored) {

        }
        dirImages = Map.copyOf(images);
    }

    /**
     * Returns the image associated with the given number.
     *
     * @param imageNumber The image number.
     * @return the image associated with the given number.
     * @throws NoSuchElementException if the given number does not correspond to an image.
     */

    public Image image(byte imageNumber) throws NoSuchElementException {
        if (!dirImages.containsKey(imageNumber)) {
            throw new NoSuchElementException(
                    "The given number does not correspond to an image of the collection.");
        }
        return dirImages.get(imageNumber);
    }
}
