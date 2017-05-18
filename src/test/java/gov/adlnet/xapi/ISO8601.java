package gov.adlnet.xapi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class ISO8601 {
		private static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

		/** Transform Calendar to ISO 8601 string. */
		public static String fromCalendar(final Calendar calendar) {
			Date date = calendar.getTime();
			String formatted = new SimpleDateFormat(dateFormat).format(date);
			return formatted.substring(0, 22) + ":" + formatted.substring(22);
		}

		/** Get current date and time formatted as ISO 8601 string. */
		public static String now() {
			return fromCalendar(GregorianCalendar.getInstance());
		}

		/** Transform ISO 8601 string to Calendar. */
		public static Calendar toCalendar(final String iso8601string)
				throws ParseException {
			Calendar calendar = GregorianCalendar.getInstance();

            String s = "";
            if (iso8601string.contains("'Z'")){
                s = iso8601string.replace("Z", "+00:00");
            }
            else if (!iso8601string.contains("+")){
                s = iso8601string + "+00:00";
            }
            else{
                s = iso8601string;
            }

			Date date = new SimpleDateFormat(dateFormat).parse(s);
			calendar.setTime(date);
			return calendar;
		}
	}