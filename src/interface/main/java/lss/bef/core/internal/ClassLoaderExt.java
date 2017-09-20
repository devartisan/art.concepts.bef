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

import java.util.Vector;

public class ClassLoaderExt extends ClassLoader {
	// PROPERTIES
	
	// CONSTRUCTORS AND INITIALIZATION
	public ClassLoaderExt()
	{
		super();  // will use the system class loader
	}

	public ClassLoaderExt( ClassLoader baseLoader )
	{
		super( baseLoader );
	}
	
	// PUBLIC METHODS	
	public Vector getPackagesList()
	{
		Vector<Package> listRet = new Vector<Package>();
		Package[] packages = super.getPackages();
		
		for( int counter = 0; counter < packages.length; counter++ )
		{
			Package packageObj = packages[ counter ];
			
			listRet.add( packageObj );
		}
		
		return listRet;
	}
	
	public String[] getPackagesListNames()
	{
		Vector packageList = getPackagesList();
		String[] listRet = new String[ packageList.size() ];
		
		for( int counter = 0; counter < packageList.size(); counter++ )
		{
			Package packageObj = (Package)packageList.get( counter );
			
			listRet[ counter ] = packageObj.getName();
		}
		
		return listRet;
	}	
}


