package pl.home.match_betting.tournaments.domain

enum class TournamentStage(
        private val operation: TournamentStageOperations,
        val operationName: String,
        val statusName: String): TournamentStageOperations {

    CREATION_STAGE(operation = CreationAso(), operationName = "creating new tournament", statusName = "newly created"),
    BET_STAGE(operation = BettingAso(), operationName = "betting phase start", statusName = "betting"),
    RUNNING_STAGE(operation = RunningAso(), operationName = "running phase start", statusName = "running");

    override fun creation(tournament: Tournament): TournamentStage = operation.creation(tournament)

    override fun betting(tournament: Tournament): TournamentStage = operation.betting(tournament)
    override fun running(tournament: Tournament): TournamentStage = operation.running(tournament)
}