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


import java.io.IOException;
import java.io.PrintStream;

import java.lang.System;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.util.Hashtable;

import lss.bef.core.Log;
import lss.bef.core.ContextDefines;
import lss.bef.core.LogDefines;

import lss.bef.core.util.internal.LoggingOutputStream;

public class LogImpl implements Log {

    private Hashtable<String,Logger> 		loggers = null;
    private Hashtable<String,FileHandler> 	fileHandlers = null;
    private int 							level = LogDefines.ALL;
    private Logger							currentLogger = null;
    private String							currentLoggerName = null;
    private boolean 						displaySource = true;
    private String							sourceClass = "";
    private String							sourceMethod = "";
    private PrintStream						originalStderr = null;
    private PrintStream						originalStdout = null;

    // CONSTRUCTORS AND INITIALIZATION
    public LogImpl()
    {
        this.loggers = new Hashtable<String,Logger>();
        this.fileHandlers = new Hashtable<String,FileHandler>();

        this.originalStderr = System.err;
        this.originalStdout = System.out;

        initialize();
    }

    private void initialize()
    {
        LogManager logManager = LogManager.getLogManager();
        logManager.reset();

        this.currentLogger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
        this.currentLoggerName = ContextDefines.GLOBAL;

        this.loggers.put( ContextDefines.GLOBAL, Logger.getLogger( Logger.GLOBAL_LOGGER_NAME ) );
        setLevelForLogger( ContextDefines.GLOBAL, this.level );
    }

    public boolean setCurrentLogger( String loggerName )
    {
        boolean bRet =  false;

        if( loggerName != null )
        {
            bRet = this.currentLoggerName.equals( loggerName );

            if( !bRet )
            {
                if( this.loggers.containsKey( loggerName ) )
                {
                    this.currentLogger = Logger.getLogger( loggerName );
                    this.currentLoggerName = loggerName;
                }
                else
                {
                    this.currentLogger = Logger.getLogger( loggerName );
                    this.currentLoggerName = loggerName;
                    this.loggers.put( loggerName, this.currentLogger );
                    setLevelForLogger( loggerName, this.level );
                }
                bRet = true;
            }
        }

        return bRet;
    }

    // PUBLIC METHODS
    public boolean attachFileHandler( String filenamePattern, int maxFileSize, int numberOfRotatingFiles, boolean append )
    {
        boolean bRet = false;

        try
        {
            // log file max size 100K, 3 rolling files, append-on-open
            Handler fileHandler = new FileHandler( filenamePattern, maxFileSize, numberOfRotatingFiles, append );
            fileHandler.setFormatter( new SimpleFormatter() );

            this.currentLogger.addHandler( fileHandler );
        }
        catch( Exception ex )
        {
            System.setOut( this.originalStdout );
            System.setErr( this.originalStderr );

            System.out.println( "Exception attaching file logger to current logger - Message=" + ex.getMessage() );
        }

        return bRet;
    }

    public boolean attachFileHandlerFor( String loggerName, String filename )
    {
        boolean bRet = false;
        Logger logger = this.loggers.get( loggerName );

        if( logger != null )
        {
            FileHandler fh = this.fileHandlers.get( loggerName );

            if( fh != null )
            {
                fh.close();
                this.fileHandlers.remove( loggerName );
                fh = null;
            }

            try
            {
                fh = new FileHandler( filename );

                fh.setFormatter( new SimpleFormatter() );

                logger.addHandler( fh );
                bRet = true;
            }
            catch( IOException ex )
            {
                System.out.println( "Error attaching [" + filename + "] to log " + loggerName + " Message=" + ex.getMessage() );
            }
        }

        return bRet;
    }

    public boolean detachFileHandlerFor( String loggerName )
    {
        boolean bRet = false;
        Logger logger = this.loggers.get( loggerName );

        if( logger != null )
        {
            FileHandler fh = this.fileHandlers.get( loggerName );

            if( fh != null )
            {
                logger.removeHandler( fh );

                fh.close();
                this.fileHandlers.remove( loggerName );
                fh = null;
                bRet = true;
            }
        }

        return bRet;
    }

    public int getCurrentGlobalLogLevel()
    {
        return this.level;
    }

    public void setCurrentGlobalLogLevel( int level )
    {
        this.level = level;
    }

    public PrintStream getOriginalStderr()
    {
        return this.originalStderr;
    }

    public PrintStream getOriginalStdout()
    {
        return this.originalStdout;
    }

    public void logEnableLevel( int level )
    {
        if( level == LogDefines.ALL || level == LogDefines.OFF )
        {
            this.level = level;
            setLevelForLogger( ContextDefines.GLOBAL, level );
            setLevelForLogger( ContextDefines.APPLICATION, level );
        }
        else
        {
            this.level = level - 1;
            setLevelForLogger( ContextDefines.GLOBAL, level );
            setLevelForLogger( ContextDefines.APPLICATION, level );
        }
    }

    public void logEnableTargetDisplay( boolean flag )
    {
        this.displaySource = flag;
    }

