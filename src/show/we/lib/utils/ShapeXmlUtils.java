package show.we.lib.utils;

import show.we.lib.widget.animation.Point;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ll
 * @version 1.0.0
 */
public class ShapeXmlUtils {
    private static final String ENCODE = "utf-8";
    private static final String XML_TAG_ITEM_TAG = "point";
    private static final float OFFSET_X = 0;
    private static float sDeltaX = 0;
    private static float sRatio = 1;
    private static final float ORIGIN_WIDTH = 720f;
    private static final float DEFAULT_RATIO = DisplayUtils.getWidthPixels() / ORIGIN_WIDTH;

    /**
     * 解析xml
     *
     * @param inputStream 输入流
     * @param widthRatio 比例
     * @return map
     * @throws org.xmlpull.v1.XmlPullParserException XmlPullParserException
     * @throws java.io.IOException                   IOException
     */
    public static List<Point> parse(InputStream inputStream, float widthRatio) throws XmlPullParserException, IOException {
        float screenRatio = DEFAULT_RATIO * widthRatio;
        List<Point> points = new ArrayList<Point>();
        XmlPullParser xmlParser = XmlPullParserFactory.newInstance().newPullParser();
        xmlParser.setInput(inputStream, ENCODE);

        int eventType = xmlParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    String name = xmlParser.getName();
                    if (name.equals("points")) {
                        float deltaX = Float.valueOf(xmlParser.getAttributeValue(0));
                        float ratio = Float.valueOf(xmlParser.getAttributeValue(1));
                        sDeltaX = deltaX;
                        sRatio = ratio * screenRatio;
                    } else if (name.equals(XML_TAG_ITEM_TAG)) {
                        String attributeValue1 = xmlParser.getAttributeValue(0);
                        String attributeValue2 = xmlParser.getAttributeValue(1);
                        Point p = new Point(sRatio * (Float.valueOf(attributeValue1) + sDeltaX - OFFSET_X), sRatio * Float.valueOf(attributeValue2));
                        points.add(p);
                    }
                    break;

                case XmlPullParser.END_TAG:
                    break;

                default:
                    break;
            }

            eventType = xmlParser.next();
        }

        return points;
    }
}
