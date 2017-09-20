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

package lss.bef.core.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.lang.IllegalAccessException;
import java.lang.IllegalArgumentException;

public class ClassFactoryUtil {
//	private static Log 	log = null;
	
/*	public static void setLogObject( Log logObj )
	{
		log = logObj;
	}
*/
	public static Object createObject( Class cls )
	{
		return createObject( cls, null, (Object[])null );
	}

	public static Object createObject( Class cls, Object ... objects  )
	{
		return createObject( cls, null, objects );
	}

	public static Object createObject( Class<?> cls, Class<?> assignableClass, Object ... objects  )
	{
		Object objRet = null;

		try
		{
			if( cls != null )
			{
				if( assignableClass == null )
				{
//					System.out.println( "About to create object for " + cls.getName() );
					if( objects != null && objects.length > 0 )
					{
						Class<?>[] parmTypes = new Class<?>[ objects.length ];
						
						for( int index = 0; index < objects.length; index++ )
						{
							parmTypes[ index ] = objects[ index ].getClass();
						}
						
						Constructor constructor = getConstructorFor( cls, parmTypes );
						
						if( constructor != null )
						{
							objRet = constructor.newInstance( objects );
						}
					}
					else
					{
						objRet = cls.newInstance();						
					}
//					System.out.println( "Object created object for " + cls.getName() + " Instance id=" + objRet.hashCode() );
				}
				else
				{
					if( assignableClass.isAssignableFrom( cls ) )
					{
						objRet = cls.newInstance();
					}
					else
					{
//						log.logError( "Create class failed - Incompatible classes : Class = " + cls.getName() + " Superclass or Interface = " + assignableClass.getName() );
					}
					
				}
			}
		}
		catch( ExceptionInInitializerError ex )
		{
//			log.logException( "Initializer error(ExceptionInInitializerError)  for " + cls.getName() );

		}
		catch( IllegalAccessException ex )
		{
//			log.logException( "Access error(IllegalAccessException) for " + cls.getName() );
		}
		catch( InstantiationException ex )
		{
//			log.logException( "Instantiation error(InstantiationException) for " + cls.getName() );
		}
		catch( InvocationTargetException ex )
		{
//			log.logException( "InvocationTargetException exception for " + cls.getName()  );
		}
		catch( LinkageError ex )
		{
//			log.logException( "Linkage error for " + cls.getName()  );
		}
		catch( SecurityException ex )
		{
//			log.logException( "Security error for " + cls.getName() );
		}
		return objRet;
	}

	public static Class getClassForName( String className )
	{
		return getClassForName( null, className );
	}

	public static Class getClassForName( ClassLoader loader, String className )
	{
		Class classRet = null;

		try
		{
			if( loader != null )
				classRet = loader.loadClass( className );
			else
				classRet = Class.forName( className );
		}
		catch( ClassNotFoundException ex )
		{
//
// 			log.logError( "Class not found for " + className );
			ClassLoaderExt loaderExt = null;
			
			if( loader != null )
				loaderExt = new ClassLoaderExt( loader );
			else
				loaderExt = new ClassLoaderExt();

//			log.logInfo( " Loader packages=" + loaderExt.getPackagesListNames().toString() );
		}
		return classRet;
	}

	public static Constructor getConstructorFor( Class<?> classObj, Class<?>[] parmTypes )
	{
		Constructor methodRet = null;

		try
		{
			Constructor[] constructors = classObj.getConstructors();
			
			for( int index = 0; methodRet == null && index < constructors.length; index++ )
			{
				Constructor constructor = constructors[ index ];
				
				Class<?>[] parameters = constructor.getParameterTypes();
				
				if( parameters.length == parmTypes.length )
				{
					int counter = 0;
					for( ; counter < parameters.length; counter++ )
					{
						if( !parameters[counter].isAssignableFrom( parmTypes[ counter ] ) &&
							( parameters[counter].isPrimitive() && !checkPrimitiveCompatibilty( parameters[counter], parmTypes[ counter ] ) ) &&
							( parmTypes[ counter ].isPrimitive() && !checkPrimitiveCompatibilty( parmTypes[ counter ], parameters[counter] ) ) )
						{
							break;
						}
					}
					
					if( counter == parameters.length )
					{
						methodRet = constructor;
					}
				}
			}
		}
		catch( SecurityException ex )
		{
//			log.logException( "Error(SecurityException) accessing constructor for Class=[" + classObj.getName() + "] with Paramater Types=[" + parmTypes.toString() + "] Message=" + ex.getMessage() );
		}
		return methodRet;
	}

	public static Method getMethodFor( String className, String methodName, Class[] parmTypes, Class retType )
	{
		Method methodRet = null;
		Class cls = getClassForName( className );
		
		if( cls != null )
		{
			methodRet = getMethodFor( cls, methodName, parmTypes, retType );
		}
		return methodRet;
	}
	
	
	public static Method getMethodFor( Class<?> classObj, String methodName, Class<?>[] parmTypes, Class<?> retType )
	{
		Method methodRet = null;

		try
		{
			Method[] methods = classObj.getMethods();

			if( methods.length > 0 )
			{
				for( int counter = 0; counter < methods.length; counter++ )
				{
					Method method = methods[counter];
					String name = method.getName();
					
					if( methodName.equals( name ) )
					{
						methodRet = method;  // if this assignement persists to the end of loop - this is a good find
						
						if( retType != null )
						{
							Class<?> methodReturnType = method.getReturnType();
							
							if( !methodReturnType.isAssignableFrom( retType ) )
							{
								methodRet = null; // this is not a good find - return types don't match
							}
						}
						
						if( methodRet != null && parmTypes != null && parmTypes.length > 0 )
						{
							Class[] methodParameters = method.getParameterTypes();
							
							if( parmTypes.length == methodParameters.length )
							{
								for( int counter2 = 0; counter2 < methodParameters.length; counter2++ )
								{
									if( !parmTypes[counter2].isInstance( methodParameters[counter2] ) )
									{
										methodRet = null; // this is not a good find - parameter is not of the same type
									}
								}
							}
							else
							{
								methodRet = null;  // this is not a good match - number of parameters don't match
							}
						}
					}
					
					if( methodRet != null )
						break;
				}
			}
		}
		catch( SecurityException ex )
		{
//			log.logException( "Error(SecurityException) accessing method(" + methodName + ") for class " + classObj.getName() + " Message=" + ex.getMessage() );
		}
		return methodRet;
	}

