package com.bundyapitest.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class ChurchGroupXmlParser {

	List<ChurchGroup> groups;
	ChurchGroup groupEntry;

	public List<ChurchGroup> parse(InputStream is)
			throws XmlPullParserException, IOException {

		String currentText = "";
		

		groups = new ArrayList<ChurchGroup>();
		

		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(is, null);

			int eventType = -1;
			int i = 0;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName();

				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagName.equalsIgnoreCase("item")) {
						groupEntry = new ChurchGroup();
					}
					break;

				case XmlPullParser.TEXT:
					currentText = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagName.equalsIgnoreCase("item")) {
						groups.add(groupEntry);
					}else if (tagName.equalsIgnoreCase("name")) {
						groupEntry.setGroupName(currentText);
					}else if (tagName.equalsIgnoreCase("group_type_name")) {
						groupEntry.setGroupType(currentText);
					}else if (tagName.equalsIgnoreCase("meet_day_name")) {
						groupEntry.setGroupTime(currentText);
					}else if (tagName.equalsIgnoreCase("description")) {
						groupEntry.setGroupDescription(currentText);
					}

					break;

				default:
					break;
				}

				eventType = parser.next();
				Log.i("eric", "i " + i + ", Tag " + tagName);
				i++;
			}
			return groups;
		} finally {
			is.close();
		}

	}

}
