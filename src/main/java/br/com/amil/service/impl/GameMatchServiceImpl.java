package br.com.amil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.amil.dao.MatchDao;
import br.com.amil.exception.MatchException;
import br.com.amil.model.Match;
import br.com.amil.service.GameMatchService;

@Component
public class GameMatchServiceImpl implements GameMatchService {

	private static final long serialVersionUID = 7866145159632110679L;
	
	private final MatchDao matchDao;
	
	@Autowired
	public GameMatchServiceImpl(MatchDao matchDao){
		this.matchDao = matchDao;
	}

	public Match loadMatch(String matchLog) throws MatchException {
		return matchDao.load(matchLog);
	}
	
	public Match getMatch(){
		return matchDao.getMatch();
	}
	
}
