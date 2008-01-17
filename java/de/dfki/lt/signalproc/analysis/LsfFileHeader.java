/**
 * Copyright 2007 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * Permission is hereby granted, free of charge, to use and distribute
 * this software and its documentation without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of this work, and to
 * permit persons to whom this work is furnished to do so, subject to
 * the following conditions:
 * 
 * 1. The code must retain the above copyright notice, this list of
 *    conditions and the following disclaimer.
 * 2. Any modifications must be clearly marked as such.
 * 3. Original authors' names are not deleted.
 * 4. The authors' names are not used to endorse or promote products
 *    derived from this software without specific prior written
 *    permission.
 *
 * DFKI GMBH AND THE CONTRIBUTORS TO THIS WORK DISCLAIM ALL WARRANTIES WITH
 * REGARD TO THIS SOFTWARE, INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS, IN NO EVENT SHALL DFKI GMBH NOR THE
 * CONTRIBUTORS BE LIABLE FOR ANY SPECIAL, INDIRECT OR CONSEQUENTIAL
 * DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR
 * PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS
 * ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */

package de.dfki.lt.signalproc.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import de.dfki.lt.signalproc.util.LEDataInputStream;
import de.dfki.lt.signalproc.util.LEDataOutputStream;
import de.dfki.lt.signalproc.window.Window;

/**
 * @author oytun.turk
 *
 */
public class LsfFileHeader {
    public int numfrm; //Total number of frames
    public int lpOrder; //Linear prediction order
    public float preCoef; //Preemphasis coefficient
    public float winsize; //Analysis window size in seconds
    public float skipsize; //Analysis skip size in seconds
    public int samplingRate; //Sampling rate in Hz
    public int windowType; //Type of analysis window (See class de.dfki.lt.signalproc.window.Window for details
    
    public LsfFileHeader()
    {
        numfrm = 0;
        lpOrder = 0;
        preCoef = 0.0f;
        winsize = 0.020f;
        skipsize = 0.010f;
        samplingRate = 0;
        windowType = Window.HAMMING;
    }
    
    public LsfFileHeader(String lsfFile)
    {
        try {
            readLsfHeader(lsfFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void readLsfHeader(String lsfFile) throws IOException
    {
        readLsfHeader(lsfFile, false);
    }
    
    public LEDataInputStream readLsfHeader(String lsfFile, boolean bLeaveStreamOpen) throws IOException
    {
        LEDataInputStream stream = new LEDataInputStream(lsfFile);

        if (stream!=null)
        {
            this.numfrm = stream.readInt();
            this.lpOrder = stream.readInt();
            this.preCoef = stream.readFloat();
            this.winsize = stream.readFloat();
            this.skipsize = stream.readFloat();
            this.samplingRate = stream.readInt();
            this.windowType = stream.readInt();
            
            if (!bLeaveStreamOpen)
            {
                stream.close();
                stream = null;
            }
        }
        
        return stream;
    }
    
    public void writeLsfHeader(String lsfFile) throws IOException
    {
        writeLsfHeader(lsfFile, false);
    }
    
    //This version returns the file output stream for further use, i.e. if you want to write additional information
    // in the file use this version
    public LEDataOutputStream writeLsfHeader(String lsfFile, boolean bLeaveStreamOpen) throws IOException
    {
        LEDataOutputStream stream = new LEDataOutputStream(lsfFile);

        if (stream!=null)
        {
            stream.writeInt(this.numfrm);
            stream.writeInt(this.lpOrder);
            stream.writeFloat(this.preCoef);
            stream.writeFloat(this.winsize);
            stream.writeFloat(this.skipsize);
            stream.writeInt(this.samplingRate);
            stream.writeInt(this.windowType);
            
            if (!bLeaveStreamOpen)
            {
                stream.close();
                stream = null;
            }
        }
        
        return stream;
    }
    
}
