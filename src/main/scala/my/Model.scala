package my

object Model:
  case class Actor(id: Int, firstName: String, lastName: String, in: Set[Command]):
    def withCommand(cmd: Command): Actor = this.copy(in = in + cmd)

  object Actor:
    def apply(id: Int, firstName: String, lastName: String, in: Command) = new Actor(id, firstName, lastName, Set(in))
  end Actor

  enum Command:
    case JL
    case AA
    case SPM
