/* Copyright (c) 2017 Lancaster Software & Service
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
/* ------------------------------------------------------------------------ */

package lss.bef.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

public class HashUtil {
    public static String hashFileContentsToString( String path )
    {
        String strRet = null;
        DigestInputStream in = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            in = new DigestInputStream(new FileInputStream( path ), md );
            byte[] buffer = new byte[8192];

            while( in.read(buffer) != -1 );

            in.close();
            in = null;

            byte[]  hashBytes = md.digest();

            strRet = Base64.getEncoder().encodeToString( hashBytes );
        }

        catch( FileNotFoundException ex )
        {
 //           Do.logException( "Exception (FileNotFoundException) on hashFileContentsToString[" + path + "] Message=" + ex.getMessage() );
        }
        catch( IOException ex )
        {
   //         Do.logException( "Exception (IOException) on hashFileContentsToString[" + path + "] Message=" + ex.getMessage() );
        }
        catch( NoSuchAlgorithmException ex )
        {
     //       Do.logException( "Exception (NoSuchAlgorithmException) on hashFileContentsToString[" + path + "] Message=" + ex.getMessage() );
        }
        return strRet;
    }

    public static int hashToInt( String key )
    {
        int hashNumber = 0;

        byte[] keyStr = key.getBytes();

        for( int counter=0; counter < keyStr.length; counter++ )
        {
            hashNumber = (hashNumber << 4) + keyStr[ counter ];
            int temp = hashNumber & 0x70000000;

            if( temp != 0 )
            {
                hashNumber ^= temp >> 24;
            }
            hashNumber &= ~temp;
        }

        // The idea is to make the hash code even more strong - Combining the two methods
        if( key.length() > 32 )
            hashNumber ^= key.hashCode();

        if( hashNumber < 0 )
        {
            hashNumber *= (-1);
        }

        return hashNumber;
    }

    public static long hashToLong( String key )
    {
        long hashNumber = 0;

        byte[] keyStr = key.getBytes();

        for( int counter=0; counter < keyStr.length; counter++ )
        {
            hashNumber = (hashNumber << 4) + keyStr[ counter ];
            long temp = hashNumber & 0x7000000000000000L;

            if( temp != 0 )
            {
                hashNumber ^= temp >> 56;
            }
            hashNumber &= ~temp;
        }

        // The idea is to make the hash code even more strong - Combining the two methods
        if( key.length() > 64 )
            hashNumber ^= key.hashCode();

        if( hashNumber < 0 )
        {
            hashNumber *= (-1);
        }

        return hashNumber;
    }
}
