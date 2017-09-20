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

import java.util.Iterator;
import java.util.Random;

import java.util.Hashtable;

//import corej.context.Log;
//import corej.context.internal.ClassFactoryUtil;

//import com.sun.jmx.remote.opt.util.Service;

import java.lang.InheritableThreadLocal;


// INTERNAL CLASSES
//import corej.object.Handler;
//import corej.object.HandlerImp;

// WELL KNOWN CLASSES
//import corej.network.ServerRequestHandler;
//import corej.network.ServerRequestHandlerImp;
//import corej.network.ServerRequestProcessor;
//import corej.network.ServerRequestProcessorBase;

//import corej.network.file.FileClient;
//import corej.network.file.FileClientImp;
//import corej.network.file.FileServer;
//import corej.network.file.FileServerImp;

//import corej.network.socket.SocketClient;
//import corej.network.socket.SocketClientImp;
//import corej.network.socket.SocketServer;
//import corej.network.socket.SocketServerImp;

//import corej.util.StreamUtil;
//import corej.util.StreamUtilImp;
//import corej.util.StringTokenizerEx;
//import corej.util.StringTokenizerExImp;

import lss.bef.core.internal.ClassFactoryUtil;
import lss.bef.core.internal.HandlerImp;

// INTERNALY KNOWN CLASSES
import lss.bef.entity.Tuple;
import lss.bef.entity.TupleBase;

import lss.bef.core.Factory;
import lss.bef.entity.EntityDefines;

public class FactoryImp implements Factory
{
	// FACTORY SUPPORT
	private static Random 												discriminator = new Random();
//	private static InheritableThreadLocal<SessionContainer> 			tLocalSessionContainer = new InheritableThreadLocal();


	private Hashtable<Class,Class> 					internalClasses = null;
    private Hashtable<String,Class> 				internalClassesByName = null;
	private Hashtable<Class,Class> 					wellKnownClasses = null;
	
//	private LoggerImp								logController = null;
	
	// LOGGERS
	
	public FactoryImp()
	{
		this.internalClasses = new Hashtable<Class,Class>();
        this.internalClassesByName = new Hashtable<String,Class>();
		this.wellKnownClasses = new Hashtable<Class,Class>();
		
		initializeInternalObjectDictionary();
	}

	// PUBLIC METHODS
/*	public Class classObj( String componentName )
	{
		Class objRet = ClassFactoryUtil.getClassForName( componentName );

		return objRet;
	}
*/
/*	public SessionContainer getSessionContainer()
	{
		SessionContainer objRet = tLocalSessionContainer.get();
		
		if( objRet == null )
		{
			String strRet = this.getUniqueId( "Session#" );
			objRet = new SessionContainerImp( strRet );
			
			tLocalSessionContainer.set( objRet );
		}
		
		return objRet;
	}
*/

	public Object instance( String componentName ) {
		Object objRet = null;
        Class cls = this.internalClassesByName.get( componentName );

        if( cls == null )
            cls = ClassFactoryUtil.getClassForName( componentName );

		System.out.println( "Found class for [" + componentName + "] Class=" + cls.getName() );
		
		if( cls != null ) {
			objRet = getInstance( cls );
		}
		
		return objRet;
	}

/*
	public Object instance( Class cls, Object ... objects  )
	{
		return getInstance( cls, objects );
	}

	public Object instance( String componentName, Object ... objects )
	{
		Object objRet = null;
		Class cls = ClassFactoryUtil.getClassForName( componentName );

//		System.out.println( "Found class for [" + componentName + "] Class=" + cls.getName() );
		
		if( cls != null )
		{
			objRet = getInstance( cls, objects );
		}
		
		return objRet;
	}
*/

	// PRIVATE METHODS
	private void initializeInternalObjectDictionary()
	{
//		this.internalClasses.put( Handler.class, HandlerImp.class );

//		this.wellKnownClasses.put( ServerRequestHandler.class, ServerRequestHandlerImp.class );
//		this.wellKnownClasses.put( ServerRequestProcessor.class, ServerRequestProcessorBase.class );
		
//		this.wellKnownClasses.put( FileClient.class, FileClientImp.class );
//		this.wellKnownClasses.put( FileServer.class, FileServerImp.class );

//		this.wellKnownClasses.put( SocketClient.class, SocketClientImp.class );
//		this.wellKnownClasses.put( SocketServer.class, SocketServerImp.class );

//		this.wellKnownClasses.put( StreamUtil.class, StreamUtilImp.class );
//		this.wellKnownClasses.put( StringTokenizerEx.class, StringTokenizerExImp.class );

        this.wellKnownClasses.put( Tuple.class, TupleBase.class );
        this.internalClassesByName.put( EntityDefines.TupleBase, Tuple.class );
	}
	
	private Object getInstance( Class cls, Object ... objects )
	{
		Object objRet = null;
		
		// FIRST - we look if this class is one of the internal very well know entities that we don't want
		// delegate to any other factory 
		if( this.internalClasses.containsKey( cls ) )
		{
			if( objects != null && objects.length > 0 )
			{
				objRet = ClassFactoryUtil.createObject( internalClasses.get( cls ), cls, objects );
			}
			else
			{
				objRet = ClassFactoryUtil.createObject( internalClasses.get( cls ), cls );
			}
			
			if( objRet instanceof HandlerImp)
			{
				HandlerImp obj = (HandlerImp)objRet;
			}
		}
		else if( this.wellKnownClasses.containsKey( cls ) )
		{
			if( objects != null && objects.length > 0 )
			{
				objRet = ClassFactoryUtil.createObject( wellKnownClasses.get( cls ), cls, objects );
			}
			else
			{
				objRet = ClassFactoryUtil.createObject( wellKnownClasses.get( cls ), cls );
			}
		}

		// now we will try to use resolve method
		if( objRet == null )
		{
			objRet = resolve( cls );
			
			if( objRet == null )
			{
				// LAST - this is the last resource - in this case we will simple create the object using the 
				// default class loader
				if( objects != null && objects.length > 0 )
				{
					objRet = ClassFactoryUtil.createObject( cls, objects );
				}
				else
				{
					objRet = ClassFactoryUtil.createObject( cls );
				}
			}
		
			if( objRet == null )
			{
				System.out.println( "On FactoryCore.instance(" + cls.getName() + ") - Cannot find suitable factory" );
			}
		}
			
		return objRet;
	}
	
	private Object resolve( Class cls )
	{
		Object objRet = null;

/*		Iterator it = Service.providers( cls, cls.getClassLoader() );

		// This loop means that if there is more than one implementation the resolve will stick with
		// the last one - there is no guarantee of any order of the JARS will influence the sequence
		// of providers - better to make sure you have only one implementation
		while( it.hasNext() )
		{
			objRet = it.next();
		}
*/
		return objRet;
	}
}
