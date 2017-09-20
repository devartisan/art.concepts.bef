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

package lss.bef.core.util;

public class Base36Util {

    private static String codeBase36 = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static String max36Int=base10ToBase36(Integer.MAX_VALUE);
    private static String max36Long=base10ToBase36(Long.MAX_VALUE);

    public static String base10ToBase36(int inNum) {
        if(inNum<0) {
            throw new NumberFormatException("Value  "+inNum +"  to small");
        }
        int num = inNum;
        String text = "";
        int j = (int)Math.ceil(Math.log(num)/Math.log(codeBase36.length()));
        for(int i = 0; i < j; i++){
            text = codeBase36.charAt(num%codeBase36.length())+text;
            num /= codeBase36.length();
        }
        return text;
    }

    public static String base10ToBase36(long inNum) {
        if(inNum<0) {
            throw new NumberFormatException("Value  "+inNum +"  to small");
        }
        long num = inNum;
        String text = "";
        int j = (int)Math.ceil(Math.log(num)/Math.log(codeBase36.length()));
        for(int i = 0; i < j; i++){
            text = codeBase36.charAt( (int)(num % (long)codeBase36.length()) ) + text;
            num /= codeBase36.length();
        }
        return text;
    }

    public  static int base36ToBase10Int(String in) {
        String text = in.toLowerCase();
        if(text.compareToIgnoreCase(max36Int)>0) {
            throw new NumberFormatException("Value  " + in + " to big - max [" + max36Int + "]");
        }

        if(!text.replaceAll("(\\W)","").equalsIgnoreCase(text)){
            throw new NumberFormatException("Value "+text+" false format");
        }
        int num=0;
        int j = text.length();
        for(int i = 0; i < j; i++){
            num += codeBase36.indexOf(text.charAt(text.length()-1))*Math.pow(codeBase36.length(), i);
            text = text.substring(0,text.length()-1);
        }
        return num;
    }

    public  static long base36ToBase10Long(String in) {
        String text = in.toLowerCase();
        if(text.compareToIgnoreCase(max36Long)>0) {
            throw new NumberFormatException("Value  " + in + " to big - max [" + max36Long + "]");
        }

        if(!text.replaceAll("(\\W)","").equalsIgnoreCase(text)){
            throw new NumberFormatException("Value "+text+" false format");
        }
        long num=0;
        int j = text.length();
        for(int i = 0; i < j; i++){
            num += codeBase36.indexOf(text.charAt(text.length()-1))*Math.pow(codeBase36.length(), i);
            text = text.substring(0,text.length()-1);
        }
        return num;
    }
}
