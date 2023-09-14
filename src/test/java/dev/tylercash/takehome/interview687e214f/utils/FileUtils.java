package dev.tylercash.takehome.interview687e214f.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class FileUtils {
    public static File getResourcesFile(String filename){
        try {
            return new File(Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
