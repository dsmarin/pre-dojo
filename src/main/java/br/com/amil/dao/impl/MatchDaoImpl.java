package br.com.amil.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

import br.com.amil.dao.MatchDao;
import br.com.amil.exception.MatchException;
import br.com.amil.model.Match;
import br.com.amil.model.MatchRecord;

@Component
public class MatchDaoImpl implements MatchDao {

	private static final long serialVersionUID = -8576779464489414596L;
	
	private Match match;
	
	public Match getMatch(){
		return this.match;
	}

	public Match load(String matchLog) throws MatchException {
		BufferedReader reader = new BufferedReader(new StringReader(matchLog));
		try {
			Match match = new Match();
			StringTokenizer token = new StringTokenizer(matchLog, "\n", false);
			while (token.hasMoreTokens()) {
				String row = token.nextToken();
				if(!row.isEmpty()){
					match.addSessionStatiscs(new MatchRecord(row));
				}
			}
			reader.close();
			match.finalize();
			this.match = match;
			return match;
		} catch (IOException e) {
			throw new MatchException(0x0001l, e);
		} catch (ParseException e) {
			throw new MatchException(0x0002l, e);
		} catch (Exception e){
			throw new MatchException(0x0000l, e);
		}
	}

}
