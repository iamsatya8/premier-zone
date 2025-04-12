package com.premierleague.premier_zone.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName){
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam_name()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getPlayerName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String searchText){
        return playerRepository.findAll().stream()
                .filter(player ->
                        player.getPosition().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByTeamAndPosition(String team, String posistion){
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam_name()) && posistion.equals(player.getPosition()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player){
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player updatePlayer){
        Optional<Player> existingPlayer = playerRepository.findByPlayerName(updatePlayer.getPlayerName());

        if(existingPlayer.isPresent()){
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setPlayerName(updatePlayer.getPlayerName());
            playerToUpdate.setTeam_name(updatePlayer.getTeam_name());
            playerToUpdate.setPosition(updatePlayer.getPosition());
            playerToUpdate.setNation(updatePlayer.getNation());

            playerRepository.save(playerToUpdate);

            return playerToUpdate;
        }
        return  null;
    }

    public void deletePlayer(String playerName){
        playerRepository.deleteByPlayerName(playerName);
    }
}
