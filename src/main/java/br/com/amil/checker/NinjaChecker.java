package br.com.amil.checker;

import br.com.amil.model.Award;
import br.com.amil.model.MatchSessionRecord;

public class NinjaChecker implements AwardChecker{

	public Award check(MatchSessionRecord matchSession) {
		if (matchSession.isWinner() && matchSession.getDeaths() == 0) {
			return new Award(Award.AwardType.NINJA);
		}
		return null;
	}
	
}