package pl.home.match_betting.tournaments.domain

interface TournamentStageOperations {

    fun creation(tournament: Tournament): TournamentStage
    fun betting(tournament: Tournament): TournamentStage
    fun running(tournament: Tournament): TournamentStage
}