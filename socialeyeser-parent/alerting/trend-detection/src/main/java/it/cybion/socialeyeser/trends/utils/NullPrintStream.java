package it.cybion.socialeyeser.trends.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import com.google.common.io.ByteStreams;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class NullPrintStream extends PrintStream {
    
    public NullPrintStream() throws FileNotFoundException {
    
        super(ByteStreams.nullOutputStream());
    }
    
    @Override
    public void print(String s) {
    
    }
    
}
