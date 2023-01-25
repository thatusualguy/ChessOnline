package io.github.thatusualguy.chessonline.ui.games.board;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;

//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.games.Games;
//import com.google.android.gms.games.multiplayer.ParticipantResult;
//import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.github.thatusualguy.chessonline.ui.games.GameFragment;

//import de.j4velin.chess.BuildConfig;
//import de.j4velin.chess.GameFragment;
//import de.j4velin.chess.R;
//import de.j4velin.chess.util.Logger;


public class Game {

    private final static int PROTOCOL_VERSION = 1;

    private final static int[] PLAYER_COLOR =
            {Color.parseColor("#FF8800"), Color.parseColor("#99CC00"), Color.parseColor("#33B5E5"),
                    Color.parseColor("#CC0000")};

    public final static int MODE_2_PLAYER_2_SIDES = 1;
    public final static int MODE_2_PLAYER_4_SIDES = 2;
    public final static int MODE_4_PLAYER_TEAMS = 3;
    public final static int MODE_4_PLAYER_NO_TEAMS = 4;

    public static String myPlayerId;
//    private static GoogleApiClient api;
    public static Match match;
    public static Player[] players;
    public static int turns;

    private static List<String> deadPlayers;

    public static GameFragment UI;
    private static GoogleApiClient api;

    public static String matchModeToName(final Context c, int mode) {
        switch (mode) {
            default:
            case MODE_2_PLAYER_2_SIDES:
//                return c.getString(R.string.mode1);
                return "";
            case MODE_2_PLAYER_4_SIDES:
//                return c.getString(R.string.mode2);
                return "";
            case MODE_4_PLAYER_TEAMS:
//                return c.getString(R.string.mode3);
                return "";
            case MODE_4_PLAYER_NO_TEAMS:
//                return c.getString(R.string.mode4);
                return "";
        }
    }

    /**
     * Should be called when a move is made
     */
    public static void moved() {
        turns++;
        String next = players[turns % players.length].id;
        while (deadPlayers.contains(next)) {
            turns++; // skip dead players
            next = players[turns % players.length].id;
        }
        if (next.startsWith("AutoMatch_")) next = null;

        if (UI != null) UI.updateTurn();
    }

    public static void save(final Context c) {
        if (match.isLocal) {
            c.getSharedPreferences("localMatches", Context.MODE_PRIVATE).edit()
                    .putString("match_" + match.id + "_" + match.mode, new String(toBytes()))
                    .commit();
        }
    }

    /**
     * Gets the ID of the winner team
     *
     * @return the team-id of the winner team
     */
    public static int getWinnerTeam() {
        return getWinner().team;
    }

