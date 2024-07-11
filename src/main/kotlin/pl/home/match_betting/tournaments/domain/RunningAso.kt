package pl.home.match_betting.tournaments.domain

class RunningAso : TournamentStageOperations {
    override fun creation(tournament: Tournament): TournamentStage = TournamentStage.CREATION_STAGE

    override fun betting(tournament: Tournament): TournamentStage = TournamentStage.BET_STAGE

    override fun running(tournament: Tournament): TournamentStage = TournamentStage.RUNNING_STAGE

}