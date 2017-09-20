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

package lss.bef.core;

import lss.bef.core.Identity;

import java.util.Random;

import lss.bef.core.util.Base36Util;
import lss.bef.core.util.HashUtil;

public class IdentityImpl {
    private static Random 	discriminator = new Random();

    public static String getSignatureIdForFile( String path ) {
        return HashUtil.hashFileContentsToString( path );
    }

    public static long getUniqueId() {
        long id = discriminator.nextLong();

        if( id < 0 )
            id = id * (-1);

        return id;
    }

    public static String getUniqueId( String prefix ) {
        return prefix.trim() + getUniqueId();
    }

    public static String getUniqueId( String prefix, String suffix ) {
        return getUniqueId( prefix ) + suffix;
    }

    public static String getUniqueStrFromId( long id ) {
        return Base36Util.base10ToBase36( id );
    }

    public static long getUniqueIdFromHash( String value ) {
        return HashUtil.hashToLong( value );
    }

    public static long getUniqueIdFromStr( String value ) {
        return Base36Util.base36ToBase10Long( value );
    }

    public static int getWeakUniqueId() {
        int id = discriminator.nextInt();

        if( id < 0 )
            id = id * (-1);

        return id;
    }

    public static String getWeakUniqueId( String prefix ) {
        return prefix.trim() + getWeakUniqueId();
    }

    public static String getWeakUniqueId( String prefix, String suffix ) {
        return getWeakUniqueId( prefix ) + suffix.trim();
    }

    public static String getWeakUniqueStrFromId( int id ) {
        return Base36Util.base10ToBase36( id );
    }

    public static int getWeakUniqueIdFromHash( String value ) {
        return HashUtil.hashToInt( value );
    }

    public static int getWeakUniqueIdFromStr( String value ) {
        return Base36Util.base36ToBase10Int( value );
    }

}
