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

package lss.bef.core

import java.io.PrintStream;


class LogDefines {
    companion object {
        // LOG LEVELS
        const val ALL : Int        = Int.MIN_VALUE;		//Level.ALL.intValue();
        const val EXCEPTION : Int = 1100;					//Level.SEVERE.intValue() + 100;
        const val ERROR : Int     =  1000;				//Level.SEVERE.intValue();
        const val WARNING : Int   =  900;					//Level.WARNING.intValue();
        const val INFO : Int      =  800;					//Level.INFO.intValue();
        const val CONFIG : Int    =  700;					//Level.CONFIG.intValue();
        const val DEBUG : Int     =  500;					//Level.FINE.intValue();
        const val PERFORMANCE : Int = 400;				//Level.FINER.intValue();
        const val VERBOSE : Int   =  300;					//Level.FINEST.intValue();
        const val OFF : Int       =  Integer.MAX_VALUE;  //Level.OFF.intValue();
    }
}

interface Log {
    fun attachFileHandler( filenamePattern : String, maxFileSize : Int, numberOfRotatingFiles : Int, append : Boolean ) : Boolean
    fun attachFileHandlerFor( loggerName : String, filename : String ) : Boolean

    fun detachFileHandlerFor( loggerName : String ) : Boolean

    fun getCurrentGlobalLogLevel() : Int
    fun	setCurrentGlobalLogLevel( level : Int ) : Unit

    fun getOriginalStderr() : PrintStream
    fun getOriginalStdout() : PrintStream

    fun logEnableLevel( level : Int ) : Unit
    fun logEnableTargetDisplay( flag : Boolean ) : Unit

    fun logIsLevelEnabled( level : Int ) : Boolean;

    fun log( level : Int, msg : String ) : Unit
    fun log( level : Int, format : String, vararg vars : Object ) : Unit
    fun log( level : Int, resourceId : Long, vararg vars : Object ) : Unit

    fun redirectStderrToLog() : Unit
    fun redirectStdoutToLog() : Unit

    fun resetStderrToOriginal() : Unit
    fun resetStdoutToOriginal() : Unit


    // CONVENIENCE METHODS
    fun logException( msg : String ) : Unit;
    fun logException( format : String, vararg vars : Object ) : Unit
    fun logException( resourceId : Long, vararg vars : Object ) : Unit

    fun logError( msg : String ) : Unit
    fun logError( format : String, vararg vars : Object ) : Unit
    fun logError( resourceId : Long, vararg vars : Object ) : Unit

    fun logWarning( msg : String ) : Unit
    fun logWarning( format : String, vararg vars : Object ) : Unit
    fun logWarning( resourceId : Long, vararg vars : Object ) : Unit

    fun logInfo( msg : String ) : Unit
    fun logInfo( format : String, vararg vars : Object ) : Unit
    fun logInfo( resourceId : Long, vararg vars : Object ) : Unit

    fun logDebug( msg : String ) : Unit
    fun logDebug( format : String, vararg vars : Object ) : Unit
    fun logDebug( resourceId : Long, vararg vars : Object ) : Unit

    fun logPerformance( msg : String ) : Long
    fun logPerformance( format : String, vararg vars : Object ) : Long
    fun logPerformance( resourceId : Long, vararg vars : Object ) : Long

    fun logPerformance( startTime : Long, msg : String ) : Unit
    fun logPerformance( startTime : Long, format : String, vararg vars : Object ) : Unit
    fun logPerformance( startTime : Long, resourceId : Long, vararg vars : Object ) : Unit
}
