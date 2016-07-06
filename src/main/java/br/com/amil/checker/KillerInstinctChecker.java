package br.com.amil.checker;

import java.util.Date;
import java.util.Iterator;

import br.com.amil.model.Award;
import br.com.amil.model.MatchSessionRecord;

public class KillerInstinctChecker implements AwardChecker{

	public Award check(MatchSessionRecord matchSession) {
		Iterator<Date> it = matchSession.getKillsTime();
		long interval = 1000 * 60;
		long lastKill = 0;
		int killInstinct = 0;
		while (it.hasNext()) {
			long e = it.next().getTime();
			long dif = e - lastKill;
			if (dif <= interval && (++killInstinct) >= 5) {
				return new Award(Award.AwardType.KILLER_INSTINCT);
			} else {
				killInstinct = 0;
			}
			lastKill = e;
		}
		return null;
	}
	
}