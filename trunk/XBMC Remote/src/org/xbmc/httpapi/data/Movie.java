/*
 *      Copyright (C) 2005-2009 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.httpapi.data;

import java.io.Serializable;

import org.xbmc.android.util.Crc32;
import org.xbmc.httpapi.type.MediaType;

/**
 * Stores what we can get from the movieview table.
 * 
 * @author Team XBMC
 */
public class Movie implements ICoverArt, Serializable, NamedResource {
	
	/**
	 * Points to where the movie thumbs are stored
	 */
	public final static String THUMB_PREFIX = "special://masterprofile/Thumbnails/Video/";

	/**
	 * Constructor
	 * @param id		Database ID
	 * @param name		Album name
	 * @param artist	Artist
	 */
	public Movie(int id, String title, int year, String path, String director) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.localPath = path;
	}

	public Movie(int id, String title, int year, String path, String director, String thumbPath) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.localPath = path;
		thumbPath = thumbPath.replace("\\", "/");
		if (!thumbPath.equals("NONE")) {
			try {
				this.thumbID = Long.parseLong(thumbPath.substring(thumbPath.lastIndexOf("/") + 1, thumbPath.length() - 4), 16);
			} catch (NumberFormatException e) {
				this.thumbID = 0L;
			}
		}
	}
	
	public int getMediaType() {
		return MediaType.VIDEO;
	}

	public String getShortName() {
		return this.title;
	}
	
	/**
	 * Composes the complete path to the album's thumbnail
	 * @return Path to thumbnail
	 */
	public String getThumbUri() {
		return getThumbUri(this);
	}
	
	public static String getThumbUri(ICoverArt cover) {
		final String hex = String.format("%08x", cover.getCrc()).toLowerCase();
		return THUMB_PREFIX + hex.charAt(0) + "/" + hex + ".tbn";
	}
	
	public static String getFallbackThumbUri(ICoverArt cover) {
		final String hex = String.format("%08x", cover.getFallbackCrc()).toLowerCase();
		return THUMB_PREFIX + hex.charAt(0) + "/" + hex + ".tbn";
	}
	
	/**
	 * Returns the CRC of the album on which the thumb name is based upon.
	 * @return CRC32
	 */
	public long getCrc() {
		if (thumbID == 0) {
			thumbID = Crc32.computeLowerCase(localPath);
		}
		return thumbID;
	}
	
	/**
	 * If no album thumb CRC is found, try to get the thumb of the album's
	 * directory.
	 * @return 0-char CRC32
	 */
	public int getFallbackCrc() {
		if (localPath != null) {
			final String lp = localPath;
			return Crc32.computeLowerCase(lp.substring(0, lp.length() - 1));
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns database ID.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns database ID.
	 * @return
	 */
	public String getName() {
		return title + " (" + year + ")";
	}
	
	/**
	 * Something descriptive
	 */
	public String toString() {
		return "[" + id + "] " + title + " (" + year + ")";
	}
	
	/**
	 * Database ID
	 */
	public int id;
	/**
	 * Movie title
	 */
	public String title;
	/**
	 * Director(s), can be several separated by " / "
	 */
	public String director;
	/**
	 * Year released
	 */
	public int year = -1;
	
	/**
	 * Local path of the movie
	 */
	public String localPath;
	
	/**
	 * Rating
	 */
	public int rating = -1;
	/**
	 * Genres, separated by " / "
	 */
	public String genres = null;
	/**
	 * Save this once it's calculated
	 */
	public long thumbID = 0;
	
	private static final long serialVersionUID = 4779827915067184250L;

}
