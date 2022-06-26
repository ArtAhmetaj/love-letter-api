package business.exceptions.models.business;

public class PlayerNotPartOfGameException extends BusinessException{
    public PlayerNotPartOfGameException(){
        error="player_not_part_of_game_exception";
    }
}
