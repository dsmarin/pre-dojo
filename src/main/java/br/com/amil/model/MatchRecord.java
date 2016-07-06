package br.com.amil.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRecord implements Serializable {

	private static final long serialVersionUID = 5781900646110129966L;

	public enum MatchPattern {
		MATCHID("\\d+", 0), //
		KILLER_PLAYER("(\\w+|<WORLD>) killed", 1), //
		KILLED_PLAYER("killed (\\w+)", 1), //
		WEAPON("(using|by)\\s(\\w+)", 2);

		private Pattern regex;
		private Integer group;

		private MatchPattern(String regexStr, Integer group) {
			this.regex = Pattern.compile(regexStr);
			this.group = group;
		}
		
		public String getValue(String data){
			Matcher m = regex.matcher(data);
			if(m.find()){
				return m.group(group);
			}
			return "";
		}
	}

	private Date eventTime;
	private String recordData;

	public MatchRecord() {
		super();
	}

	public MatchRecord(String record) throws ParseException {
		String data[] = record.split("\\s{1}-\\s{1}");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		eventTime = sdf.parse(data[0]);
		recordData = data[1];
	}

	public Date getEventTime() {
		return eventTime;
	}

	public String getPropertie(MatchPattern pattern) {
		return pattern.getValue(recordData);
	}

	public boolean isMatchStart() {
		return recordData.contains("New match");
	}

	public boolean isMatchEnd() {
		return recordData.contains("has ended");
	}

	public boolean isSystemPlayer() {
		return MatchPattern.KILLER_PLAYER.getValue(recordData).equalsIgnoreCase("<WORLD>");
	}
}
