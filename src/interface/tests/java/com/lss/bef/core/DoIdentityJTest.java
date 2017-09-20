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

package com.lss.bef.core;

import lss.bef.core.Do;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class DoIdentityJTest {
	@Test
	public void identityLongTest() {
		long id = Do.Execute.getUniqueId();
		
		System.out.printf( "On DoIdentityTest long id = [%s]\n", id );
		assertNotEquals( 0, id );
	}

	@Test
	public void identityUniqueIdFromStrTest() {
		String testStr = "1y2p0ij32e8e7";
		long id = Do.Execute.getUniqueIdFromStr( testStr );
		String resultStr = Do.Execute.getUniqueStrFromId( id );

		System.out.printf( "On DoIdentityTest long id = [%s] from string [%s]\n", id, testStr );
		assertEquals( testStr, resultStr );
	}

	@Test
	public void identityWeakUniqueIdFromStrTest() {
		String testStr = "zik0zj";
		int id = Do.Execute.getWeakUniqueIdFromStr( testStr );
		String resultStr = Do.Execute.getWeakUniqueStrFromId( id );

		System.out.printf( "On DoIdentityTest int id = [%s] from string [%s]\n", id, testStr );
		assertEquals( testStr, resultStr );
	}

	@Test
	public void identityWeakUniqueIdFromHashTest() {
		String testStr1 = "THIS_IS_TEST_STRING";
		String testStr2 = "THIS_IS_TEST_STRING2";
		int id1 = Do.Execute.getWeakUniqueIdFromHash( testStr1 );
		int id2 = Do.Execute.getWeakUniqueIdFromHash( testStr2 );

		System.out.printf( "On DoIdentityTest int id1 = [%s] from hasg string [%s]\n", id1, testStr1 );
		System.out.printf( "On DoIdentityTest int id2 = [%s] from hasg string [%s]\n", id2, testStr2 );
		assertNotEquals( id1, id2 );
	}
}
