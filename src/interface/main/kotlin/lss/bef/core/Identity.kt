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

interface Identity {
    fun getSignatureIdForFile( path : String): String
    fun getUniqueId(): Long
    fun getUniqueId(prefix: String): String
    fun getUniqueId(prefix: String, suffix: String): String
    fun getUniqueStrFromId(id : Long): String
    fun getUniqueIdFromStr(value : String): Long
    fun getUniqueIdFromHash(value : String): Long
    fun getWeakUniqueId(): Int
    fun getWeakUniqueId(prefix: String): String
    fun getWeakUniqueId(prefix: String, suffix: String): String
    fun getWeakUniqueStrFromId(id : Int): String
    fun getWeakUniqueIdFromStr(value : String): Int
    fun getWeakUniqueIdFromHash(value : String): Int
}