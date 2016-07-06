package br.com.amil.service;

import java.io.Serializable;

import br.com.amil.exception.MatchException;
import br.com.amil.model.Match;

public interface GameMatchService extends Serializable {

	public Match loadMatch(String matchLog) throws MatchException;

	public Match getMatch();
	
}
