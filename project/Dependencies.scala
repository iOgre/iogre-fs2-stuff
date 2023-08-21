

val cats = "2.9.0":
val catsEffect = "3.5.0"
val fs2 = "3.7.0"
val catsCore = "org.typelevel" %% "cats-core" % Versions.cats
val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect:
val fs2 = "co.fs2" %% "fs2-core" % Versions.fs2
val all: Seq[ModuleID] =
  Seq(
    catsCore,
    catsEffect,
    fs2
  )

object Versions

object Dependencies
