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

import lss.bef.core.Do

import io.kotlintest.matchers.shouldEqual
import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

import lss.bef.entity.EntityDefines
import lss.bef.entity.Tuple

class DoEntityKTest : StringSpec() {

	 init  {
		"tupleBaseTest"{
			val tupleBase : Tuple = Do.instance( EntityDefines.TupleBase ) as Tuple
            val name = tupleBase.name
            val size = tupleBase.getSize()

			println( "On DoEntityTest tupleBase name [$name] size=$size\n" )
            name shouldEqual EntityDefines.TupleBase
		}
	}
}