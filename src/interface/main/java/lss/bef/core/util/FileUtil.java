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

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileUtil
{
	static final String JAR_SUFFIX = ".jar";
	static final String ZIP_SUFFIX = ".zip";
	
	String checkOrGetNewFileNameFor( String filename, String directory );
	boolean closeInputStream( InputStream input );
	boolean closeOutputStream( OutputStream output );
	boolean copyFile( String currentPath, String newPath );
	boolean createNewFile( String path );
	boolean createNewFile( String path, String contents );
	boolean createNewFile( String path, boolean deleteCurrentFile );
	boolean createNewFile( String path, String contents, boolean deleteCurrentFile );
	String createNewTempFile( String prefix, String suffix, String directory );
	String createNewTempFile( String prefix, String suffix, String directory, InputStream input );
	boolean deleteFile( String path );
	String getDirPathFromPath( String path );
	String getFileNameExtensionFromPath( String path );
	String getFileNameFromPath( String path );
	String getFileNameOnlyFromPath( String path );
	long getFileSize( String path );
	long  getLastModificationTime( String path );
	String[] getListOfFiles( String dirPath );
	String[] getListOfFiles( File dir, FileFilter filter );
	String[] getListOfFiles( String dirPath, FileFilter filter );
	String[] getListOfFilesModifiedSince( String dirPath, long lastModified );
	String[] getListOfFilesModifiedSince( String dirPath, long lastModified, FileFilter filter );
    String hashFile( String path );
    boolean isFileArchive( String filename );
 	boolean isFilePresent( String path );
	boolean isFileReadyForExclusiveOpen( String path );
	InputStream openInputStream( String path );
	InputStream openInputStream( String directory, String filename );
	OutputStream openOutputStream( String path );
	OutputStream openOutputStream( String directory, String filename );
	String path( String path );
    String path( String path, String fileName );
    String path( String path, String fileName, String suffix );
	String pathUnique( String path, String fileNamePrefix, String suffix );
	String readFileContentsToString( String path );
	boolean renameFile( String currentPath, String newPath );
    boolean isDirectory( String path );
	boolean isFile( String path );
}
