/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.player.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import com.example.player.model.PlayerRowMapper;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.example.player.model.Player;
import com.example.player.repository.PlayerRepository;
@Service
public class PlayerH2Service implements PlayerRepository{
    @Autowired
    private JdbcTemplate db;
    @Override
    public ArrayList<Player> getPlayers(){
        List<Player> playerList=db.query("select * from TEAM",new PlayerRowMapper());
        ArrayList<Player> players=new ArrayList<>(playerList);
        return players;
    }
    @Override
    public Player getPlayerById(int playerId){
        try{
            Player player=db.queryForObject("select * from TEAM where id=?",new PlayerRowMapper(),playerId);
            return player;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public Player addPlayer(Player player){
        db.update("insert into TEAM(name,number,role) values(?,?,?)",player.getplayerName(),player.getjerseyNumber(),player.getRole());
        Player savedplayer=db.queryForObject("select * from TEAM where name=? and number=? and role=?",new PlayerRowMapper(),player.getplayerName(),player.getjerseyNumber(),player.getRole());
        return savedplayer;
    }
    @Override
    public Player updatePlayer(int playerId,Player player){
        if(player.getplayerName()!=null){
            db.update("update TEAM set name=? where id=?",player.getplayerName(),playerId);
        }
        if(player.getjerseyNumber()!=0){
            db.update("update TEAM set number=? where id=?",player.getjerseyNumber(),playerId);
        }
        if(player.getRole()!=null){
            db.update("update TEAM set role=? where id=?",player.getRole(),playerId);
        }
        return getPlayerById(playerId);
    }
    @Override
    public void deletePlayer(int playerId){
        db.update("delete from TEAM where id=?",playerId);
    }
}