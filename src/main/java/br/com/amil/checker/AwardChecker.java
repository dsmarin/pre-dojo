package br.com.amil.checker;

import br.com.amil.model.Award;
import br.com.amil.model.MatchSessionRecord;

public interface AwardChecker{
	Award check(MatchSessionRecord matchSession);
}