	public static String[] getMethodsForClass( String className )
	{
		String[] listRet = new String[0];
		Class classObj = getClassForName( className );

		if( classObj != null )
			listRet = getMethodsForClass( classObj );

		return listRet;
	}

	public static String[] getMethodsForClass( Class classObj )
	{
		String[] listRet = new String[0];

		try
		{
			Method[] methods = classObj.getMethods();

			if( methods.length > 0 )
			{
				listRet = new String[methods.length];
				for( int counter = 0; counter < methods.length; counter++ )
				{
					Method method = methods[counter];

					listRet[counter] = method.getName();
				}
			}
		}
		catch( SecurityException ex )
		{
//			log.logException( "Error(SecurityException) accessing methods list for class " + classObj.getName() + " Message=" + ex.getMessage() );
		}
		return listRet;
	}
	
	public static Object runMethodFor( Object obj, String methodName, Object[] parms, Class retType )
	{
		Object retObj = null;
		
		Class[] parmTypes = null;
		if( parms!= null && parms.length > 0 )
		{
			parmTypes = new Class[ parms.length ];
			
			for( int counter = 0; counter < parms.length; counter++ )
			{
				parmTypes[counter] =  parms[counter].getClass();
			}
		}
		
		Method method = getMethodFor( obj.getClass(), methodName, parmTypes, retType );
		
		if( method != null )
		{
			try
			{
//				System.out.println( "About to invoke method [" + methodName + "] for " + obj.getClass().getName() + " Id=" + obj.hashCode() );
				retObj = method.invoke( obj, parms );
//				System.out.println( "Invoked method [" + methodName + "] for " + obj.getClass().getName() + " Id=" + obj.hashCode() + " Ret=" + retObj );
			}
			catch( InvocationTargetException ex )
			{
				//message.appendToMessage( "Invocation error(InvocationTargetException) method " + methodName + " in object " + obj.getClass().getName() + " Message=" + ex.getMessage() + ": TargetObject=" + obj.toString() + ": Args=" + argsArray.toString() );    			
	        	//System.out.println( "Invocation error(InvocationTargetException) method " + methodName + " in object " + obj.getClass().getName() + " Message=" + ex.getMessage() + ": TargetObject=" + obj.toString() + ": Args=" + parms.toString() );
//				log.logException( "Invocation error(InvocationTargetException) method " + methodName + " Message=" + ex.getTargetException().getMessage() );
				
				ex.getTargetException().printStackTrace();
			}
			catch( IllegalArgumentException ex )
			{
				//message.appendToMessage( "Invocation error(IllegalArgumentException)  method " + methodName + " in object " + obj.getClass().getName() + " Message=" + ex.getMessage() + ": TargetObject=" + obj.toString() + ": Args=" + argsArray.toString() );    			
//				log.logException( "Invocation error(IllegalArgumentException)  method " + methodName + " in object " + obj.getClass().getName() + " Message=" + ex.getMessage() + ": TargetObject=" + obj.toString() + ": Args=" + parms.toString() );
			}
			catch( IllegalAccessException ex )
			{
				//message.appendToMessage( "Invocation error(IllegalAccessException)  method " + methodName + " in object " + obj.getClass().getName() + " Message=" + ex.getMessage() + ": TargetObject=" + obj.toString() + ": Args=" + argsArray.toString() );    			
//				log.logException( "Invocation error(IllegalAccessException)  method " + methodName + " in object " + obj.getClass().getName() + " Message=" + ex.getMessage() + ": TargetObject=" + obj.toString() + ": Args=" + parms.toString() );
			}		
		}
		
		return retObj;
	}
	
	// Private methods
	
	private static boolean checkPrimitiveCompatibilty( Class cls1, Class cls2 )
	{
		boolean bRet = false;
		
		if( cls1.getName().equals( "int" ) && cls2.getName().equals( "java.lang.Integer" ) )
			bRet = true;
		else if( cls1.getName().equals( "boolean" ) && cls2.getName().equals( "java.lang.Boolean" ) )
			bRet = true;
		else if( cls1.getName().equals( "char" ) && cls2.getName().equals( "java.lang.Character" ) )
			bRet = true;
		else if( cls1.getName().equals( "byte" ) && cls2.getName().equals( "java.lang.Byte" ) )
			bRet = true;
		else if( cls1.getName().equals( "short" ) && cls2.getName().equals( "java.lang.Short" ) )
			bRet = true;
		else if( cls1.getName().equals( "long" ) && cls2.getName().equals( "java.lang.Long" ) )
			bRet = true;
		else if( cls1.getName().equals( "float" ) && cls2.getName().equals( "java.lang.Float" ) )
			bRet = true;		
		else if( cls1.getName().equals( "double" ) && cls2.getName().equals( "java.lang.Double" ) )
			bRet = true;
		else if( cls1.getName().equals( "void" ) && cls2.getName().equals( "java.lang.Void" ) )
			bRet = true;	
		
		return bRet;
	}
}