    public boolean logIsLevelEnabled( int level )
    {
        boolean bRet = false;

        if( level > this.level )
        {
            bRet = true;
        }
        return bRet;
    }

    public void log( int level, String msg )
    {
        if( logIsLevelEnabled( level ) )
        {
            postToLog( level, msg );
        }
    }

    public void log( int level, String format, Object...vars )
    {
        if( logIsLevelEnabled( level ) )
        {
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( level, formatObj.format( vars ) );
        }
    }

    public void log( int level, long resourceId, Object...vars )
    {

    }

    public void logSetDisplaySource( boolean flag )
    {
        this.displaySource = flag;
    }

    public void redirectStderrToLog()
    {
        // now rebind stdout/stderr to logger
        Logger logger;
        LoggingOutputStream los;

        logger = Logger.getLogger( "stderr" );
        los= new LoggingOutputStream( logger, Level.ALL );
        System.setErr( new PrintStream( los, true ) );
    }

    public void redirectStdoutToLog()
    {
        // now rebind stdout/stderr to logger
        Logger logger;
        LoggingOutputStream los;

        logger = Logger.getLogger( "stdout" );
        los = new LoggingOutputStream( logger, Level.ALL );
        System.setOut( new PrintStream( los, true ) );
    }

    public void resetStderrToOriginal()
    {
        System.setErr( this.originalStderr );
    }

    public void resetStdoutToOriginal()
    {
        System.setOut( this.originalStdout );
    }

    // CONVENIENCE METHODS
    public void logDebug( String msg )
    {
        if( logIsLevelEnabled( LogDefines.DEBUG ) )
        {
            postToLog( LogDefines.DEBUG, msg );
        }
        else
        {
            System.out.println( "LogDebug is not enabled" );
        }
    }

