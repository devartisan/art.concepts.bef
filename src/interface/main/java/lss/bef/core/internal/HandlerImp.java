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


public class HandlerImp
{
	// CONSTRUCTORS AND INITIALIZATION

	// PUBLIC METHODS
	public String[] getMethodsFor( Class cls )
	{
		return ClassFactoryUtil.getMethodsForClass( cls );
	}
	
	public String[] getMethodsFor( Object obj )
	{
		return ClassFactoryUtil.getMethodsForClass( obj.getClass() );
	}
	
	public String[] getMethodsFor( String componentName )
	{
		return ClassFactoryUtil.getMethodsForClass( componentName );
	}

	public boolean isClassAvailableFor( String componentName )
	{
		return ClassFactoryUtil.getClassForName( componentName ) != null;
	}
	
	public boolean isMethodAvailableFor( Class cls, String methodName )
	{
		return ClassFactoryUtil.getMethodFor( cls, methodName, null, null ) != null;
	}
	
	public boolean isMethodAvailableFor( Object obj, String methodName )
	{
		return ClassFactoryUtil.getMethodFor( obj.getClass(), methodName, null, null ) != null;
	}
	
	public boolean isMethodAvailableFor( String componentName, String methodName )
	{
		return ClassFactoryUtil.getMethodFor( componentName, methodName, null, null ) != null;
	}

	public Object runMethodFor( Object obj, String methodName )
	{
		return ClassFactoryUtil.runMethodFor( obj, methodName, null, null );
	}
}
