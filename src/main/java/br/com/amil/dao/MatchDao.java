package br.com.amil.dao;

import java.io.Serializable;

import br.com.amil.exception.MatchException;
import br.com.amil.model.Match;

public interface MatchDao extends Serializable{
	
	public Match load(String matchLog) throws MatchException;

	public Match getMatch();

}
