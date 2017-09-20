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

import java.io.PrintStream
import kotlin.reflect.KClass

import lss.bef.core.Environment as EnvironmentInterface
import lss.bef.core.Factory as FactoryInterface
import lss.bef.core.Identity as IdentityInterface
import lss.bef.core.Log as LogInterface

import lss.bef.core.IdentityImpl;
import lss.bef.core.FactoryImp;

// initialize global objects
val logger : LogInterface = LogImpl();
val factory : FactoryInterface = FactoryImp();

class Do {

    companion object Execute : FactoryInterface, EnvironmentInterface, IdentityInterface, LogInterface {

/*        override fun classObj( componentName : String ) : KClass<Any> {

        }

        override fun instance( cls : KClass<Any>) : kotlin.Any {

        }
*/

        override fun instance( componentName : String ) : kotlin.Any {
            return factory.instance( componentName );
        }

/*
        override fun instance(cls : KClass<Any>, vararg objects : Object  ) : kotlin.Any {

        }


        override fun instance( componentName : String, vararg objects : Object ) : kotlin.Any {

        }
*/

        // IDENTITY Methods
        override fun getSignatureIdForFile( path : String): String {
            return IdentityImpl.getSignatureIdForFile( path )
        }

        override fun getUniqueId(): Long {
            return IdentityImpl.getUniqueId()
        }

        override fun getUniqueId(prefix: String): String {
            return IdentityImpl.getUniqueId( prefix )
        }

        override fun getUniqueId(prefix: String, suffix: String): String {
            return IdentityImpl.getUniqueId( prefix, suffix )
        }

        override  fun getUniqueStrFromId(id : Long): String {
            return IdentityImpl.getUniqueStrFromId( id )
        }

        override fun getUniqueIdFromHash( value : String): Long {
            return IdentityImpl.getUniqueIdFromHash( value )
        }

        override  fun getUniqueIdFromStr(value : String): Long {
            return IdentityImpl.getUniqueIdFromStr( value )
        }

        override fun getWeakUniqueId(): Int {
            return IdentityImpl.getWeakUniqueId()
        }

        override fun getWeakUniqueId(prefix: String): String {
            return IdentityImpl.getWeakUniqueId( prefix )
        }

        override fun getWeakUniqueId(prefix: String, suffix: String): String {
            return IdentityImpl.getWeakUniqueId( prefix, suffix )
        }

        override fun getWeakUniqueStrFromId(id : Int): String {
            return IdentityImpl.getWeakUniqueStrFromId( id )
        }

        override fun getWeakUniqueIdFromHash( value : String): Int {
            return IdentityImpl.getWeakUniqueIdFromHash( value )
        }

        override fun getWeakUniqueIdFromStr(value : String): Int {
            return IdentityImpl.getWeakUniqueIdFromStr( value )
        }

        // LOG METHODS
        override fun attachFileHandler( filenamePattern : String, maxFileSize : Int, numberOfRotatingFiles : Int, append : Boolean ) : Boolean {
            return logger.attachFileHandler( filenamePattern, maxFileSize, numberOfRotatingFiles, append )
        }

        override fun attachFileHandlerFor( loggerName : String, filename : String ) : Boolean {
            return logger.attachFileHandlerFor( loggerName, filename )
        }

        override fun detachFileHandlerFor( loggerName : String ) : Boolean {
            return logger.detachFileHandlerFor( loggerName )
        }

        override fun getCurrentGlobalLogLevel() : Int {
            return logger.getCurrentGlobalLogLevel()
        }

        override fun	setCurrentGlobalLogLevel( level : Int ) : Unit {
            return logger.setCurrentGlobalLogLevel( level )
        }

        override fun getOriginalStderr() : PrintStream {
            return logger.getOriginalStderr()
        }

        override fun getOriginalStdout() : PrintStream {
            return logger.getOriginalStdout()
        }

        override fun logEnableLevel( level : Int ) : Unit {
            logger.logEnableLevel( level )
        }

        override fun logEnableTargetDisplay( flag : Boolean ) : Unit {
            logger.logEnableTargetDisplay( flag )
        }

        override fun logIsLevelEnabled( level : Int ) : Boolean {
            return logger.logIsLevelEnabled( level )
        }

        override fun log( level : Int, msg : String ) : Unit {
            logger.log( level , msg )
        }

        override fun log( level : Int, format : String, vararg vars : Object ) : Unit {
            logger.log( level, format, *vars )
        }

        override fun log( level : Int, resourceId : Long, vararg vars : Object ) : Unit {
            logger.log( level, resourceId, *vars )
        }


        override fun redirectStderrToLog() : Unit {
            logger.redirectStderrToLog()
        }

        override fun redirectStdoutToLog() : Unit {
            logger.redirectStdoutToLog()
        }


        override fun resetStderrToOriginal() : Unit {
            logger.resetStderrToOriginal()
        }

        override fun resetStdoutToOriginal() : Unit {
            logger.resetStdoutToOriginal()
        }


        // CONVENIENCE METHODS
        override fun logException( msg : String ) : Unit {
            logger.logException( msg )
        }

        override fun logException( format : String, vararg vars : Object ) : Unit {
            logger.logException( format, *vars )
        }

        override fun logException( resourceId : Long, vararg vars : Object ) : Unit {
            logger.logException( resourceId, *vars )
        }


        override fun logError( msg : String ) : Unit {
            logger.logError( msg )
        }

        override fun logError( format : String, vararg vars : Object ) : Unit {
            logger.logError( format, *vars )
        }

        override fun logError( resourceId : Long, vararg vars : Object ) : Unit {
            logger.logError( resourceId, *vars )
        }


        override fun logWarning( msg : String ) : Unit {
            logger.logWarning( msg )
        }

        override fun logWarning( format : String, vararg vars : Object ) : Unit {
            logger.logWarning( format, *vars )
        }

        override fun logWarning( resourceId : Long, vararg vars : Object ) : Unit {
            logger.logWarning( resourceId, *vars )
        }


        override fun logInfo( msg : String ) : Unit {
            logger.logInfo( msg )
        }

        override fun logInfo( format : String, vararg vars : Object ) : Unit {
            logger.logInfo( format, *vars )
        }

        override fun logInfo( resourceId : Long, vararg vars : Object ) : Unit {
            logger.logInfo( resourceId, *vars )
        }


        override fun logDebug( msg : String ) : Unit {
            logger.logDebug( msg )
        }

        override fun logDebug( format : String, vararg vars : Object ) : Unit {
            logger.logDebug( format, *vars )
        }

        override fun logDebug( resourceId : Long, vararg vars : Object ) : Unit {
            logger.logDebug( resourceId, *vars )
        }


        override fun logPerformance( msg : String ) : Long {
            return logger.logPerformance( msg )
        }

        override fun logPerformance( format : String, vararg vars : Object ) : Long {
            return logger.logPerformance( format, *vars )
        }

        override fun logPerformance( resourceId : Long, vararg vars : Object ) : Long {
            return logger.logPerformance( resourceId, *vars )
        }

        override fun logPerformance( startTime : Long, msg : String ) : Unit {
            logger.logPerformance( startTime, msg )
        }

        override fun logPerformance( startTime : Long, format : String, vararg vars : Object ) : Unit {
            logger.logPerformance( startTime, format, *vars )
        }

        override fun logPerformance( startTime : Long, resourceId : Long, vararg vars : Object ) : Unit {
            val array = arrayListOf( vars )
            logger.logPerformance( startTime, resourceId, *vars )
        }

        // ENVIRONMENT METHODS
        override fun version() : String {
            val currentVersion = "0.1.0.0-SNAPSHOT"
            return currentVersion
        }
	}

}