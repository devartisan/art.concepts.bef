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

package lss.bef.entity

class Type {
    companion object {
        // TYPE DEFINIGIONS
        const val CLASS : String        = "CLASS"   // this is a special type
        const val BOOLEAN : String      = "BOOLEAN"
        const val BYTE : String         = "BYTE"
        const val CHAR : String         = "CHAR"
        const val DOUBLE : String       = "DOUBLE"
        const val FLOAT : String        = "FLOAT"
        const val INT : String          = "INT"
        const val LONG : String         = "LONG"
        const val SHORT : String        = "SHORT"
        const val STRING : String       = "STRING"
        const val OBJECT : String       = "OBJECT"

        // TODO - additional types to implement
        // JSON
        // XML
        // JSON_ARRAY
        // BOOLEAN_ARRAY
        // BYPE_ARRAY
        // CHAR_ARRAY
        // DOUBLE_ARRAY
        // FLOAT_ARRAY
        // INT_ARRAY
        // LONG_ARRAY
        // SHORT_ARRAY
        // STRING_ARRAY
        // OBJECT_ARRAY

        fun _boolean(value: Any): Boolean {
            var bRet = false

            if( value is Boolean) {

                bRet = value.booleanValue()
            } else if (value is Byte) {

                bRet = value.toByte().toInt() != 0
            } else if (value is Char) {

                bRet = value.charValue().toInt() != 0
            } else if (value is Double) {

                bRet = value.toDouble() != 0.0
            } else if (value is Float) {

                bRet = value.toFloat() != 0f
            } else if (value is Int) {

                bRet = value.toInt() != 0
            } else if (value is Long) {

                bRet = value.toLong() != 0
            } else if (value is Short) {

                bRet = value.toShort().toInt() != 0
            } else if (value is String) {

                bRet = value.length > 0
            } else {
                bRet = true
            }

            return bRet
        }


    }
}