    public void logDebug( String format, Object...vars )
    {
        if( logIsLevelEnabled( LogDefines.DEBUG ) )
        {
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.DEBUG, formatObj.format( vars ) );
        }
    }

    public void logDebug( long resourceId, Object...vars )
    {

    }

    public void logException( String msg )
    {
        if( logIsLevelEnabled( LogDefines.EXCEPTION ) )
        {
            postToLog( LogDefines.EXCEPTION, msg );
        }
    }


    public void logException( String format, Object...vars )
    {
        if( logIsLevelEnabled( LogDefines.EXCEPTION ) )
        {
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.EXCEPTION, formatObj.format( vars ) );
        }
    }

    public void logException( long resourceId, Object...vars )
    {

    }

    public void logError( String msg )
    {
        if( logIsLevelEnabled( LogDefines.ERROR ) )
        {
            postToLog( LogDefines.ERROR, msg );
        }
    }

    public void logError( String format, Object...vars )
    {
        if( logIsLevelEnabled( LogDefines.ERROR ) )
        {
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.ERROR, formatObj.format( vars ) );
        }
    }

    public void logError( long resourceId, Object...vars )
    {

    }

    public void logInfo( String msg )
    {
        if( logIsLevelEnabled( LogDefines.INFO ) )
        {
            postToLog( LogDefines.INFO, msg );
        }
        else
        {
            System.out.println( "LogInfo is not enabled" );
        }
    }

    public void logInfo( String format, Object...vars )
    {
        if( logIsLevelEnabled( LogDefines.INFO ) )
        {
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.INFO, formatObj.format( vars ) );
        }
    }

    public void logInfo( long resourceId, Object...vars )
    {

    }

    public long logPerformance( String msg )
    {
        long lRet = 0;

        if( logIsLevelEnabled( LogDefines.PERFORMANCE ) )
        {
            Date now = new Date();

            postToLog( LogDefines.PERFORMANCE, msg );
            lRet = now.getTime();
        }

        return lRet;
    }

    public long logPerformance( String format, Object...vars )
    {
        long lRet = 0;

        if( logIsLevelEnabled( LogDefines.PERFORMANCE ) )
        {
            Date now = new Date();
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.PERFORMANCE, formatObj.format( vars ) );
            lRet = now.getTime();
        }

        return lRet;
    }

    public long logPerformance( long resourceId, Object...vars )
    {
        long lRet = 0;

        if( logIsLevelEnabled( LogDefines.PERFORMANCE ) )
        {
        }

        return lRet;
    }

    public void logPerformance( long startTime, String msg )
    {
        if( logIsLevelEnabled( LogDefines.PERFORMANCE ) )
        {
            Date now = new Date();

            postToLog( LogDefines.PERFORMANCE, msg + " :Duration=" + (now.getTime()-startTime) + " mills." );
        }
    }

    public void logPerformance( long startTime, String format, Object...vars )
    {
        if( logIsLevelEnabled( LogDefines.PERFORMANCE ) )
        {
            Date now = new Date();
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.PERFORMANCE, formatObj.format( vars ) + " :Duration=" + (now.getTime()-startTime) + " mills." );
        }
    }

    public void logPerformance( long startTime, long resourceId, Object...vars )
    {

    }

    public void logWarning( String msg )
    {
        if( logIsLevelEnabled( LogDefines.WARNING ) )
        {
            postToLog( LogDefines.WARNING, msg );
        }
    }

    public void logWarning( String format, Object...vars )
    {
        if( logIsLevelEnabled( LogDefines.WARNING ) )
        {
            MessageFormat formatObj = new MessageFormat( format );

            postToLog( LogDefines.WARNING, formatObj.format( vars ) );
        }
    }

    public void logWarning( long resourceId, Object...vars )
    {

    }

    // PRIVATE METHODS
    // Private method to infer the caller's class and method names
    private void inferCaller( int level )
    {
        // Get the stack trace.
        StackTraceElement stack[] = (new Throwable()).getStackTrace();

        // First, search back to a method in the Logger class.
        int ix = 0;
        while( ix < stack.length )
        {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();

            if( cname.equals( "corej.context.LoggerImp" ) ||
                    cname.equals("java.util.logging.Logger") )
            {
                break;
            }
            ix++;
        }

        // Now search for the first frame before the "Logger" class.
        while( ix < stack.length )
        {
            StackTraceElement frame = stack[ix];

            String cname = frame.getClassName();
            if( !cname.equals("corej.context.LoggerImp" ) &&
                    !cname.equals("corej.context.Do" ) &&
                    !cname.equals("java.util.logging.Logger"))
            {
                // We've found the relevant frame.
                this.sourceClass = cname;
                this.sourceMethod = frame.getMethodName() + "(" + frame.getLineNumber() + ")";

                if( level == LogDefines.EXCEPTION )
                {
                    ix++;
                    if( ix < stack.length )
                    {
                        frame = stack[ix];

                        this.sourceClass = this.sourceClass + " " + this.sourceMethod;
                        this.sourceMethod = frame.getClassName() + " " + frame.getMethodName() + "(" + frame.getLineNumber() + "}";
                    }
                }

                return;
            }
            ix++;
        }
        // We haven't found a suitable frame, so just punt.  This is
        // OK as we are only committed to making a "best effort" here.
    }

    private void postToLog(  int level, String msg )
    {
        inferCaller( level );

        switch( level )
        {
            case LogDefines.EXCEPTION:
                if( this.displaySource )
                    this.currentLogger.logp( Level.SEVERE, this.sourceClass, this.sourceMethod, msg + " :EXCEPTION:");
                else
                    this.currentLogger.logp( Level.SEVERE, "","", msg + " :EXCEPTION:" );
                break;
            case LogDefines.ERROR:
                if( this.displaySource )
                    this.currentLogger.logp( Level.SEVERE, this.sourceClass, this.sourceMethod, msg );
                else
                    this.currentLogger.logp( Level.SEVERE, "", "", msg );
                break;
            case LogDefines.WARNING:
                if( this.displaySource )
                    this.currentLogger.logp( Level.WARNING, this.sourceClass, this.sourceMethod, msg );
                else
                    this.currentLogger.logp( Level.WARNING, "", "", msg );
                break;
            case LogDefines.INFO:
                if( this.displaySource )
                    this.currentLogger.logp( Level.INFO, this.sourceClass, this.sourceMethod, msg );
                else
                    this.currentLogger.logp( Level.INFO, "", "", msg );
                break;
            case LogDefines.DEBUG:
                if( this.displaySource )
                    this.currentLogger.logp( Level.CONFIG, this.sourceClass, this.sourceMethod, msg + " :DEBUG:" );
                else
                    this.currentLogger.logp( Level.CONFIG, "", "", msg + " :DEBUG:" );
                break;
            case LogDefines.PERFORMANCE:
                if( this.displaySource )
                    this.currentLogger.logp( Level.FINE, this.sourceClass, this.sourceMethod, msg + " :PERFORMANCE:" );
                else
                    this.currentLogger.logp( Level.FINE, "", "", msg + " :PERFORMANCE:" );
                break;
        }

        this.sourceClass = "";
        this.sourceMethod = "";
    }

    private void setLevelForLogger( String loggerName, int level )
    {
        Logger logger = this.loggers.get( loggerName );

        if( logger != null )
        {
            if( level == LogDefines.ALL )
            {
                logger.setLevel( Level.ALL );
            }
            else if( level == LogDefines.CONFIG )
            {
                logger.setLevel( Level.CONFIG );
            }
            else if( level == LogDefines.DEBUG )
            {
                logger.setLevel( Level.FINE );
            }
            else if( level == LogDefines.ERROR || level == LogDefines.EXCEPTION )
            {
                logger.setLevel( Level.SEVERE );
            }
            else if( level == LogDefines.INFO )
            {
                logger.setLevel( Level.INFO );
            }
            else if( level == LogDefines.PERFORMANCE )
            {
                logger.setLevel( Level.FINER );
            }
            else if( level == LogDefines.VERBOSE )
            {
                logger.setLevel(  Level.FINEST );
            }
            else if( level == LogDefines.WARNING )
            {
                logger.setLevel(  Level.WARNING );
            }
            else if( level == LogDefines.OFF )
            {
                logger.setLevel( Level.OFF  );
            }
        }
    }
}
