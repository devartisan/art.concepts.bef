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

import org.slf4j.helpers.MessageFormatter;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String format(String original, Object... replacements)
    {
        if (Pattern.matches(".*\\{\\d+,.+,.+\\}.*", original))
        {
            return MessageFormat.format(original, replacements);
        }
        else
        {
            String r = "\\{\\d\\}";

            if(original.matches(".*" + r + ".*"))
            {
                Pattern p = Pattern.compile(r);
                Matcher m = p.matcher(original);
                StringBuffer b = null;
                int i = 0;

                while(m.find())
                {
                    if(b == null) b = new StringBuffer();
                    m.appendReplacement(b, replacements[i].toString());
                    i++;
                }
                m.appendTail(b);
                return (i > 0) ? b.toString() : null;
            }
            else
            {
                return MessageFormatter.arrayFormat(original, replacements).getMessage();
            }
        }
    }
}