    /**
     * Gets the player you has won the game
     *
     * @return the winner
     */
    private static Player getWinner() {
        for (Player p : players) {
            if (!deadPlayers.contains(p.id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Game over
     */
    public static void over() {
        int winnerTeam = getWinnerTeam();
//        if (!match.isLocal) {
//            if (BuildConfig.DEBUG) Logger.log("Game.over state: " + match.getStatus());
//            if (match.getStatus() == TurnBasedMatch.MATCH_STATUS_ACTIVE) {
//                List<ParticipantResult> result = new ArrayList<ParticipantResult>(players.length);
//                for (Player p : players) {
//                    result.add(new ParticipantResult(p.id,
//                            p.team == winnerTeam ? ParticipantResult.MATCH_RESULT_WIN :
//                                    ParticipantResult.MATCH_RESULT_LOSS,
//                            ParticipantResult.PLACING_UNINITIALIZED));
//                    if (BuildConfig.DEBUG)
//                        Logger.log(p.id + " " + (p.team == winnerTeam ? "win" : "loss"));
//                }
//                Games.TurnBasedMultiplayer.finishMatch(api, match.id, toBytes(), result);
//            } else {
//                Games.TurnBasedMultiplayer.finishMatch(api, match.id);
//            }
//            if (UI != null) UI.gameOver(winnerTeam == getPlayer(myPlayerId).team);
//        } else {
//            if (UI != null) UI.gameOverLocal(getWinner());
//        }
    }

    /**
     * Checks if the game is over
     *
     * @return true, if the game is over
     */
    public static boolean isGameOver() {
//        if (BuildConfig.DEBUG) Logger.log(
//                "Game.isGameOver: #Player: " + players.length + " #Dead: " + deadPlayers.size() +
//                        ((deadPlayers.size() == 2) ?
//                                " sameTeam: " + sameTeam(deadPlayers.get(0), deadPlayers.get(1)) :
//                                "-"));
//        return (players.length - deadPlayers.size() <= 1) ||
//                (deadPlayers.size() == 2 && sameTeam(deadPlayers.get(0), deadPlayers.get(1)));
        return false;
    }

    /**
     * Removes a player from the game
     *
     * @param playerId the player to remove
     * @return true, if the game is now over
     */
    public static boolean removePlayer(final String playerId) {
        if (players.length > 2) Board.removePlayer(playerId);
        deadPlayers.add(playerId);
        return isGameOver();
    }

    /**
     * Check if this client should move its piece
     *
     * @return true, if it's this client's turn
     */
    public static boolean myTurn() {
        return match.isLocal || myPlayerId.equals(players[turns % players.length].id);
    }

    /**
     * Gets the id for the currently active player.
     *
     * @return the id of the currently active player
     */
    public static String currentPlayer() {
        return players[turns % players.length].id;
    }


    /**
     * Load game data
     *
     * @param data the data to load
     * @param m    the match
//     * @param a    the ApiClient
     * @return false, if protocol version is too old and the app should be updated first
     */
    public static boolean load(final byte[] data, final Match m) {
//        if (BuildConfig.DEBUG) Logger.log("  load: " + (new String(data)));
//        String[] s = new String(data).split(":");
//        // newer protocol used for the match
//        if (s.length > 6 && s[6] != null && Integer.parseInt(s[6]) > PROTOCOL_VERSION) {
//            return false;
//        }
//        api = a;
//        match = m;
//        if (s.length > 5 && s[5] != null) {
//            match.mode = Integer.parseInt(s[5]);
//        }
//        turns = Integer.parseInt(s[0]);
//        deadPlayers = new LinkedList<String>();
//        if (s.length > 3 && s[3] != null) {
//            for (String dead : s[3].split(",")) {
//                if (BuildConfig.DEBUG) Logger.log("  dead: " + dead);
//                if (dead != null && dead.length() > 0) deadPlayers.add(dead);
//            }
//        }
//        createPlayers();
//        if (BuildConfig.DEBUG)
//            Logger.log("Game.load myPlayerId: " + myPlayerId + " playersInData: " + s[1]);
//        if (!s[1].contains(players[1].id)) {
//            s[2] = s[2].replace("AutoMatch_2", players[1].id);
//        }
//        if (players.length > 2) {
//            if (!s[1].contains(players[2].id)) {
//                s[2] = s[2].replace("AutoMatch_3", players[2].id);
//            }
//            if (!s[1].contains(players[3].id)) {
//                s[2] = s[2].replace("AutoMatch_4", players[3].id);
//            }
//        }
//        Board.load(s[2], match.mode);
//        if (s.length > 4 && s[4] != null) {
//            String[] lastMoves = s[4].split(";");
//            String[] coords;
//            for (int i = 0; i < lastMoves.length; i++) {
//                if (BuildConfig.DEBUG) Logger.log("  lastMove: " + lastMoves[i]);
//                if (lastMoves[i].equals("-")) continue;
//                coords = lastMoves[i].split(",");
//                players[i].lastMove = new Pair<Coordinate, Coordinate>(
//                        new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]),
//                                Board.getRotation()),
//                        new Coordinate(Integer.parseInt(coords[2]), Integer.parseInt(coords[3]),
//                                Board.getRotation()));
//            }
//        }
//        if (isGameOver()) over();
        return true;
    }

    /**
     * Returns the byte-array representation of the game
     *
     * @return the byte-array representation of the game
     */
    private static byte[] toBytes() {
        StringBuilder sb = new StringBuilder(turns + ":" + players[0].id + "," + players[1].id);
        if (players.length > 2)
            sb.append(",").append(players[2].id).append(",").append(players[3].id);
        sb.append(":").append(Board.getString()).append(":");
        for (String dead : deadPlayers)
            sb.append(dead).append(",");
        sb.append(":");
        Coordinate oldPos, newPos;
        for (int i = 0; i < players.length; i++) {
            if (players[i].lastMove == null) {
                sb.append("-");
            } else {
                oldPos = new Coordinate(players[i].lastMove.first.x, players[i].lastMove.first.y,
                        (4 - Board.getRotation()) % 4);
                newPos = new Coordinate(players[i].lastMove.second.x, players[i].lastMove.second.y,
                        (4 - Board.getRotation()) % 4);
                sb.append(oldPos.toString()).append(",").append(newPos.toString());
            }
            sb.append(";");
        }
        sb.append(":").append(match.mode).append(":").append(PROTOCOL_VERSION);
//        if (BuildConfig.DEBUG) Logger.log("  save: " + sb.toString());
        return sb.toString().getBytes();
    }

    /**
     * Initiate a new game
     *
     * @param m the match
     * @param a the ApiClient, can be null for a local game
     */
    public static void newGame(final Match m, final GoogleApiClient a) {
        api = a;
        match = m;
        turns = 0;
        deadPlayers = new LinkedList<String>();
        createPlayers();
        Board.newGame(players);
    }

    /**
     * Creates the player objects
     */
    private static void createPlayers() {
        int num_players = match.getNumPlayers();
        players = new Player[num_players];
        if (match.isLocal) {
            for (int i = 0; i < num_players; i++) {
                players[i] =
                        new Player(String.valueOf(i), match.mode == MODE_4_PLAYER_TEAMS ? i / 2 : i,
                                PLAYER_COLOR[i], "Player " + (i + 1));
            }
        } else {
            players[0] = new Player(match.getParticipants().get(0).getParticipantId(), 1,
                    PLAYER_COLOR[0], match.getParticipants().get(0).getDisplayName());
            if (match.getParticipants().size() > 1) {
                players[1] = new Player(match.getParticipants().get(1).getParticipantId(),
                        match.mode == MODE_4_PLAYER_TEAMS ? 1 : 2, PLAYER_COLOR[1],
                        match.getParticipants().get(1).getDisplayName());
            } else {
                players[1] = new Player("AutoMatch_2", match.mode == MODE_4_PLAYER_TEAMS ? 1 : 2,
                        PLAYER_COLOR[1], "Waiting for player...");
            }
            if (num_players > 2) {
                if (match.getParticipants().size() > 2) {
                    players[2] = new Player(match.getParticipants().get(2).getParticipantId(),
                            match.mode == MODE_4_PLAYER_TEAMS ? 2 : 3, PLAYER_COLOR[2],
                            match.getParticipants().get(2).getDisplayName());
                } else {
                    players[2] =
                            new Player("AutoMatch_3", match.mode == MODE_4_PLAYER_TEAMS ? 2 : 3,
                                    PLAYER_COLOR[2], "Waiting for player...");
                }
                if (match.getParticipants().size() > 3) {
                    players[3] = new Player(match.getParticipants().get(3).getParticipantId(),
                            match.mode == MODE_4_PLAYER_TEAMS ? 2 : 4, PLAYER_COLOR[3],
                            match.getParticipants().get(3).getDisplayName());
                } else {
                    players[3] =
                            new Player("AutoMatch_4", match.mode == MODE_4_PLAYER_TEAMS ? 2 : 4,
                                    PLAYER_COLOR[3], "Waiting for player...");
                }
            }
            myPlayerId = match.getParticipantId(Games.Players.getCurrentPlayerId(api));
        }

    }

    /**
     * Checks if id1 and id2 are on the same team
     *
     * @param id1 player1
     * @param id2 player2
     * @return true, if player1 and player2 are on the same team
     */
    public static boolean sameTeam(final String id1, final String id2) {
        return getPlayer(id1).team == getPlayer(id2).team;
    }

    /**
     * Gets the color of the player with the id 'id'
     *
     * @param id the player id
     * @return the player's color
     */
    public static int getPlayerColor(final String id) {
        return getPlayer(id).color;
    }

    /**
     * Gets the player object to the given id
     *
     * @param id the player id
     * @return the player or null, if no such player exists
     */
    public static Player getPlayer(final String id) {
        for (Player p : players) {
            if (p.id.equals(id)) return p;
        }
        return null;
    }
}
